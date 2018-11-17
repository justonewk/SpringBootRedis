package com.wk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;

@SpringBootConfiguration
public class SpringBootRedisApplication {

    private static SpringBootRedisApplication instance;
    public static void main(String args[]){
        SpringApplication.run(SpringBootRedisApplication.class);
        instance = (SpringBootRedisApplication) SpringContextUtils.getBeanByClass(SpringBootRedisApplication.class);

    }

    public static SpringBootRedisApplication getInstance() {
        return instance;
    }
}
