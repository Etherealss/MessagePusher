package cn.wtk.mp.relation.domain.relation.sub;

import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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

    @Transactional(rollbackFor = Exception.class)
    public void createSubRelation(SubRelationVO subRelationVO) {
        Query query = Query.query(Criteria.where("connectorId").is(subRelationVO.getConnectorId()));
        SubRelationItem subRelationItem = new SubRelationItem(subRelationVO.getSubscriberId(), subRelationVO.getRelationTopic(), new Date());
        Update update = new Update().push("subscribers", subRelationItem);
        UpdateResult result = mongoTemplate.upsert(query, update, SubRelationEntity.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeSubRelation(SubRelationVO subRelationVO) {
        Query query = Query.query(Criteria.where("connectorId").is(subRelationVO.getConnectorId()));
        SubRelationItem subRelationItem = new SubRelationItem(subRelationVO.getSubscriberId(), subRelationVO.getRelationTopic(), new Date());
        Update update = new Update().push("subscribers", subRelationItem);
        UpdateResult result = mongoTemplate.upsert(query, update, SubRelationEntity.class);
    }
}
