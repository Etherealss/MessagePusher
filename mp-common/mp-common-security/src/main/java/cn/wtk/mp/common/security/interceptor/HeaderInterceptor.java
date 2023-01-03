package cn.wtk.mp.common.security.interceptor;


import cn.wtk.mp.common.base.interceptor.ConfigHandlerInterceptor;
import cn.wtk.mp.common.security.config.ServerCredentialConfig;
import cn.wtk.mp.common.security.config.UserCredentialConfig;
import cn.wtk.mp.common.security.exception.TokenException;
import cn.wtk.mp.common.security.service.auth.ICredentialVerifier;
import cn.wtk.mp.common.security.service.auth.server.ServerCredential;
import cn.wtk.mp.common.security.service.auth.server.ServerSecurityContextHolder;
import cn.wtk.mp.common.security.service.auth.user.UserCredential;
import cn.wtk.mp.common.security.service.auth.user.UserSecurityContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取 Token 并将相关信息存入 SecurityContextHolder 中
 * @author wtk
 * @date 2022-08-30
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class HeaderInterceptor implements ConfigHandlerInterceptor {

    private final ICredentialVerifier tokenVerifier;
    private final UserCredentialConfig userCredentialConfig;
    private final ServerCredentialConfig serverCredentialConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        getUserTokenAndVerify(request);
        getServerTokenAndVerify(request);
        return true;
    }

    private void getUserTokenAndVerify(HttpServletRequest request) {
        String userToken = request.getHeader(userCredentialConfig.getHeaderName());
        if (StringUtils.hasText(userToken)) {
            try {
                UserCredential userCredential = tokenVerifier.verify(userToken, UserCredential.class);
                UserSecurityContextHolder.set(userCredential);
            } catch (TokenException ignored) {}
        }
    }

    private void getServerTokenAndVerify(HttpServletRequest request) {
        String serverToken = request.getHeader(serverCredentialConfig.getHeaderName());
        if (StringUtils.hasText(serverToken)) {
            try {
                ServerCredential credential = tokenVerifier.verify(serverToken, ServerCredential.class);
                ServerSecurityContextHolder.set(credential);
            } catch (TokenException ignored) {}
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        UserSecurityContextHolder.remove();
        ServerSecurityContextHolder.remove();
    }

    @Override
    public int getOrder() {
        return -10;
    }
}
