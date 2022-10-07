package com.vttp2.miniproject01.models;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import lombok.Data;

@Data
public class User {
    private String username;
    private String password;
    private String name;
    private String email;
   

    public User(){

    }

    public User (String name, String username, String email) {
        this.name = name;
        this.username = username;
        this.email = email;
    }


    public User (String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;

    }

	public static User createUser(String name, String username, String email, String password) {
        User userData = new User(name, username, email, password);

        userData.setName(name);
        userData.setUsername(username);
        userData.setEmail(email);
        userData.setPassword(password);
        System.out.println("user1" + userData);
        return userData;
    }

	public static User loginUser(JsonObject jsonObj) {
        User userData = new User();

        userData.setName(jsonObj.getString("name"));
        userData.setUsername(jsonObj.getString("username"));
        userData.setEmail(jsonObj.getString("email"));

        return userData;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("name", name)
            .add("username", username)
            .add("email", email)
            .add("password", password)
            
            .build();
    }

    public static User create(JsonObject jsonObj) {
        User user = new User();
        user.setName(jsonObj.getString("name"));
        user.setUsername(jsonObj.getString("username"));
        user.setEmail(jsonObj.getString("email"));
        user.setPassword(jsonObj.getString("password"));
        System.out.println("user2" + user);
        return user;
    }

    //? Convert JSON object --> String
    public static User create(String jsonStr) {
        StringReader strReader = new StringReader(jsonStr);
        JsonReader jsReader = Json.createReader(strReader);

        return create(jsReader.readObject());
    }
}
