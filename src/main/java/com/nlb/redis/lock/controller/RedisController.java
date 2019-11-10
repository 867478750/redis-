package com.nlb.redis.lock.controller;

import com.nlb.redis.lock.config.RedissonConfig;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class RedisController {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    Redisson redisson;


    @GetMapping("/buy")
    public String buy(){
        String clientId = UUID.randomUUID().toString();
        RLock lockKey = redisson.getLock("lockKey");
        try {
            lockKey.lock(10,TimeUnit.SECONDS);
//            Boolean setIfAbsent = stringRedisTemplate.opsForValue().setIfAbsent("lockKey", clientId,10, TimeUnit.SECONDS);
//            if(!setIfAbsent){
//                return "请稍后再试！";
//            }
            String storage = stringRedisTemplate.opsForValue().get("storage");
            int storageProduct = Integer.parseInt(storage);
            if(storageProduct>0){
                storageProduct = storageProduct-1;
                stringRedisTemplate.opsForValue().set("storage",String.valueOf(storageProduct));
                System.out.println("成功，目前余量： "+storageProduct);
            }else{
                System.out.println("失败，目前余量 "+storageProduct);
            }
        }finally {
            lockKey.unlock();
//            if(clientId.equals(stringRedisTemplate.opsForValue().get("lockKey"))) {
//                stringRedisTemplate.delete("lockKey");
//            }
        }


        return "end";
    }
}
