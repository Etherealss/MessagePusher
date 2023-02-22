package cn.wtk.mp.common.base.uid.config;

import cn.wtk.mp.common.base.uid.SnowFlakeUidGenerator;
import cn.wtk.mp.common.base.uid.UidGenerator;
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
        log.info("默认 UID 生成器：雪花算法");
        return new SnowFlakeUidGenerator(
                uidServerConfig.getDataCenterId(),
                uidServerConfig.getMachineId()
        );
    }
}
