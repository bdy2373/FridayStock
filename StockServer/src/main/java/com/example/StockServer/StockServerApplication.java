package com.example.StockServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class StockServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockServerApplication.class, args);
	}

}
