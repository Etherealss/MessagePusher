package cn.wtk.mp.connect.domain.conn.server.connector.device;

import cn.wtk.mp.common.base.exception.service.ServiceFiegnException;
import cn.wtk.mp.common.base.utils.UrlUtil;
import cn.wtk.mp.connect.domain.conn.server.AuthResult;
import cn.wtk.mp.connect.domain.conn.server.ServerConnContainer;
import cn.wtk.mp.connect.infrastructure.config.ChannelAttrKey;
import cn.wtk.mp.connect.infrastructure.config.NettyServerConfig;
import cn.wtk.mp.connect.infrastructure.remote.feign.AuthFeign;
import cn.wtk.mp.connect.infrastructure.remote.netty.MessageSender;
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

import java.util.Map;
import java.util.Objects;

/**
 * @author wtk
 * @date 2023-01-14
 */
@ChannelHandler.Sharable
@Component
@RequiredArgsConstructor
@Slf4j
public class ConnAuthHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final String PARAM_NAME_CONNECTOR_TOKEN = "connectToken";
    private static final String PARAM_NAME_CONNECTOR_ID = "connectorId";
    private static final String PARAM_NAME_DEVICE_ID = "deviceId";
    private static final String PARAM_NAME_APP_ID = "appId";
    private static final String HEADER_ORIGIN = "Origin";

    private final AuthFeign authFeign;
    private final MessageSender messageSender;
    private final ServerConnContainer serverConnContainer;
    private final NettyServerConfig nettyServerConfig;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("数据类型：{}", msg.getClass());
        if (this.isAuthenticated(ctx.channel())) {
            /*
            这个方法只能在 channelRead 方法里调用，如果在 channelRead0 里调用会报错
            它的作用是将当前消息传给下一个 ChannelInboundHandler
            如果不调用的话，当 ChannelInboundHandler 匹配到合适的消息（比如当前，匹配到 WebSocketFrame）
            它就不会继续往后面传了
             */
            ctx.fireChannelRead(msg);
            return;
        }
        if (msg instanceof FullHttpRequest) {
            httpRequestAuth(ctx, (FullHttpRequest) msg);
        } else {
            log.info("连接尚未认证，关闭连接");
            messageSender.send(ctx.channel(), "连接尚未认证");
            ctx.close();
        }
        super.channelRead(ctx, msg);
    }

    private void httpRequestAuth(ChannelHandlerContext ctx, FullHttpRequest request) {
        String uri = request.uri();
        String origin = request.headers().get(HEADER_ORIGIN);
        Map<String, String> urlParameters = UrlUtil.getUrlParameters(uri);
        AuthResult authResult = this.doAuth(urlParameters);
        if (authResult.isSuccess()) {
            addToManager(ctx, authResult);
            // WebSocketServerProtocolHandler内部会通过URI与配置文件的URI做比对，
            // 如果URI一致，才会通过握手建立WebSocket连接
            request.setUri(nettyServerConfig.getPath());
        } else {
            // TODO 发送给客户端的数据结构
            messageSender.send(ctx.channel(), authResult.getErrorMsg());
            ctx.close();
        }
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
        String deviceIdStr = urlParameters.get(PARAM_NAME_DEVICE_ID);
        String appIdStr = urlParameters.get(PARAM_NAME_APP_ID);
        if (!isNotBlack(connectorToken, appIdStr, connectorIdStr, deviceIdStr)) {
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
            return AuthResult.success(
                    connectorId, Long.valueOf(appIdStr), Long.valueOf(deviceIdStr)
            );
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
        Long deviceId = authResult.getDeviceId();
        Long connectorId = authResult.getConnectorId();
        DeviceConnection conn = new DeviceConnection(deviceId, connectorId, authResult.getAppId(), ctx);
        ctx.channel().attr(ChannelAttrKey.CONNECTOR).set(connectorId);
        ctx.channel().attr(ChannelAttrKey.DEVICE_ID).set(deviceId);
        serverConnContainer.addConn(conn);
        log.info("连接创建：RemoteIP={}, connectorId={}, appId={}, deviceId={}",
                ctx.channel().remoteAddress(),
                connectorId,
                authResult.getAppId(),
                deviceId
        );
    }

    private boolean isAuthenticated(Channel channel) {
        return channel.attr(ChannelAttrKey.DEVICE_ID).get() != null;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) {
    }

}