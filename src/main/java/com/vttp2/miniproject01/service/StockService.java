package com.vttp2.miniproject01.service;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.vttp2.miniproject01.models.Query;
import com.vttp2.miniproject01.models.StockModel;
import com.vttp2.miniproject01.models.Stocks;
import com.vttp2.miniproject01.repo.StocksRepository;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import lombok.AllArgsConstructor;
import netscape.javascript.JSObject;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class StockService {
    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    String payload;

    @Autowired
    private StocksRepository stockRepo;

    String YAHOO_API_URL = "https://query1.finance.yahoo.com/v8/finance/chart/";
    String STOCK_API = "https://yahoofinance-stocks1.p.rapidapi.com/stock-metadata";
    String HISTORY_API = "https://yahoofinance-stocks1.p.rapidapi.com/stock-prices";
    
    public Optional <Stocks> getHistory (String symbol, String startDate, String endDate){
        String url = UriComponentsBuilder.fromUriString(HISTORY_API)
                    .queryParam("Symbol", symbol)
                    .queryParam("EndDateInclusive", endDate)
                    .queryParam("StartDateInclusive", startDate)
                    .toUriString();
                    logger.info(url);

                    RestTemplate template = new RestTemplate();
                ResponseEntity<String> resp;
                try {
                HttpHeaders headers = new HttpHeaders();
                headers.set("X-RapidAPI-Key", "9aba53796dmshc6ab0ba9379cf3ep17ed8cjsn8946feb8201d");
                headers.set("X-RapidAPI-Host", "yahoofinance-stocks1.p.rapidapi.com");
                HttpEntity request = new HttpEntity<>(headers);
                resp = template.exchange(url, HttpMethod.GET, request, String.class);
                 //throw exception if status code not in range
                 //resp = template.exchange(req, String.class);

                 //get payload
                 payload = resp.getBody();
                 stockRepo.save(symbol, payload);
                 Stocks s = Stocks.create(resp.getBody());
                 return Optional.of(s);
                 
                }
                catch (Exception e) {
                    logger.error(e.getMessage());
                        e.printStackTrace();
                }
                return Optional.empty();

    }
    public Optional<Stocks> getStocks (String symbol) {
        
            String url = UriComponentsBuilder.fromUriString(STOCK_API)
                .queryParam("Symbol",symbol)
                .toUriString();
                logger.info(url);

                //create get req, get url
                //RequestEntity<Void> req = RequestEntity.get(url).build();
                
                RestTemplate template = new RestTemplate();
                ResponseEntity<String> resp;
                try {
                HttpHeaders headers = new HttpHeaders();
                headers.set("X-RapidAPI-Key", "9aba53796dmshc6ab0ba9379cf3ep17ed8cjsn8946feb8201d");
                headers.set("X-RapidAPI-Host", "yahoofinance-stocks1.p.rapidapi.com");
                HttpEntity request = new HttpEntity<>(headers);
                resp = template.exchange(url, HttpMethod.GET, request, String.class);
                 //throw exception if status code not in range
                 //resp = template.exchange(req, String.class);

                 //get payload
                 payload = resp.getBody();
                 stockRepo.save(symbol, payload);
                 Stocks s = Stocks.create(resp.getBody());
                 return Optional.of(s);
                 

                
        } catch (Exception e) {
            logger.error(e.getMessage());
                e.printStackTrace();
        }

        return Optional.empty();
        
    }

    
}
