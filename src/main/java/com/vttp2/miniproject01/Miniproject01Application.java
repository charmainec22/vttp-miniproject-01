package com.vttp2.miniproject01;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

@SpringBootApplication
public class Miniproject01Application {
	public static void main(String[] args) throws IOException {
		SpringApplication.run(Miniproject01Application.class, args);
	}

}
