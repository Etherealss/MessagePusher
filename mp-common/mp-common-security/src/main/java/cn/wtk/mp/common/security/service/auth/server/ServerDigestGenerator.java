package cn.wtk.mp.common.security.service.auth.server;

import cn.wtk.mp.common.base.exception.internal.BugException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author wtk
 * @date 2022-12-30
 */
@Component
public class ServerDigestGenerator {

    public static final String ALGORITHM = "SHA-1";

    private final MessageDigest messageDigest;

    public ServerDigestGenerator() {
        try {
            this.messageDigest = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new BugException("加密算法不存在");
        }
    }

    public byte[] generate(Long serverId, String serverName, String secret) {
        String data = serverId.toString() + serverName + secret;
        byte[] digest = messageDigest.digest(data.getBytes(StandardCharsets.UTF_8));
        return digest;
    }
}
