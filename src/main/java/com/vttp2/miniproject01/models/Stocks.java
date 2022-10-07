package com.vttp2.miniproject01.models;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Stocks {

    private static final Logger logger = LoggerFactory.getLogger(Stocks.class);

    private String currency;
    private String instrumentType;
    private String timestamp;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private String close;
    private BigDecimal volume;
    private Query query;
    private String symbol;
    private BigDecimal price;
    private String market;
    private String startDate;
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    private String endDate;
    private String date;


    public String getMarket() {
        return market;
    }
    public void setMarket(String market) {
        this.market = market;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getInstrumentType() {
        return instrumentType;
    }
    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public BigDecimal getOpen() {
        return open;
    }
    public void setOpen(BigDecimal open) {
        this.open = open;
    }
    public BigDecimal getHigh() {
        return high;
    }
    public void setHigh(BigDecimal high) {
        this.high = high;
    }
    public BigDecimal getLow() {
        return low;
    }
    public void setLow(BigDecimal low) {
        this.low = low;
    }
    public String getClose() {
        return close;
    }
    public void setClose(String close) {
        this.close = close;
    }
    public BigDecimal getVolume() {
        return volume;
    }
    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }
    public Query getQuery() {
        return query;
    }
    public void setQuery(Query query) {
        this.query = query;
    }

    // public static Stocks createJson(String json) throws IOException {
    //     logger.info("stocks createJson");
    //     Stocks s = new Stocks();
    //     try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
    //         JsonReader r = Json.createReader(is);
    //         JsonObject o = r.readObject();
    //         logger.info(">>>>>>" + o.getJsonObject("query"));
    //         s.query = Query.createJson(o.getJsonObject("query"));
    //         s.currency = o.getString("currency");
    //         s.close = o.getString("close");
    //         s.high = o.getString("high");
    //         s.instrumentType = o.getString("instrumentType");
    //         s.low = o.getString("low");
    //         s.open = o.getString("open");
    //         s.timestamp = o.getString("timestamp");
    //         s.volume = o.getString("volume");
    //         logger.info(">>>>>>>" +s.toString());
    //     }
    //     return s;
    // }

    public static Stocks create (String json) throws IOException {
        Stocks s = new Stocks();
        try(InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            JsonObject stocks = o.getJsonObject("result");
            logger.info(stocks.toString());

            s.symbol = stocks.getString("symbol");
            logger.info("symbol is > " + s.getSymbol());

            s.currency = stocks.getString("currency");
            logger.info("currency is > " + s.getCurrency());
            
            s.price = stocks.getJsonNumber("regularMarketPrice").bigDecimalValue();

            s.open = stocks.getJsonNumber("regularMarketOpen").bigDecimalValue();
            s.high = stocks.getJsonNumber("regularMarketDayHigh").bigDecimalValue();
            s.low = stocks.getJsonNumber("regularMarketDayLow").bigDecimalValue();
            s.volume = stocks.getJsonNumber("regularMarketVolume").bigDecimalValue();
            s.market = stocks.getString("market");
        }
        return s;
    }
    public static Stocks create2(JsonObject jo) {
        Stocks s = new Stocks();
        // s.setClose(jo.getString("close"));
        s.setCurrency(jo.getString("currency"));
        //s.setHigh(jo.getString("high"));
        //s.setInstrumentType("instrumentType");
        //s.setLow("low");
        //s.setHigh("high");
        //s.setOpen("open");
        s.setSymbol(jo.getString("symbol"));
        //s.setTimestamp("timestamp");
        //s.setVolume("volume");
        // /ystem.out.println("ID is >>>> " + n.getId());
        // System.out.println("ImageUrl is >>>> " + n.getImageurl());
        logger.info("symbol is" + s.getSymbol());
        return s;
    }
}
