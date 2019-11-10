package com.nlb.redis.lock.service;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

public interface DistributeLock {
    RLock lock(String lockKey);

    RLock lock(String lockKey, long timeout);

    RLock lock(String lockKey, TimeUnit unit, long timeout);

    boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);

    void unlock(String lockKey);

    void unlock(RLock lock);

}
