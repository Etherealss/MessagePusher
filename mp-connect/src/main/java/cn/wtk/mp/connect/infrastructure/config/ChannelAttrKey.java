package cn.wtk.mp.connect.infrastructure.config;

import io.netty.util.AttributeKey;

/**
 * @author wtk
 * @date 2023-01-14
 */
public class ChannelAttrKey {
    public static final AttributeKey<Long> CONNECTOR = AttributeKey.valueOf("connectorKey");
    public static final AttributeKey<Long> DEVICE_ID = AttributeKey.valueOf("deviceId");

}
