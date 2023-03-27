package cn.wtk.mp.msg.manager.infrasturcture;

import cn.wtk.mp.common.base.enums.BaseEnum;
import cn.wtk.mp.common.database.config.MongoConverter;
import cn.wtk.mp.common.msg.enums.MsgTransferStatus;
import cn.wtk.mp.common.msg.enums.MsgType;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

/**
 * @author wtk
 * @date 2023/3/27
 */
@Configuration
public class EnumMongoConverterConfiguration {

    @Component
    @WritingConverter
    private static class EnumToIntConverter implements MongoConverter<BaseEnum, Integer> {
        @Override
        public Integer convert(BaseEnum source) {
            return source.getCode();
        }
    }

    @Component
    @ReadingConverter
    private static class IntToStatusConverter implements MongoConverter<Integer, MsgTransferStatus> {
        @Override
        public MsgTransferStatus convert(Integer source) {
            return BaseEnum.fromCode(MsgTransferStatus.class, source);
        }
    }

    @Component
    @ReadingConverter
    private static class IntToMsgTypeConverter implements MongoConverter<Integer, MsgType> {
        @Override
        public MsgType convert(Integer source) {
            return BaseEnum.fromCode(MsgType.class, source);
        }
    }
}
