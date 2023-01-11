package cn.wtk.connect.domain.server;

import cn.wtk.connect.infrastructure.feign.AuthFeign;
import cn.wtk.mp.common.base.exception.service.ServiceFiegnException;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * @author wtk
 * @date 2023-01-11
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ConnectTokenAuthService {

    private static final String PARAM_NAME_CONNECT_TOKEN = "connectToken";
    private static final String PARAM_NAME_CONNECTOR_ID = "connectorId";
    private static final String PARAM_NAME_APP_ID = "appId";
    private final AuthFeign authFeign;

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
            authFeign.verify(connectorId, connectToken);
            return new AuthResult(true, null);
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
