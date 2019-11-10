package com.nlb.redis.lock.controller;

import com.nlb.redis.lock.config.RedisClusterConfig;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
@RestController
public class RedisClusterController {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisClusterConfig redisClusterConfig;


    @GetMapping("/buy1")
    public String buy1(){
        try {
            boolean lockKey = redisClusterConfig.tryLock("lockKey", TimeUnit.SECONDS, 5L, 10L);
            if(lockKey){
                String storage = stringRedisTemplate.opsForValue().get("storage");
                int storageProduct = Integer.parseInt(storage);
                if(storageProduct>0){
                    //Thread.sleep(11000);
                    storageProduct = storageProduct-1;
                    stringRedisTemplate.opsForValue().set("storage",String.valueOf(storageProduct));
                    System.out.println("成功，目前余量： "+storageProduct);
                }else{
                    System.out.println("失败，目前余量 "+storageProduct);
                }
            }else{
            return "获取锁失败！";
            }
        } finally {
            redisClusterConfig.unlock("lockKey");
        }
        return "end";
    }
}
