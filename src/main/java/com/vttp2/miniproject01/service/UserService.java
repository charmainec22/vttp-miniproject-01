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

    public void createAccount(User user) {
        userRepo.create(user.getEmail(), user.toMap());
    }

    public void updateProfile(String email, String key, String value) {
        userRepo.updateUser(email, key, value);
    }


    public void deleteProfile(String email) {
        userRepo.deleteUser(email);
        System.out.println("User has been deleted");
    }

    public boolean login(String email, String password) {

        Optional<User> redisValue = userRepo.findUserByEmail(email);

        if (redisValue.isEmpty()) {
            return false;
        } 
        String redisPassword = redisValue.get().getPassword();
        if (password.equals(redisPassword)) {
            return true;
        }

        return false;
    }

    public boolean checkProfile(String email) {

        Optional<User> redisValue = userRepo.findUserByEmail(email);

        if (redisValue.isEmpty()) {
            return false;
        } 
        //String redis_profile = redisValue.get().getProfile();
        
        //? Check if password is correct
        // if (redis_profile.isBlank()) {
        //     return false;
        // }

        return true;
    }

    public User userDetails(String email) {
        Optional<User> userOpt = userRepo.findUserByEmail(email);
        return userOpt.get();
    }

    public JsonObject toJson(String payload) {
        StringReader strReader = new StringReader(payload);
        JsonReader jsReader = Json.createReader(strReader);
        JsonObject createJson = jsReader.readObject();

        return createJson;

    }

}
