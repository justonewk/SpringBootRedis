package com.wk.utils;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 封装RedisTemplate的RedisUtil类
 * @author wk
 */
public class RedisUtil {
    private RedisTemplate<String,Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String,Object> redisTemplate){
        this.redisTemplate=redisTemplate;
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key){
        try{
            return redisTemplate.hasKey(key);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}