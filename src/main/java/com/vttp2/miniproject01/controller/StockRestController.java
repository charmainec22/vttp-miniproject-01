package com.vttp2.miniproject01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vttp2.miniproject01.models.User;
import com.vttp2.miniproject01.service.RedisService;
import com.vttp2.miniproject01.service.StockService;
import com.vttp2.miniproject01.service.UserService;

@RestController
public class StockRestController {
    
    @Autowired
    RedisService redisSvc;

    @Autowired
    StockService stocksSvc;

    @Autowired
    UserService userSvc;

    private static final Logger logger = LoggerFactory.getLogger(StockRestController.class);

   @GetMapping("/stocks/{symbol}")
   public ResponseEntity<String> getStocks (@PathVariable String symbol){
       String respBody = stocksSvc.getStocks(symbol).toString();
       return ResponseEntity.ok(respBody);
   }

   @GetMapping("/user/{email}")
   public ResponseEntity<String> getUser (@PathVariable String email){
       String respBody = userSvc.userDetails(email).toString();
       return ResponseEntity.ok(respBody);
   }
}
