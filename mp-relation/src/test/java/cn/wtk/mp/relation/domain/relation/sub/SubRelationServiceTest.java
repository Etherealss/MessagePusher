package cn.wtk.mp.relation.domain.relation.sub;

import cn.wtk.mp.relation.infrasturcture.client.command.CreateSubRelationCommand;
import cn.wtk.mp.relation.infrasturcture.client.command.RemoveSubRelationCommand;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@Slf4j(topic = "test")
@DisplayName("SubRelationServiceTest测试")
@SpringBootTest
class SubRelationServiceTest {

    @Autowired
    private SubRelationService service;

    @Test
    @Rollback(false)
    void testUpsertSubRelation() {
        service.upsertSubRelation(new CreateSubRelationCommand(
                1L,
                2L,
                "brother"
        ));
    }
    @Test
    @Rollback(false)
    void testUpsertSubRelation2() {
        service.upsertSubRelation(new CreateSubRelationCommand(
                1L,
                2L,
                "friend"
        ));
        service.upsertSubRelation(new CreateSubRelationCommand(
                1L,
                2L,
                "classmate"
        ));
        service.upsertSubRelation(new CreateSubRelationCommand(
                1L,
                2L,
                "family"
        ));
    }

    @Test
    void testRemoveSubRelation() {
        service.removeSubRelation(new RemoveSubRelationCommand(
                1L,
                2L,
                "friend"
        ));
    }

    @Test
    void testGetSubRelations() {
        List<String> subRelations = service.getSubRelations(1L, 2L);
        log.info("relations: {}", subRelations);
    }

    @Test
    void testCheckSubRelation() {
        boolean friend = service.checkSubRelation(1L, 2L, "friend");
        boolean family = service.checkSubRelation(1L, 2L, "family");
        log.info("friend: {}", friend);
        log.info("family: {}", family);
    }
}