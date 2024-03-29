package cn.wtk.mp.relation.domain.relation.sub;

import cn.wtk.mp.RelationApplication;
import cn.wtk.mp.relation.domain.connector.relation.SubRelationService;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.sub.CreateSubRelationCommand;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.sub.RemoveSubRelationCommand;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@Slf4j(topic = "test")
@DisplayName("SubRelationServiceTest测试")
@SpringBootTest(classes = {RelationApplication.class})
@Rollback(false)
class SubRelationServiceTest {

    @Autowired
    private SubRelationService service;

    @Test
    void testUpsertSubRelation() {
        service.upsertSubRelation(new CreateSubRelationCommand(
                1L,
                2L,
                "brother"
        ));
    }
    @Test
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
    void testGetSubRelations2() {
        List<String> subRelations = service.getSubRelations(1L, 524234L);
        log.info("relations: {}", subRelations);
    }

    @Test
    void testCheckSubRelation() {
        boolean friend = service.checkSubRelation(1L, 2L, "famliy");
        boolean noRelation = service.checkSubRelation(1L, 2L, "famil1231313y");
        log.info("famliy: {}", friend);
        log.info("noRelation: {}", noRelation);
    }
}