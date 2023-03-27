package cn.wtk.mp.connect.domain.conn.server.connector.connection;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023-02-25
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthMsg {
    String authUrl;
}
