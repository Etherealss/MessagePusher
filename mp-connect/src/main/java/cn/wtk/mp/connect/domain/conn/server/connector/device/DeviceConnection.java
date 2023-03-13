package cn.wtk.mp.connect.domain.conn.server.connector.device;

import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wtk
 * @date 2023-01-11
 */
@Data
@AllArgsConstructor
public class DeviceConnection {
    private Long deviceId;
    private Long connectorId;
    private Long appId;
    private ChannelHandlerContext ctx;
}
