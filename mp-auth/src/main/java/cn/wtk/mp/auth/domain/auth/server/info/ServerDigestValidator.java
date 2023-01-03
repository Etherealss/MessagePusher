package cn.wtk.mp.auth.domain.auth.server.info;

import cn.wtk.mp.common.base.exception.internal.BugException;
import cn.wtk.mp.common.security.service.auth.server.ServerDigestGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author wtk
 * @date 2022-12-29
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ServerDigestValidator {

    private final ServerDigestGenerator serverDigestGenerator;

    public boolean validate(byte[] userInput, ServerInfoEntity entity) {
        byte[] digest = serverDigestGenerator.generate(
                entity.getId(), entity.getServerName(), entity.getSecret()
        );
        return Arrays.equals(userInput, digest);
    }
}
