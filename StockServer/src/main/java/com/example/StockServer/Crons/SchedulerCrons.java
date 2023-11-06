package com.example.StockServer.Crons;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.StockServer.Jpa.CPNTHMJpaService;
import com.example.StockServer.Jpa.CompanyJpaService;
import com.example.StockServer.Jpa.ThemeJpaService;
import com.example.StockServer.ParseResponse.ThemeCaptureChart;
import com.example.StockServer.ParseResponse.ThemeResponse;
import com.example.StockServer.dao.CPNTHM;
import com.example.StockServer.dao.Company;
import com.example.StockServer.dao.Theme;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@Transactional
@Component
@RequiredArgsConstructor
public class SchedulerCrons {
	private static final Logger logger = LoggerFactory.getLogger(SchedulerCrons.class);
	
	private final CompanyJpaService companyService;
	private final ThemeJpaService themeService;
	private final CPNTHMJpaService cpnthmService;
	
	//@Scheduled(cron = "1 * * * * *")
	public void parsing() {
		logger.debug("parsing 시작~~");
		System.out.println(new Date());
		
		//List<ThemeCaptureChart> apiResponse = parseThemeCaptureChart();

		
//		companytheme("000660");//하이닉스
		List<Company> companyList = companyService.getCompanys();
		System.out.println("회사명이 뭘까요? " +companyList.size());
	//	for(Company company : companyList) {
	//		companytheme(company.getCompany_code());
	//	}
		Company company1 = companyService.getCompany(10);
		System.out.println("회사명이 뭘까요? " +company1.getCompanyName());

	}
	

}
