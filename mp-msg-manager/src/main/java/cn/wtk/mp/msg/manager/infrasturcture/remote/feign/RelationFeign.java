package cn.wtk.mp.msg.manager.infrasturcture.remote.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wtk
 * @date 2023/2/24
 */
@FeignClient(
        value = "mp-relation",
        path = "/relation"
)
public interface RelationFeign {

    @GetMapping("/groups/{groupId}/relations")
    List<Long> getGroupMembers(@PathVariable Long groupId);

    @GetMapping("/connectors/{connectorId}/relations/{subrId}/topics")
    Boolean checkSubRelation(@PathVariable Long connectorId,
                             @PathVariable Long subrId,
                             @RequestParam("topic") String relationTopic);
}
