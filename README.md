# SpringBootRedis
SpringBoot整合Redis步骤

1.使用Redis之前需要引入相关依赖，Maven方式依赖的脚本如下：
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        
        
2.将Redis的相关配置写到配置文件里（这里采用application.properties的方式，还有一种application.yml）

    注:Redis默认端口为6379 默认共有16个数据库，默认使用0号数据库
    
 配置如下:
 spring.redis.hostName=127.0.0.1
 spring.redis.port=6379    
 spring.redis.pool.maxActive=8    
 spring.redis.pool.maxWait=-1    
 spring.redis.pool.maxIdle=8    
 spring.redis.pool.minIdle=0    
 spring.redis.timeout=0 
  
