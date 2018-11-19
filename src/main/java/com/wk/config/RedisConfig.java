package com.wk.config;

import com.wk.utils.RedisUtil;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * SpringBoot整合redis
 * @author wk
 */
@Configuration
// 必须加，使配置生效
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
    /**
     * 注入 RedisConnectionFactory
     */
    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    /**
     * 实例化 RedisTemplate 对象
     *设置数据存入 redis 的序列化方式
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory ) {
        //这里使用Jackson2JsonRedisSerializer 替代默认的序列化
        //Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //xml序列化,速度慢,占用空间大
//        OxmSerializer oxmSerializer = new OxmSerializer();
        //jdk序列化,速度快,但是暂用空间大,并且序列化对象必须实现java.io.Serializable接口,redis默认采用jdk序列化
       JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());

        // 开启事务
        redisTemplate.setEnableTransactionSupport(true);

        return redisTemplate;
    }

    /**
     * 设置自动key的生成规则，配置spring boot的注解，进行方法级别的缓存
     * 使用：进行分割，可以很多显示出层级关系
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(":");
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(":" + String.valueOf(obj));
            }
            String rsToUse = String.valueOf(sb);
            return rsToUse;
        };
    }

    /**
     *  // 初始化缓存管理器，在这里我们可以缓存的整体过期时间什么的，我这里默认没有配置
     * @return
     */
    @Bean
    @Override
    public CacheManager cacheManager() {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
                .RedisCacheManagerBuilder.fromConnectionFactory(jedisConnectionFactory);
        return builder.build();
    }



    /**
     * 注入封装RedisTemplate
     * @param redisTemplate
     * @return
     */
    @Bean(name = "redisUtil")
    public RedisUtil redisUtil(RedisTemplate<String, Object> redisTemplate) {
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setRedisTemplate(redisTemplate);
        return redisUtil;
    }

}
