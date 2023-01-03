package cn.wtk.mp.uid.infrastructure.config;

import cn.wtk.mp.uid.infrastructure.generator.IUidGenerator;
import cn.wtk.mp.uid.infrastructure.generator.SnowFlakeUidGenerator;
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
    public IUidGenerator uidGenerator(UidServerConfig uidServerConfig) {
        return new SnowFlakeUidGenerator(
                uidServerConfig.getDataCenterId(),
                uidServerConfig.getMachineId()
        );
    }
}
