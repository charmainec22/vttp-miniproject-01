package com.vttp2.miniproject01.controller;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vttp2.miniproject01.models.Stocks;
import com.vttp2.miniproject01.service.StockService;

@Controller
@RequestMapping("/stockshistory")
public class StockHistoryController {

    @Autowired
    private StockService stocksSvc;

    private Logger logger = LoggerFactory.getLogger(StockHistoryController.class);

    @GetMapping
    public String getHistory( @RequestParam(required = true) String symbol, String startDate, String endDate ,Model model){
        // Query q = new Query();
        // q.setSymbol(symbol);
        // List<Stocks> stocks = stocksSvc.getStocks(q);
        // return "stocks";

        Optional<Stocks> opt = stocksSvc.getHistory(symbol, startDate, endDate);
        logger.info("symbol is " + symbol);
        logger.info("start date is " + startDate);
        logger.info("end date is " + endDate);
        if(opt.isEmpty()) {
            model.addAttribute("stocks", new Stocks());
            return "stockshistory";
        }
        model.addAttribute("stocks", opt.get());
        logger.info("opt is " + opt.get());
        return "stockshistory";
    }
}
