package com.vttp2.miniproject01.repo;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.vttp2.miniproject01.models.User;

@Repository
public class UserRepo {
    @Autowired
    @Qualifier("redislab")
    private RedisTemplate<String, String> template;

    public void create(String redisKey, Map<String, String> payload) {
        HashOperations<String, String, String> hashOp = template.opsForHash();
        hashOp.putAll(redisKey, payload);
    }


    public void updateUser(String key, String hashkey, String value) {
        HashOperations<String, String, String> hashOp = template.opsForHash();
        hashOp.put(key, hashkey, value);
    }

    public void deleteUser(String email) {
        template.delete(email);
    }

    public Optional<User> findUserByEmail(String email) {
        HashOperations<String, String, String> hashOp = template.opsForHash();
        String redisName = hashOp.get(email, "name");
        String redisUserName = hashOp.get(email, "username");
        String redisEmail = hashOp.get(email, "email");
        String redisPassword = hashOp.get(email, "password");
        String redisBook = hashOp.get(email, "favourite");


        User user = new User(redisName, redisUserName, redisEmail, redisPassword, redisBook);
        if (null == redisEmail)
            return Optional.empty();
        return Optional.of(user);
    }

}
