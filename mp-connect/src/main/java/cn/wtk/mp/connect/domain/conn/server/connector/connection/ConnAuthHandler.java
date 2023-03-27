package cn.wtk.mp.connect.domain.conn.server.connector.connection;

import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.exception.service.ServiceFiegnException;
import cn.wtk.mp.common.base.utils.JsonUtil;
import cn.wtk.mp.common.base.utils.UUIDUtil;
import cn.wtk.mp.common.base.utils.UrlUtil;
import cn.wtk.mp.connect.domain.conn.server.AuthResult;
import cn.wtk.mp.connect.domain.conn.server.ServerConnContainer;
import cn.wtk.mp.connect.infrastructure.client.dto.ChannelMsg;
import cn.wtk.mp.connect.infrastructure.config.ChannelAttrKey;
import cn.wtk.mp.connect.infrastructure.config.NettyServerConfig;
import cn.wtk.mp.connect.infrastructure.remote.feign.AuthFeign;
import cn.wtk.mp.connect.infrastructure.utils.MessageSender;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author wtk
 * @date 2023-01-14
 */
@ChannelHandler.Sharable
@Component
@RequiredArgsConstructor
@Slf4j
public class ConnAuthHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final String PARAM_NAME_CONNECTOR_TOKEN = "connectorToken";
    private static final String PARAM_NAME_CONNECTOR_ID = "connectorId";
    private static final String PARAM_NAME_APP_ID = "appId";
    private static final String HEADER_ORIGIN = "Origin";

    private final AuthFeign authFeign;
    private final ServerConnContainer serverConnContainer;
    private final NettyServerConfig nettyServerConfig;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object data) throws Exception {
        log.info("数据类型：{}", data.getClass());
        if (this.isAuthenticated(ctx.channel())) {
            /*
            这个方法只能在 channelRead 方法里调用，如果在 channelRead0 里调用会报错
            它的作用是将当前消息传给下一个 ChannelInboundHandler
            如果不调用的话，当 ChannelInboundHandler 匹配到合适的消息（比如当前，匹配到 WebSocketFrame）
            它就不会继续往后面传了
             */
            ctx.fireChannelRead(data);
            return;
        }
        AuthResult authResult;
        if (data instanceof FullHttpRequest) {
            authResult = this.auth4WebSocket(ctx, (FullHttpRequest) data);
        } else if (data instanceof ByteBuf) {
            authResult = this.auth4Socket(ctx, (ByteBuf) data);
        } else {
            log.info("连接未认证且不符合认证格式，拒绝连接");
            ChannelMsg resp = new ChannelMsg(ApiInfo.CONNECT_UNAUTH, "连接未认证且不符合认证格式，拒绝连接");
            MessageSender.send(ctx.channel(), resp);
            ctx.close();
            return;
        }
        if (authResult.isSuccess()) {
            addToManager(ctx, authResult);
        } else {
            ChannelMsg resp = new ChannelMsg(
                    ApiInfo.CONNECT_AUTH_FAIL, authResult.getErrorMsg()
            );
            MessageSender.send(ctx.channel(), resp);
            ctx.close();
        }
    }

    private AuthResult auth4WebSocket(ChannelHandlerContext ctx, FullHttpRequest request) {
        String uri = request.uri();
        String origin = request.headers().get(HEADER_ORIGIN);
        Map<String, String> urlParameters = UrlUtil.getUrlParameters(uri);
        AuthResult authResult = this.doAuth(urlParameters);
        if (authResult.isSuccess()) {
            // WebSocketServerProtocolHandler内部会通过URI与配置文件的URI做比对，
            // 如果URI一致，才会通过握手建立WebSocket连接
            request.setUri(nettyServerConfig.getPath());
        }
        return authResult;
    }

    private AuthResult auth4Socket(ChannelHandlerContext ctx, ByteBuf slicedByteBuf) {
        int readableBytes = slicedByteBuf.readableBytes();
        byte[] bytes = new byte[readableBytes];
        slicedByteBuf.readBytes(bytes);
        String jsonData = new String(bytes, StandardCharsets.UTF_8);
        AuthMsg authMsg;
        try {
            authMsg = JsonUtil.toObject(jsonData, AuthMsg.class);
        } catch (Exception e) {
            return AuthResult.fail("认证消息格式错误：" + e.getMessage());
        }
        Map<String, String> urlParameters = UrlUtil.getUrlParameters(authMsg.getAuthUrl());
        AuthResult authResult = this.doAuth(urlParameters);
        return authResult;
    }

    /**
     * 新建连接时的验证逻辑
     * @param urlParameters 将url的所有参数传输，验证逻辑需要什么参数就自取，实现封装
     * @return
     */
    public AuthResult doAuth(Map<String, String> urlParameters) {
        log.debug("进行创建连接的登录验证");
        Objects.requireNonNull(urlParameters, "url参数Map不能为空");
        String connectorToken = urlParameters.get(PARAM_NAME_CONNECTOR_TOKEN);
        String connectorIdStr = urlParameters.get(PARAM_NAME_CONNECTOR_ID);
        String appIdStr = urlParameters.get(PARAM_NAME_APP_ID);
        if (!isNotBlack(connectorToken, appIdStr, connectorIdStr)) {
            log.info("缺少参数(connectorToken, appId 或 connectorId)，不允许创建连接");
            return AuthResult.fail("缺少参数(connectorToken, appId 或 connectorId)，不允许创建连接");
        }
        try {
            Long appId = Long.valueOf(appIdStr);
        } catch (NumberFormatException e) {
            return AuthResult.fail("appId格式错误：非Long类型");
        }

        try {
            // 验证 connectorToken
            Long connectorId = Long.valueOf(connectorIdStr);
            authFeign.verify(appIdStr, connectorId, connectorToken);
            return AuthResult.success(connectorId, Long.valueOf(appIdStr));
        } catch (ServiceFiegnException e) {
            log.info("认证失败，异常报告：{}，RPC 返回值：{}", e.getMessage(), e.getResult());
            return AuthResult.fail("token无效或appId错误");
        }
    }

    private boolean isNotBlack(String... params) {
        for (String param : params) {
            if (!StringUtils.hasText(param)) {
                return false;
            }
        }
        return true;
    }

    private void addToManager(ChannelHandlerContext ctx, AuthResult authResult) {
        // 认证完毕，可以建立连接
        UUID connId = UUIDUtil.get();
        Long connectorId = authResult.getConnectorId();
        Connection conn = new Connection(connId, connectorId, authResult.getAppId(), ctx);
        ctx.channel().attr(ChannelAttrKey.CONNECTOR).set(connectorId);
        ctx.channel().attr(ChannelAttrKey.CONN_ID).set(connId);
        serverConnContainer.addConn(conn);
        log.info("连接创建：RemoteIP={}, connectorId={}, appId={}, connId={}",
                ctx.channel().remoteAddress(),
                connectorId,
                authResult.getAppId(),
                connId
        );
    }

    private boolean isAuthenticated(Channel channel) {
        return channel.attr(ChannelAttrKey.CONN_ID).get() != null;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) {
    }

}