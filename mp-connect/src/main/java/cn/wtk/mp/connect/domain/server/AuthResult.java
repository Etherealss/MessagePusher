package cn.wtk.mp.connect.domain.server;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class AuthResult {
    boolean success;
    String errorMsg;
    Long connectorId;
    Long deviceId;
    Long appId;

    public static AuthResult success(Long connectorId, Long appId, Long deviceId) {
        AuthResult authResult = new AuthResult();
        authResult.success = true;
        authResult.connectorId = connectorId;
        authResult.appId = appId;
        authResult.deviceId = deviceId;
        return authResult;
    }

    public static AuthResult fail(String errorMsg) {
        AuthResult authResult = new AuthResult();
        authResult.success = false;
        authResult.errorMsg = errorMsg;
        return authResult;
    }
}
