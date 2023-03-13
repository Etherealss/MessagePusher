package cn.wtk.mp.common.security.service.auth.server;


import cn.wtk.mp.common.security.annotation.InternalAuth;
import cn.wtk.mp.common.security.config.ServerCredentialConfig;
import cn.wtk.mp.common.security.service.auth.IPreAuthHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author wtk
 * @date 2022-10-13
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ServerPreAuthHandler implements IPreAuthHandler {
    private final ServerCredentialConfig serverCredentialConfig;

    @Override
    public boolean checkNeedAuth(Method method) {
        InternalAuth internalAuth = method.getAnnotation(InternalAuth.class);
        if (internalAuth == null || !internalAuth.serv()) {
            log.info("请求无需检验");
            return false;
        }
        return true;
    }

    @Override
    public void doAuth(Method method) {
        ServerSecurityContextHolder.require();
    }
}
