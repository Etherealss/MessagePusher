package cn.wtk.mp.relation.domain.group.relation;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wtk
 * @date 2022-12-28
 */
@Repository
public interface GroupRelationRepository extends MongoRepository<GroupRelationEntity, Long> {
}
