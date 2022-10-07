package com.vttp2.miniproject01.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vttp2.miniproject01.models.User;
import com.vttp2.miniproject01.service.UserService;

@Controller
public class LoginController {
    
    @Autowired
    private UserService userSvc;

    @GetMapping(path = "/")
    public String getLoginPage() {
        return "login";
    }

    // @GetMapping(path = "/login")
    // public String LoginPage() {
    //     return "stocks";
    // }

    @PostMapping(path = "/login")
    public String postHomePage(Model model, @RequestBody MultiValueMap<String, String> form, HttpSession session) {
        String email = form.getFirst("email");
        String password = form.getFirst("password");

        
        Boolean loginStatus = userSvc.login(email, password);
        Boolean profileExists = userSvc.checkProfile(email);

       
        if (loginStatus == false) {
            String errorLogin = "Incorrect email or password";
            model.addAttribute("errorMessage", errorLogin);
            return "login";
        }
        
        User userDetails = userSvc.userDetails(email);
        
        session.setAttribute("userDetails", userDetails);
        model.addAttribute("userDetails", userDetails);
        return "home";
    }

    @PostMapping(path = "/register")
    public String postRegisterPage() {
       return "register";
    }
}
