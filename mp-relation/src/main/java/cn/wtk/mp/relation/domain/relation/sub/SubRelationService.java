package cn.wtk.mp.relation.domain.relation.sub;

import cn.wtk.mp.relation.infrasturcture.client.command.CreateSubRelationCommand;
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
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

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
        Query query = Query.query(Criteria.where(SubRelationEntity.CONNECTOR_ID).is(command.getConnectorId()));

        SubRelationItem subRelationItem = new SubRelationItem(
                command.getSubrId(), command.getRelationTopic(), new Date()
        );
        Update update = new Update().addToSet(SubRelationEntity.RELATION, subRelationItem);

        mongoTemplate.upsert(query, update, SubRelationEntity.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeSubRelation(SubRelationVO subRelationVO) {
        Query query = Query.query(Criteria.where(SubRelationEntity.CONNECTOR_ID).is(subRelationVO.getConnectorId()));
        Document fields = new Document()
                .append(SubRelationItem.SUBR_ID, subRelationVO.getSubrId())
                .append(SubRelationItem.RELATION_TOPIC, subRelationVO.getRelationTopic());
        Update update = new Update().pull(SubRelationEntity.RELATION, fields);
        mongoTemplate.upsert(query, update, SubRelationEntity.class);
    }

    public List<String> getSubRelations(Long connectorId, Long subrId) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.unwind(SubRelationEntity.RELATION),
                Aggregation.match(Criteria
                        .where(SubRelationEntity.CONNECTOR_ID).is(connectorId)
                        .and(SubRelationEntity.RELATION + "." + SubRelationItem.SUBR_ID).is(subrId)
                ),
                Aggregation.project(SubRelationEntity.RELATION)
        );
        AggregationResults<SubRelationAggOutput> groupResults =
                mongoTemplate.aggregate(agg, SubRelationEntity.class, SubRelationAggOutput.class);
        List<SubRelationAggOutput> subRelationAggOutputs = groupResults.getMappedResults();
        if (CollectionUtils.isEmpty(subRelationAggOutputs)) {
            return Collections.emptyList();
        } else {
            SubRelationAggOutput subRelationAggOutput = subRelationAggOutputs.get(0);
            return subRelationAggOutput.getRelations();
        }
    }

    public boolean checkSubRelation(Long connectorId, Long subrId, String relationTopic) {
        Query query = Query.query(Criteria
                .where(SubRelationEntity.CONNECTOR_ID).is(connectorId)
                .and(SubRelationEntity.RELATION + "." + SubRelationItem.SUBR_ID).is(subrId)
                .and(SubRelationEntity.RELATION + "." + SubRelationItem.RELATION_TOPIC).is(relationTopic)
        );
        Object value = mongoTemplate.findOne(query, SubRelationEntity.class, SubRelationEntity.CONNECTOR_ID);
        return value != null;
    }
}
