package com.vttp2.miniproject01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vttp2.miniproject01.models.Query;
import com.vttp2.miniproject01.models.Stocks;
import com.vttp2.miniproject01.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

@Controller
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stocksSvc;

    private Logger logger = LoggerFactory.getLogger(StockController.class);

    @GetMapping
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
