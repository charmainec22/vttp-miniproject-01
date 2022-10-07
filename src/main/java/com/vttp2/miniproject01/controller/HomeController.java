package com.vttp2.miniproject01.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.vttp2.miniproject01.models.User;
import com.vttp2.miniproject01.service.UserService;

@Controller
public class HomeController {
    
    @Autowired
    UserService userSvc;

    @GetMapping("/login")
    public String getHome(Model model, HttpSession session){
        User userDetails = (User) session.getAttribute("userDetails");
        String name = userDetails.getName();
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("name", name);
        return "login";
    }
}
