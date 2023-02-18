package cn.wtk.mp.relation.domain.connector.relation;

import cn.wtk.mp.relation.infrasturcture.client.command.relation.sub.CreateSubRelationCommand;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.sub.RemoveSubRelationCommand;
import cn.wtk.mp.relation.infrasturcture.client.converter.SubRelationConverter;
import cn.wtk.mp.relation.infrasturcture.client.dto.SubRelationAggOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wtk
 * @date 2023/2/14
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SubRelationService {

    private final SubRelationRepository subRelationRepository;
    private final MongoTemplate mongoTemplate;
    private final SubRelationConverter converter;

    @Transactional(rollbackFor = Exception.class)
    public void upsertSubRelation(CreateSubRelationCommand command) {
        Query query = Query.query(Criteria
                .where(SubRelationEntity.CONNECTOR_ID).is(command.getConnectorId())
        );

        SubRelationItem subRelationItem = new SubRelationItem(
                command.getSubrId(), command.getRelationTopic()
        );
        Update update = new Update().addToSet(SubRelationEntity.RELATIONS, subRelationItem);

        mongoTemplate.upsert(query, update, SubRelationEntity.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeSubRelation(RemoveSubRelationCommand command) {
        Query query = Query.query(Criteria.where(SubRelationEntity.CONNECTOR_ID).is(command.getConnectorId()));
        Document fields = new Document()
                .append(SubRelationItem.SUBR_ID, command.getSubrId())
                .append(SubRelationItem.RELATION_TOPIC, command.getRelationTopic());
        Update update = new Update().pull(SubRelationEntity.RELATIONS, fields);
        mongoTemplate.upsert(query, update, SubRelationEntity.class);
    }

    public List<String> getSubRelations(Long connectorId, Long subrId) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.unwind(SubRelationEntity.RELATIONS),
                Aggregation.match(Criteria
                        .where(SubRelationEntity.CONNECTOR_ID).is(connectorId)
                        .and(SubRelationEntity.RELATIONS + "." + SubRelationItem.SUBR_ID).is(subrId)
                ),
                Aggregation.project(SubRelationEntity.RELATIONS)
        );
        AggregationResults<SubRelationAggOutput> groupResults =
                mongoTemplate.aggregate(agg, SubRelationEntity.class, SubRelationAggOutput.class);
        List<SubRelationAggOutput> subRelationAggOutputs = groupResults.getMappedResults();
        List<String> topics = subRelationAggOutputs.stream()
                .map(output -> output.getRelations().getRelationTopic())
                .collect(Collectors.toList());
        log.debug("{}", topics);
        return topics;
    }

    public boolean checkSubRelation(Long connectorId, Long subrId, String relationTopic) {
        Query query = Query.query(Criteria
                .where(SubRelationEntity.CONNECTOR_ID).is(connectorId)
                .and(SubRelationEntity.RELATIONS + "." + SubRelationItem.SUBR_ID).is(subrId)
                .and(SubRelationEntity.RELATIONS + "." + SubRelationItem.RELATION_TOPIC).is(relationTopic)
        );
        // 判断是否存在时，只查一个字段，提高性能
        query.fields().include(SubRelationEntity.CONNECTOR_ID);
        Object value = mongoTemplate.findOne(query, SubRelationEntity.class);
        return value != null;
    }
}
