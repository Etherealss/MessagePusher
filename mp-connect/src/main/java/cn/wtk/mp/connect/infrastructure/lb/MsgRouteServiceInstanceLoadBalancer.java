package cn.wtk.mp.connect.infrastructure.lb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.RequestData;
import org.springframework.cloud.client.loadbalancer.RequestDataContext;
import org.springframework.cloud.loadbalancer.core.DelegatingServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.RequestBasedStickySessionServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author wtk
 * @date 2023/2/6
 * @see RequestBasedStickySessionServiceInstanceListSupplier 写法参考该类
 */
@Slf4j
public class MsgRouteServiceInstanceLoadBalancer extends DelegatingServiceInstanceListSupplier {


    private final MsgRouteHandler msgRouteHandler;

    public MsgRouteServiceInstanceLoadBalancer(ServiceInstanceListSupplier delegate, MsgRouteHandler msgRouteHandler) {
        super(delegate);
        this.msgRouteHandler = msgRouteHandler;
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return super.delegate.get();
    }

    @Override
    public Flux<List<ServiceInstance>> get(Request request) {
        Object context = request.getContext();
        if (context instanceof RequestDataContext) {
            RequestData clientRequest = ((RequestDataContext) context).getClientRequest();
            HttpHeaders headers = clientRequest.getHeaders();
            MsgRouteAddress msgRouteAddress = msgRouteHandler.getMsgRouteAddress(headers);
            if (msgRouteAddress != null) {
                return super.delegate.get(request).map(serviceInstances ->
                        this.selectInstance(serviceInstances, msgRouteAddress.getIp(), msgRouteAddress.getPort())
                );
            }
        }
        return super.get(request);
    }

    private List<ServiceInstance> selectInstance(List<ServiceInstance> serviceInstances, @NonNull String ip, int port) {
        Iterator<ServiceInstance> iterator = serviceInstances.iterator();

        ServiceInstance targetServerInstance = null;
        for (ServiceInstance serverInstance : serviceInstances) {
            if (ip.equals(serverInstance.getHost()) && port == serverInstance.getPort()) {
                targetServerInstance = serverInstance;
                break;
            }
        }
        if (targetServerInstance == null) {
            if (log.isDebugEnabled()) {
                log.warn("消息路由时，获取不到 ip 为 {} 且端口号为 {} 的 serverInstance，通过 delegate 返回所有 serverInstance。", ip, port);
            }
            return serviceInstances;
        } else {
            if (log.isDebugEnabled()) {
                log.debug("发现匹配的 serverInstance: {}，ip: {}，port: {}", targetServerInstance.toString(), ip, port);
            }
            return Collections.singletonList(targetServerInstance);
        }
    }
}
