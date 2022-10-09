package com.vttp2.miniproject01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vttp2.miniproject01.models.FavStock;
import com.vttp2.miniproject01.models.Favourite;
import com.vttp2.miniproject01.models.Query;
import com.vttp2.miniproject01.models.Stocks;
import com.vttp2.miniproject01.models.User;
import com.vttp2.miniproject01.service.RedisService;
import com.vttp2.miniproject01.service.StockService;
import com.vttp2.miniproject01.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

@Controller
public class StockController {

    @Autowired
    private StockService stocksSvc;

    @Autowired
    RedisService redisSvc;

    @Autowired
    UserService userSvc;

    public static String currentUser = null;

    private Logger logger = LoggerFactory.getLogger(StockController.class);

    @GetMapping(path = "/profile")
    public String getProfilePage(Model model, HttpSession session) {
        User userDetails = (User) session.getAttribute("userDetails");
        model.addAttribute("userDetails", userDetails);
        return "profile";

    }

    @PostMapping(path = "/updateUser") 
    public String updateUser(HttpSession session, Model model) {
        User userDetails = (User) session.getAttribute("userDetails");
        model.addAttribute("userDetails", userDetails);
        return "update";
    }

    @PostMapping(path = "/update") 
    public String updateProfilePage(Model model, HttpSession session, @RequestBody MultiValueMap<String, String> form) {
        
        //User userDetails = (User) session.getAttribute("userDetails");
        
        String name = form.getFirst("name");
        String username = form.getFirst("username");
        String password = form.getFirst("password");
        String email = form.getFirst("email");

        User userDetails = (User) session.getAttribute("userDetails");

        userSvc.updateProfile(email, "name", name);
        userSvc.updateProfile(email, "password", password);
        userSvc.updateProfile(email, "username", username);
        userSvc.updateProfile(email, "email", email);

        logger.info(">>>updated profile is " + userDetails);

        model.addAttribute("userDetails", userDetails);
        return "profile";
    }

    @PostMapping(path = "/deleteUser") 
    public String deleteProfilePage(Model model, HttpSession session) {
        User userDetails = (User) session.getAttribute("userDetails");
        String userEmail = userDetails.getEmail();
        //delete profile 
        userSvc.deleteProfile(userEmail);
        
        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        //remove user session details 
        session.removeAttribute("userDetails");
        session.invalidate();
        return "login";
    }


    @GetMapping("/stocks")
    public String getStocks( @RequestParam(required = true) String symbol,Model model){
        // Query q = new Query();
        // q.setSymbol(symbol);
        // List<Stocks> stocks = stocksSvc.getStocks(q);
        // return "stocks";

        Optional<Stocks> opt = stocksSvc.getStocks(symbol);
        logger.info("symbol is " + symbol);
        if(opt.isEmpty()) {
            model.addAttribute("stocks", new Stocks());
            return "stocks";
        }
        model.addAttribute("stocks", opt.get());
        logger.info("opt is " + opt.get());
        return "stocks";
    }
 
   
    @PostMapping(path = "/addfavourite")
    public String favouriteStock(Model model, HttpSession session, @RequestBody MultiValueMap<String, String> form) {
        //? When user click on Favourite button it will get the current book ID
        String symbol = form.getFirst("symbol");
        logger.info(">>>symbol is " + symbol);
        //? Gets the existing user details through session
        User userDetails = (User) session.getAttribute("userDetails");
        List<String> favouriteStock = stocksSvc.addFav(userDetails.getEmail().toString(), symbol);  
        logger.info(">>>favstock " + favouriteStock);
        logger.info(">>>favstock symbol " + symbol);
        userDetails.setFavourite(symbol);

        model.addAttribute("favStock", favouriteStock);
        model.addAttribute("userDetails", userDetails);
        return "favourite";
    }

    @GetMapping(path = "/favourite")
    public String FavPage(HttpSession session,Model model) {
        User userDetails = (User) session.getAttribute("userDetails");
        logger.info("userdetail: "+ userDetails);
        model.addAttribute("userDetails", userDetails);
        return "favourite";
    }

    @GetMapping(path = "/quote")
    public String homePage () {
        return "quote";
    }

    // @GetMapping
    // public String getHistory( @RequestParam(required = true) String symbol, String startDate, String endDate ,Model model){
    //     // Query q = new Query();
    //     // q.setSymbol(symbol);
    //     // List<Stocks> stocks = stocksSvc.getStocks(q);
    //     // return "stocks";

    //     Optional<Stocks> opt = stocksSvc.getHistory(symbol, startDate, endDate);
    //     logger.info("symbol is " + symbol);
    //     logger.info("start date is " + startDate);
    //     logger.info("end date is " + endDate);
    //     if(opt.isEmpty()) {
    //         model.addAttribute("stocks", new Stocks());
    //         return "stocks";
    //     }
    //     model.addAttribute("stocks", opt.get());
    //     logger.info("opt is " + opt.get());
    //     return "stocks";
    // }
}
