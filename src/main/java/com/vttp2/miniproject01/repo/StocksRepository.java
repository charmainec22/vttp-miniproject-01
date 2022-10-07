package com.vttp2.miniproject01.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class StocksRepository {
    @Autowired
    @Qualifier("redislab")
    private RedisTemplate<String, String> redisTemplate;

    public void save(String symbol, String payLoad) {
        ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
        valueOp.set(symbol.toLowerCase(), payLoad);
    }
}
