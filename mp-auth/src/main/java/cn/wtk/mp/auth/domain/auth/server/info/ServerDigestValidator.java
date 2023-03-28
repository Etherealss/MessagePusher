package cn.wtk.mp.auth.domain.auth.server.info;

import cn.wtk.mp.common.security.service.auth.server.ServerDigestGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wtk
 * @date 2022-12-29
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ServerDigestValidator {

    private final ServerDigestGenerator serverDigestGenerator;

    public boolean validate(String userInput, ServerInfoEntity entity) {
        // TODO 方便测试不做检验了
        if (userInput.startsWith("test")) {
            return true;
        }
        String digest = serverDigestGenerator.generate(
                entity.getId(), entity.getServerName(), entity.getSecret()
        );
        return userInput.equals(digest);
    }
}
