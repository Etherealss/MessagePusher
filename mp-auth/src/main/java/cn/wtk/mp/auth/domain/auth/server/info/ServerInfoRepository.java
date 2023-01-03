package cn.wtk.mp.auth.domain.auth.server.info;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wtk
 * @date 2022-12-28
 */
@Repository
public interface ServerInfoRepository extends MongoRepository<ServerInfoEntity, Long> {
}
