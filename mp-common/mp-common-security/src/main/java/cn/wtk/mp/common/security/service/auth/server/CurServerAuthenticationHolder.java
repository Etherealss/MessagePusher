package cn.wtk.mp.common.security.service.auth.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author wtk
 * @date 2022-10-20
 */
@Component
@Slf4j
public class CurServerAuthenticationHolder {

    private volatile ServerTokenCredential serverTokenCredential;
    private final IServerCredentialProvider serverCredentialProvider;

    public CurServerAuthenticationHolder(IServerCredentialProvider serverCredentialProvider) {
        this.serverCredentialProvider = serverCredentialProvider;
        updateServerCredential();
        log.info("初始化当前服务的 serverCredential： {}", serverTokenCredential);
    }

    private void updateServerCredential() {
        this.serverTokenCredential = serverCredentialProvider.create();
    }

    public ServerTokenCredential get() {
        if (serverTokenCredential == null || isExpired(serverTokenCredential)) {
            synchronized (serverCredentialProvider) {
                if (serverTokenCredential == null || isExpired(serverTokenCredential)) {
                    updateServerCredential();
                }
            }
        }
        return serverTokenCredential;
    }

    private boolean isExpired(ServerTokenCredential serverTokenCredential) {
        Date expireAt = serverTokenCredential.getExpireAt();
        return System.currentTimeMillis() >= expireAt.getTime();
    }
}
