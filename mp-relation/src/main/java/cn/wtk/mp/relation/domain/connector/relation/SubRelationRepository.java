package cn.wtk.mp.relation.domain.connector.relation;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wtk
 * @date 2022-12-28
 */
@Repository
public interface SubRelationRepository extends MongoRepository<SubRelationEntity, Long> {
}
