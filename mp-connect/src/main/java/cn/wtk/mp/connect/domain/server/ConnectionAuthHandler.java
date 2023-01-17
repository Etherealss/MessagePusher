package cn.wtk.mp.connect.domain.server;

import cn.wtk.mp.common.base.exception.service.ServiceFiegnException;
import cn.wtk.mp.common.base.utils.UUIDUtil;
import cn.wtk.mp.common.base.utils.UrlUtil;
import cn.wtk.mp.connect.domain.server.app.connector.ConnectorKey;
import cn.wtk.mp.connect.domain.server.app.connector.connection.Connection;
import cn.wtk.mp.connect.domain.server.app.connector.connection.MessageSender;
import cn.wtk.mp.connect.infrastructure.config.ChannelAttrKey;
import cn.wtk.mp.connect.infrastructure.feign.AuthFeign;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
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
public class ConnectionAuthHandler extends SimpleChannelInboundHandler<Object> {

    private static final String PARAM_NAME_CONNECT_TOKEN = "connectToken";
    private static final String PARAM_NAME_CONNECTOR_ID = "connectorId";
    private static final String PARAM_NAME_APP_ID = "appId";

    private final AuthFeign authFeign;
    private final MessageSender messageSender;
    private final ServerConnectionManager serverConnectionManager;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (this.isAuthenticated(ctx.channel())) {
            ctx.fireChannelRead(msg);
            return;
        }
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
            String origin = request.headers().get("Origin");
            Map<String, String> urlParameters = UrlUtil.getUrlParameters(uri);
            AuthResult authResult = this.doAuth(urlParameters);
            // TODO WebSocketServerProtocolHandler内部会通过URI与配置文件的URI做比对，如果URI一致，才会通过握手建立WebSocket连接
            request.setUri("/");
            if (authResult.isSuccess()) {
                addToManager(ctx, authResult);
            } else {
                // TODO 发送给客户端的数据结构
                messageSender.send(ctx.channel(), authResult.getErrorMsg());
                ctx.close();
            }
        } else {
            messageSender.send(ctx.channel(), "连接尚未认证");
            ctx.close();
            log.info("连接尚未认证，关闭连接");
        }
    }


    /**
     * 新建连接时的验证逻辑
     * @param urlParameters 将url的所有参数传输，验证逻辑需要什么参数就自取，实现封装
     * @return
     */
    public AuthResult doAuth(Map<String, String> urlParameters) {
        Objects.requireNonNull(urlParameters, "url参数Map不能为空");
        String connectToken = urlParameters.get(PARAM_NAME_CONNECT_TOKEN);
        String connectorId = urlParameters.get(PARAM_NAME_CONNECTOR_ID);
        String appIdStr = urlParameters.get(PARAM_NAME_APP_ID);
        if (!isNotBlack(connectToken, appIdStr, connectorId)) {
            log.info("缺少参数(connectToken, appId 或 connectorId)，不允许创建连接");
            return new AuthResult(false, "缺少参数(connectToken, appId 或 connectorId)，不允许创建连接");
        }
        try {
            Long appId = Long.valueOf(appIdStr);
        } catch (NumberFormatException e) {
            return new AuthResult(false, "appId格式错误：非Long类型");
        }

        try {
            // TODO 设置 serverToken
            // authFeign.verify(connectorId, connectToken);
            return new AuthResult(connectorId, Long.valueOf(appIdStr));
        } catch (ServiceFiegnException e) {
            log.info("认证失败，异常报告：{}，RPC 返回值：{}", e.getMessage(), e.getMsg());
            return new AuthResult(false, "token无效或appId错误");
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
        ConnectorKey connectorKey = new ConnectorKey(
                authResult.getAppId(),
                authResult.getConnectorId()
        );
        UUID connId = UUIDUtil.get();
        Connection conn = new Connection(connId, connectorKey, ctx);
        ctx.channel().attr(ChannelAttrKey.CONNECTOR).set(connectorKey);
        ctx.channel().attr(ChannelAttrKey.CONN_ID).set(connId);
        serverConnectionManager.addConn(conn);
        log.info("链接创建：RemoteIP={}, connectorKey={}, connId={}",
                ctx.channel().remoteAddress(), connectorKey, connId
        );
    }

    private boolean isAuthenticated(Channel channel) {
        return channel.attr(ChannelAttrKey.CONN_ID).get() != null;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
class AuthResult {
    boolean success;
    String errorMsg;
    Serializable connectorId;
    Long appId;

    public AuthResult(boolean success, String errorMsg) {
        this.success = success;
        this.errorMsg = errorMsg;
    }

    public AuthResult(Serializable connectorId, Long appId) {
        this.success = true;
        this.connectorId = connectorId;
        this.appId = appId;
    }
}