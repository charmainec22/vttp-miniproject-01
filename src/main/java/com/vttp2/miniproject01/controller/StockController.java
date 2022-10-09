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
        User userDetails = (User) session.getAttribute("userDetails");

        String name = form.getFirst("name");
        String username = form.getFirst("username");
        String password = form.getFirst("password");
        String email = form.getFirst("email");

        userSvc.updateProfile(email, "name", name);
        userSvc.updateProfile(email, "password", password);
        userSvc.updateProfile(email, "username", username);
        userSvc.updateProfile(email, "email", email);


        model.addAttribute("userDetails", userDetails);
        return "profile";
    }

    @PostMapping(path = "/deleteUser") 
    public String deleteProfilePage(Model model, HttpSession session) {
        User userDetails = (User) session.getAttribute("userDetails");
        String userEmail = userDetails.getEmail();
        userSvc.deleteProfile(userEmail);
        
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
 
    @PostMapping("/add/{email}")
    public String add (@PathVariable(name="email", required=true) String email, @ModelAttribute Stocks stocks, Model model, HttpSession session) {
        logger.info(">>> TEST" + stocks.getSymbol());
        logger.info("TESTTESTTESTTEST");
        Favourite favourite = new Favourite();
        logger.info(">>>TEST222222222222222222");
        //get user detail using session
        User user = (User) session.getAttribute("userDetails");
        if (user != null){
            email = user.getEmail();
        }
        logger.info (">>>> Email: " + email);
        //logger.info(user);
        User users = userSvc.userDetails(email);
        logger.info(">>>TEST33333333333333333333333");
        if (user.getfavourites() != null) {
            favourite = user.getfavourites();  
        }
        favourite.addQuotes(stocks);
        logger.info("STOCK ADDED IS " + stocks);
        user.setfavourites(favourite);
        //logger.info("fav added is: "+ favourite);
        logger.info("TEST");
        redisSvc.save(user);
        model.addAttribute("favourite", user.getfavourites());
        logger.info("fav added is: "+ user.getfavourites().toString());
        model.addAttribute("email", email); 
        logger.info("email added is: "+ email);
        return "favourite"; 
    }

    //href always a Get
    @GetMapping ("/favourite/{email}")
    public String favourite ( String email, Model model, HttpSession session) {
        //User user = redisSvc.findByUsername(email).get();
        User user = (User) session.getAttribute("userDetails");
        if (user != null){
            email = user.getEmail();
        }
        logger.info("email is: "+ email);
        //to refresh and get the latest prices
        List<Stocks> listOfQuotes = user.getfavourites().getListOfQuotes();
        logger.info("list added is: "+ listOfQuotes);
        listOfQuotes = stocksSvc.updateList(listOfQuotes);
        user.getfavourites().setListOfQuotes(listOfQuotes);
        model.addAttribute("favourite", user.getfavourites());
        model.addAttribute("email", email); 
        return "favourite"; 
    }

    @PostMapping(path = "/favourite")
    public String favouriteStock(Model model, HttpSession session, @RequestBody MultiValueMap<String, String> form) {
        //? When user click on Favourite button it will get the current book ID
        String symbol = form.getFirst("symbol");
        logger.info(">>>symbol is " + symbol);
        //? Gets the existing user details through session
        User userDetails = (User) session.getAttribute("userDetails");
        List<String> favouriteStock = stocksSvc.addFav(userDetails.getEmail().toString(), symbol);  
        logger.info(">>>favstock " + favouriteStock);
        userDetails.setFavourite(symbol);

        model.addAttribute("favStock", favouriteStock);
        model.addAttribute("userDetails", userDetails);
        return "favourite";
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
