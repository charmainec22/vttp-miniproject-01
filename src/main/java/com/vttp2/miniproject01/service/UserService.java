package com.vttp2.miniproject01.service;

import java.io.StringReader;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vttp2.miniproject01.models.User;
import com.vttp2.miniproject01.repo.UserRepo;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    private JsonObject jsonObj;

    public void createAccount(String email, String payload) {
        userRepo.create(email, payload);
        System.out.println("User created");
    }

    public void deleteProfile(String email) {
        userRepo.deleteUser(email);
        System.out.println("User has been deleted");
    }

    public boolean login(String email, String password) {

        Optional<String> redisValue = userRepo.findUser(email);

        if (redisValue.isEmpty()) {
            return false;
        } 
        String payload = redisValue.get();

        jsonObj = toJson(payload);
        String redis_password = jsonObj.getString("password");
        if (password.equals(redis_password)) {
            return true;
        }
        return false;
    }

    public boolean checkProfile(String email) {

        Optional<String> redisValue = userRepo.findUser(email);

        if (redisValue.isEmpty()) {
            return false;
        } 
        String payload = redisValue.get();

        jsonObj = toJson(payload);

        // String redis_profile = jsonObj.getString("profile");
        
        // if (redis_profile.isBlank()) {
        //     return false;
        // }

        return true;
    }

    public User userDetails(String email) {
        return User.loginUser(jsonObj);
    }

    public JsonObject toJson(String payload) {
        StringReader strReader = new StringReader(payload);
        JsonReader jsReader = Json.createReader(strReader);
        JsonObject createJson = jsReader.readObject();

        return createJson;

    }

}
