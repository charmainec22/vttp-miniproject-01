package com.vttp2.miniproject01.models;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import lombok.Data;

@Data
public class FavStock {
    private String symbol;

    public static FavStock addToFavStock(String symbol) {
        FavStock FavStockData = new FavStock();

        FavStockData.setSymbol(symbol);

        return FavStockData;
    }

    public static FavStock create(JsonObject jsonObj) {
        FavStock favStock = new FavStock();
        favStock.setSymbol(jsonObj.getString("symbol"));

        return favStock;
    }


    public static FavStock create(String jsonStr) {
        StringReader strReader = new StringReader(jsonStr);
        JsonReader jsReader = Json.createReader(strReader);

        return create(jsReader.readObject());
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
        .add("symbol", this.symbol)
        
        .build();
    }
}
