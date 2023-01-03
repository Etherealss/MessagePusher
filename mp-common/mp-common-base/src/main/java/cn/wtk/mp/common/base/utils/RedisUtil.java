package cn.wtk.mp.common.base.utils;

/**
 * @author wang tengkun
 * @date 2022/3/17
 */
public class RedisUtil {
    private final String lockLua;

    public RedisUtil() {
        // 先判断商品id是否存在，如果不存在则直接返回。
        // 获取该商品id的库存，判断库存如果是-1，则直接返回，表示不限制库存。
        // 如果库存大于0，则扣减库存。
        // 如果库存等于0，是直接返回，表示库存不足。
        lockLua = "if(redis.call('exists',KEYS[1])==1)then" +
                "localstock=tonumber(redis.call('get',KEYS[1]));" +
                "if(stock==-1)then" +
                "return1;" +
                "end;" +
                "if(stock>0)then" +
                "redis.call('incrby',KEYS[1],-1);" +
                "returnstock;" +
                "end;" +
                "return0;" +
                "end;" +
                "return-1;";
    }
}
