package com.vttp2.miniproject01.models;

import jakarta.json.JsonObject;
import lombok.extern.java.Log;

public class Query {
    private String symbol;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public static Query createJson(JsonObject o) {
        Query q = new Query();
        String toSymbol = o.getString("symbol");
        q.symbol = toSymbol;
        return q;
    }
}
