package com.nlb.redis.lock.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    @Bean
    public Redisson redissonGetConfig(){
        Config config = new Config();
        String[] nodes = new String[6];
        nodes[0]= "redis://192.168.98.128:6381";
        nodes[1]= "redis://192.168.98.128:6382";
        nodes[2]= "redis://192.168.98.128:6383";
        nodes[3]= "redis://192.168.98.128:6384";
        nodes[4]= "redis://192.168.98.128:6385";
        nodes[5]= "redis://192.168.98.128:6386";
        config.useClusterServers().addNodeAddress(nodes);
//       config.useSingleServer().setAddress("redis://192.168.98.128:6379").setDatabase(0);
        return (Redisson) Redisson.create(config);
    }
}
