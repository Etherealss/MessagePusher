package cn.wtk.mp.relation.domain.relation.group;

import cn.wtk.mp.RelationApplication;
import cn.wtk.mp.common.base.exception.service.ExistException;
import cn.wtk.mp.relation.domain.group.GroupService;
import cn.wtk.mp.relation.domain.group.relation.GroupRelationService;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.group.CreateGroupCommand;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.group.JoinGroupCommand;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.group.QuitGroupRelationCommand;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j(topic = "test")
@DisplayName("GroupRelationServiceTest测试")
@SpringBootTest(classes = {RelationApplication.class})
@Rollback(false)
class GroupRelationServiceTest {
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupRelationService groupRelationService;

    long GROUP_ID = 27836220164292608L;

    @Test
    void testCreateGroup() {
        assertDoesNotThrow(() -> {
            Long groupId = groupService.createGroup(new CreateGroupCommand(
                    1L,
                    "/test",
                    1L
            ));
            log.debug("创建群组：{}", groupId);
            GROUP_ID = groupId;
        }, "创建群组");
    }

    @Test
    void testCreateGroupRelation() {
        assertDoesNotThrow(() -> {
            groupRelationService.createGroupRelation(new JoinGroupCommand(GROUP_ID, 2L));
            groupRelationService.createGroupRelation(new JoinGroupCommand(GROUP_ID, 3L));
        }, "添加群成员");
        assertThrows(ExistException.class, () -> {
            groupRelationService.createGroupRelation(new JoinGroupCommand(GROUP_ID, 3L));
        });
    }


    @Test
    void testGetGroupRelations() {
        List<Long> groupRelations = groupRelationService.getGroupRelations(GROUP_ID);
        assertNotEquals(0, groupRelations.size(), "群用户不为空");
        log.debug("群成员：{}", groupRelations);
    }

    @Test
    void testRemoveGroupRelation() {
        assertDoesNotThrow(() -> {
            groupRelationService.removeGroupRelation(new QuitGroupRelationCommand(GROUP_ID, 2L));
        }, "删除群成员");
    }


    @Test
    void testCheckGroupRelation() {
        boolean b1 = groupRelationService.checkGroupRelation(GROUP_ID, 1L);
        assertFalse(b1, "用户 1231345342123L 与群组 1L 不具有群组关系");
        boolean b2 = groupRelationService.checkGroupRelation(GROUP_ID, 3L);
        assertTrue(b2, "用户 3L 与群组 1L 具有群组关系");
    }
}