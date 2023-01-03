package cn.wtk.mp.common.database.uid.config;

import cn.wtk.mp.common.database.uid.UidGenerator;
import cn.wtk.mp.common.database.uid.SnowFlakeUidGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wtk
 * @date 2023-01-02
 */
@Configuration
@Slf4j
public class UidAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public UidGenerator uidGenerator(UidServerConfig uidServerConfig) {
        return new SnowFlakeUidGenerator(
                uidServerConfig.getDataCenterId(),
                uidServerConfig.getMachineId()
        );
    }
}
