# SpringBootRedis
SpringBoot整合Redis(jedis pool连接池)步骤

1.使用Redis之前需要引入相关依赖，Maven方式依赖的脚本如下：

        <!-- 整合redis 所依赖的包-->
                <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-data-redis</artifactId>
                    <!-- 1.5的版本默认采用的连接池技术是jedis  2.0以上版本默认连接池是lettuce, 在这里采用jedis，所以需要排除lettuce的jar -->
                    <exclusions>
                        <exclusion>
                            <groupId>redis.clients</groupId>
                            <artifactId>jedis</artifactId>
                        </exclusion>
                        <exclusion>
                            <groupId>io.lettuce</groupId>
                            <artifactId>lettuce-core</artifactId>
                        </exclusion>
                    </exclusions>
                </dependency>
                <dependency>
                    <groupId>redis.clients</groupId>
                    <artifactId>jedis</artifactId>
                    <version>2.7.3</version>
                </dependency>
                <!--spring2.0集成redis所需common-pool2-->
                <!-- 必须加上，jedis依赖此  -->
                <dependency>
                    <groupId>org.apache.commons</groupId>
                    <artifactId>commons-pool2</artifactId>
                    <version>2.5.0</version>
                </dependency>
                <!-- 将作为Redis对象序列化器 -->
                <dependency>
                    <groupId>com.alibaba</groupId>
                    <artifactId>fastjson</artifactId>
                    <version>1.2.47</version>
                </dependency>
        
        
2.将Redis的相关配置写到配置文件里（这里采用application.properties的方式，还有一种application.yml）

    注:Redis默认端口为6379 默认共有16个数据库，默认使用0号数据库，这里采用jedis pool连接池
    
 配置如下:
 
 #Redis配置
 spring.redis.database=0
 spring.redis.host=127.0.0.1
 spring.redis.port=6379
 spring.redis.password=
 
 #连接超时时间（毫秒）
 spring.redis.timeout=1000
 
 #连接池最大连接数（使用负值表示没有限制）
 spring.redis.jedis.pool.max-active=8
 
 #连接池最大阻塞等待时间（使用负值表示没有限制）
 spring.redis.jedis.pool.max-wait=-1
 
 #连接池中最大的空闲连接
 spring.redis.jedis.pool.max-idle=5
 
 #连接池中最小的空闲连接
 spring.redis.jedis.pool.min-idle=8
  
