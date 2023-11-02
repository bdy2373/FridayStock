package com.example.StockServer.Controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.StockServer.Jpa.NewsJpaService;
import com.example.StockServer.Jpa.ThemeJpaService;
import com.example.StockServer.Response.NaverNews;
import com.example.StockServer.Response.NaverNews.Items;
import com.example.StockServer.dao.News;
import com.example.StockServer.dao.Theme;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins="*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/News")
public class NewsController {
	
	private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
	
	private final NewsJpaService newsJpaService;
	private final ThemeJpaService themeJpaService;
	
	@Value( "${naver.client.id}")
	private String clientKey;
	
	@Value( "${naver.client.secret}")
	private String clientSecret;
	
    @ApiOperation(
            value = "뉴스 전체 조회"
            , notes = "뉴스 전체 조회")
	@GetMapping(value = "/getAllNews")
    public List<News> getAllNews(){
    	System.out.println("getAllNews");
    	List<News> newsList = newsJpaService.getNewss();
    	System.out.println("getAllNews " + newsList.size());
       return newsList;
    }
    
    
    @ApiOperation(
            value = "테마를 이용해서 신규 뉴스를 등록해보자"
            , notes = "테마로 신규뉴스 조회하고 저장하기")
	@PutMapping(value = "/saveTodaysNewsWithTheme")
    public List<News> saveTodaysNewsWithTheme(@RequestParam Integer themeId){
    	System.out.println("saveTodaysNewsWithTheme");
    	Theme theme = themeJpaService.getTheme(themeId);
       	
    	List<News> updatedList = new ArrayList<>();
    	NaverNews newsList = new NaverNews();
       	newsList = parseNews(theme.getThemeName(), 10);
       	for(Items items : newsList.getItems()) {
       		logger.debug(theme.getThemeName()+" newsDB is making... "+items.getTitle());
       		News news = new News();
       		news.setNewsTitle(replaceTags(items.getTitle()));
       		news.setNewsLink(items.getLink());
       		news.setNewsOriginalLink(items.getOriginallink());
       		news.setDescription(replaceTags(items.getDescription()));
       		news.setPubDate(items.getPubDate());
       		news.setTheme(theme);
       		newsJpaService.createNews(news);
       		updatedList.add(news);
       	}
       	
    	System.out.println("addReasonToNews " + updatedList.size());
       return updatedList;
    }
    
    @ApiOperation(
            value = "뉴스에 이유 달기 "
            , notes = "뉴스에 이유 달기 ")
	@PutMapping(value = "/addReasonToNews")
    public List<News> addReasonToNews(@RequestBody List<News> newsList){
    	System.out.println("addReasonToNews");
    	List<News> updatedList = new ArrayList<>();
    	for(News news : newsList) {
        	News updatedNews = newsJpaService.updateNews(news);
        	updatedList.add(updatedNews);
    	}
    	System.out.println("addReasonToNews " + updatedList.size());
       return updatedList;
    }
    
    
	
    public NaverNews parseNews(String searchWord, int display) {
   		String endpoint = "https://openapi.naver.com/v1/search/news.json?query="+searchWord+"&display="+display+"&sort=sim";
   		logger.debug(endpoint);
   		WebClient webClient = WebClient.builder().baseUrl(endpoint)
   				.defaultHeader("X-Naver-Client-Id",clientKey)
   				.defaultHeader("X-Naver-Client-Secret", clientSecret)
   				.defaultHeader("Accept", "*/*").build();

   		NaverNews webClientResponse = new NaverNews();
   		try {
   			webClientResponse = webClient.get()
                       .retrieve()
                       .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                           throw new RuntimeException("4xx");
                       })
                       .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                           throw new RuntimeException("5xx");
                       })
                       .bodyToMono(new ParameterizedTypeReference<NaverNews>() {})
                       .block();
   		}catch(Exception e) {
   			e.printStackTrace();
   			return null;
   		}
   		logger.debug("webclient response" + webClientResponse.getTotal() + "   ");
   		return webClientResponse;
   		
   	}
    
    public String replaceTags(String input) {
       	return input
       			.replaceAll("<br>", "\n")
       			.replaceAll("&gt;", ">")
       			.replaceAll("&lt;", "<")
       			.replaceAll("&quot;", "")
       			.replaceAll("&nbsp;", " ")
       			.replaceAll("&amp;", "&")
       			.replaceAll("<b>", "")
       			.replaceAll("</b>", "");
       }
    
   
}
