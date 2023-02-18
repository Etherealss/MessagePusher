package cn.wtk.mp.msg.acceptor.infrasturcture.remote.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author wtk
 * @date 2023-02-18
 */
@FeignClient(
        value = "mp-relation",
        path = "/relation"
)
public interface RelationFeign {
}
