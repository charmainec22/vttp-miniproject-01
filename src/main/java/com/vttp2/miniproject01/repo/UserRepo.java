package com.vttp2.miniproject01.repo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepo {
    @Autowired
    @Qualifier("redislab")
    private RedisTemplate<String, String> template;

    public void create(String redisKey, String payload){
        ValueOperations<String, String> valueOp = template.opsForValue();
        valueOp.set(redisKey, payload);
        System.out.println("payload is"+ payload);
        System.out.println("added");
    }

    public Optional<String> findUser(String email){
        ValueOperations<String, String> valueOp = template.opsForValue();
        String user = valueOp.get(email);
        if (null == user)
            return Optional.empty();
        return Optional.of(user);
    }

    public Optional<String> deleteUser(String email) {
        ValueOperations<String, String> valueOp = template.opsForValue();
        String user = valueOp.getAndDelete(email);
        return Optional.of(user);
    }
}
