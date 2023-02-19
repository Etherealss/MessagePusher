package cn.wtk.mp.msg.acceptor.infrasturcture.remote.feign;

import cn.wtk.mp.common.security.annotation.InternalAuth;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wtk
 * @date 2023-02-18
 */
@FeignClient(
        value = "mp-relation",
        path = "/relation"
)
public interface RelationFeign {
    @GetMapping("/connectors/{connectorId}/relations/{subrId}/topics")
    @InternalAuth
    Boolean checkSubRelation(@PathVariable Long connectorId,
                             @PathVariable Long subrId,
                             @RequestParam("topic") String relationTopic);


    @GetMapping("/groups/{groupId}/relations/{memberId}")
    @InternalAuth
    Boolean checkMember(@PathVariable Long groupId,
                        @PathVariable Long memberId);
}
