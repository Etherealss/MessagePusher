package cn.wtk.mp.common.base.config;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author wtk
 * @date 2022-10-27
 */
@Slf4j
public class SystemConfig {
    public static final String IP;

    static {
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("获取IP失败", e);
        }
        IP = ip;
    }
}
