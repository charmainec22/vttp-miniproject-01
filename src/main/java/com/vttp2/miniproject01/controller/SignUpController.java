package com.vttp2.miniproject01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vttp2.miniproject01.models.User;
import com.vttp2.miniproject01.service.UserService;

import jakarta.json.JsonObject;

@Controller
@RequestMapping
public class SignUpController {
    @Autowired
    private UserService userSvc;

    // ? REGISTER PAGE

    @GetMapping(path = "/register")
    public String getRegisterPage() {

        return "register";

    }

    @PostMapping(path = "/")
    public String postRegisterPage(Model model, @RequestBody MultiValueMap<String, String> form) {

        String name = form.getFirst("name");
        String username = form.getFirst("username");
        String email = form.getFirst("email");
        String password = form.getFirst("password");

        User user = new User(name, username, email, password);

        //?  Convert User model -> JSON object
        JsonObject userObj = user.toJson();

        //? Convert JSON object -> String
        String payload = userObj.toString();

        //? Store data in Redis Database
        userSvc.createAccount(email, payload);

        return "/login";
    } 
}
