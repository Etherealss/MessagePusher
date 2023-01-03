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
public class CurServerCredentialHolder {

    private volatile ServerCredential serverCredential;
    private final IServerCredentialProvider serverCredentialProvider;

    public CurServerCredentialHolder(IServerCredentialProvider serverCredentialProvider) {
        this.serverCredentialProvider = serverCredentialProvider;
        updateServerCredential();
        log.info("初始化当前服务的 serverCredential： {}", serverCredential);
    }

    private void updateServerCredential() {
        this.serverCredential = serverCredentialProvider.create();
    }

    public ServerCredential get() {
        if (serverCredential == null || isExpired(serverCredential)) {
            synchronized (serverCredentialProvider) {
                if (serverCredential == null || isExpired(serverCredential)) {
                    updateServerCredential();
                }
            }
        }
        return serverCredential;
    }

    private boolean isExpired(ServerCredential serverCredential) {
        Date expireAt = serverCredential.getTokenExpireAt();
        return System.currentTimeMillis() >= expireAt.getTime();
    }
}
