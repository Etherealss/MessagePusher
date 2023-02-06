package cn.wtk.mp.connect.infrastructure.lb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import reactor.core.publisher.Mono;

/**
 * @author wtk
 * @date 2023/2/6
 */
@RequiredArgsConstructor
@Slf4j
public class MsgRouteServiceInstanceLoadBalancer implements ReactorServiceInstanceLoadBalancer  {

    private final ReactorServiceInstanceLoadBalancer delegate;

    private final MsgRouteHandler msgRouteHandler;

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        return null;
    }
}
