package com.vttp2.miniproject01.models;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component ("favourite")
public class Favourite {
    private List<Stocks> listOfQuotes = new LinkedList<>();

    public List<Stocks> getListOfQuotes() {
        return listOfQuotes;
    }

    public void setListOfQuotes(List<Stocks> listOfQuotes) {
        this.listOfQuotes = listOfQuotes;
    }

    public void addQuotes (Stocks quotes) {
        //prevent repetitive addd
        listOfQuotes.add(quotes);
    }

    public void deleteQuotes (String quotes) {
        for (int i = 0; i < listOfQuotes.size(); i++) {
            //Quotes is an object
            //listOfQuotes is a list of objects
            //this is the object Quotes q = listOfQuotes.get(i), then getSymbol() to make it into a string
            //compare string to string
            if (listOfQuotes.get(i).getSymbol().equals(quotes)) {
                listOfQuotes.remove(i);
            }
        }
        /* 
        for (Quotes q :listOfQuotes) {

            if (q.getSymbol()==quotes) {
                listOfQuotes.remove()
                remove only can do index. so must find another way of writing
            }
        }
        */ 
    }
    
}
