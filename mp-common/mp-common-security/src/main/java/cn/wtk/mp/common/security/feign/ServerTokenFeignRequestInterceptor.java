package cn.wtk.mp.common.security.feign;


import cn.wtk.mp.common.security.config.ServerCredentialConfig;
import cn.wtk.mp.common.security.service.auth.server.CurServerAuthenticationHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 在每一次进行服务间请求前，在 header 中加上 serverToken
 * @author wtk
 * @date 2022-10-17
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ServerTokenFeignRequestInterceptor implements RequestInterceptor {

    private final ServerCredentialConfig config;
    private final CurServerAuthenticationHolder curServerAuthenticationHolder;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            String serverToken = curServerAuthenticationHolder.get().getToken();
            if (StringUtils.hasText(serverToken)) {
                requestTemplate.header(config.getHeaderName(), serverToken);
            }
        } catch (Exception e) {
            log.warn("远程调用设置serverToken时出现异常", e);
            throw e;
        }
    }
}
