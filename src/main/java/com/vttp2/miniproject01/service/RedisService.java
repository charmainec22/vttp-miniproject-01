package com.vttp2.miniproject01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.vttp2.miniproject01.models.User;
import com.vttp2.miniproject01.repo.UserRepo;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.StringReader;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    RedisTemplate<String, User> redisTemplate;

    @Autowired
    @Qualifier("redislab")
    private RedisTemplate<String, String> template;
    
    @Autowired
    private UserRepo userRepo;

    private JsonObject jsonObj;

    public void save (final User user) {
        logger.info (">>>> Save user details: " + user.getUsername());
        redisTemplate.opsForValue().set(user.getUsername(), user);
    } 

    public Optional<User> findByUsername (final String email) {
        //logger.info (">>>> USER: " + User);
        logger.info (">>>> Find user details: " + email);
        try {
            User userDetails = (User) redisTemplate.opsForValue().get(email);
            return Optional.of(userDetails);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<User> findUserName(String email){
        logger.info (">>>> Find user details: " + email);
        ValueOperations<String, String> valueOp = template.opsForValue();
        String user = valueOp.get(email);
        // if (null == user)
        //     return Optional.empty();
            return Optional.empty();
    }

    public Optional<String> findUser(String email){
        
        logger.info (">>>> Find user details: " + email);
        ValueOperations<String, String> valueOp = template.opsForValue();
        String user = valueOp.get(email);
        if (null == user)
            return Optional.empty();
        return Optional.of(user);
    }

    // public boolean checkProfile(String email) {

    //     //? Get Redis Value from the Database
    //     Optional<String> redisValue = userRepo.findUser(email);

    //     if (redisValue.isEmpty()) {
    //         return false;
    //     } 

    //     //? Store the Value as a string called Payload
    //     String payload = redisValue.get();

    //     //? Convert the String to a JSON 
    //     jsonObj = toJson(payload);

    //     //? Check if the profile exists in Redist
    //     String redis_profile = jsonObj.getString("profile");
        
    //     //? Check if password is correct
    //     if (redis_profile.isBlank()) {
    //         return false;
    //     }

    //     return true;
    // }

    public JsonObject toJson(String payload) {
        StringReader strReader = new StringReader(payload);
        JsonReader jsReader = Json.createReader(strReader);
        JsonObject createJson = jsReader.readObject();

        return createJson;

    }

    public Optional<User> restResponse(String username){
        logger.info("Restcontoller calling for >>>" + username);
        User restUser = (User) redisTemplate.opsForValue().get(username);
        return Optional.of(restUser);
    }
    
}
