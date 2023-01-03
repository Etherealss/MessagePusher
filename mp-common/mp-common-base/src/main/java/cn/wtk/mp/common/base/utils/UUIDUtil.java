package cn.wtk.mp.common.base.utils;

import java.util.UUID;

/**
 * @author wtk
 * @date 2021-11-01
 */
public class UUIDUtil {
    /**
     * 获取简洁的uuid 去掉了“-”
     * @return
     */
    public static String getConcise() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static UUID get() {
        return UUID.randomUUID();
    }
}
