package database;

import cn.wtk.mp.auth.domain.auth.server.info.ServerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wtk
 * @date 2022-12-30
 */
public class DbTest {

    @Autowired
    ServerInfoRepository serverInfoRepository;

    public void createCollection() {

    }
}
