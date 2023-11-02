package com.example.StockServer.Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.StockServer.Jpa.CPNTHMJpaService;
import com.example.StockServer.Jpa.CompanyJpaService;
import com.example.StockServer.Jpa.NewsJpaService;
import com.example.StockServer.Jpa.ReasonsJpaService;
import com.example.StockServer.Jpa.ThemeJpaService;
import com.example.StockServer.Response.NaverNews;
import com.example.StockServer.Response.ThemeCaptureChart;
import com.example.StockServer.Response.ThemeResponse;
import com.example.StockServer.Response.NaverNews.Items;
import com.example.StockServer.dao.CPNTHM;
import com.example.StockServer.dao.Company;
import com.example.StockServer.dao.News;
import com.example.StockServer.dao.Reasons;
import com.example.StockServer.dao.Theme;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Temp")
public class TempController {
	private static final Logger logger = LoggerFactory.getLogger(TempController.class);
	
	
    private final CompanyJpaService companyJpaService;
	private final ThemeJpaService themeJpaService;
	private final ReasonsJpaService reasonsJpaService;

   	private final NewsJpaService newsJpaService;
   	
   	@Value( "${naver.client.id}")
   	private String clientKey;
   	
   	@Value( "${naver.client.secret}")
   	private String clientSecret;
    
    @GetMapping(value = "/addAllCompany")
    public int addAllTest() throws IOException {
        String filename="E:\\2023\\StockServer\\data_2149_20231026.csv";
        return insertLargeVolumeCompanyData(filename);
       
    }
    
    
    @GetMapping(value = "/selectTest")
    void selectTest(){
    	String themevalue = "150775";
    	
    	List<Theme> savedTheme2 = themeJpaService.findTopThemeByThemeEtc(themevalue);
    	System.out.println("existsByThemeEtc"+savedTheme2.get(0).getThemeName());
    	
    	boolean savedTheme = themeJpaService.existsByThemeEtc(themevalue);
    	System.out.println("existsByThemeEtc"+savedTheme);
       // assertEquals(0, hospitalDao.getCount());
    }
    
    @GetMapping(value = "/deleteAllTest")
    void deleteAllTest(){
    	System.out.println("sizee" + companyJpaService.getCompanys());
        companyJpaService.deleteAllCompany();
       // assertEquals(0, hospitalDao.getCount());
    }
    
    @GetMapping(value = "/companythemeSave")
    public String companythemeSave() {
    	List<Company> companyList = companyJpaService.getCompanys();
		System.out.println("회사명sdfsdfsd이 뭘까요? " +companyList.size());
		for(Company company : companyList) {
			companytheme(company);
		}
		return "success";
    }
    
    public void companytheme(String code) {
		String endpoint = "https://finance.finup.co.kr/Stock/"+code;
		Connection conn = Jsoup.connect(endpoint);
		Document document;
		try {
			document = conn.get();
			Elements themeElements = document.select("#ulStockRelationTheme");

		    StringBuilder sb = new StringBuilder();
		    sb.append("company theme starts  ");
		    for (Element element : themeElements) {
		    	for(Element el : element.select("li")) {
		    		String keywordId = el.attr("data-idx");
		    		String themevalue = el.getElementsByClass("label").text();
		    		//logger.debug(themevalue+"   "+keywordId);
		    		Theme theme = new Theme();
		    		theme.setThemeName(themevalue);
		    		theme.setThemeEtc(keywordId);
		    		Theme savedTheme = themeJpaService.createTheme(theme);
		    		//companyThemeReason(company, savedTheme);
		    		sb.append(themevalue);
			        sb.append("   ");
			        sb.append(keywordId);
		    	}
		    	
		    }
		    //logger.debug(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public void companytheme(Company company) {
		String endpoint = "https://finance.finup.co.kr/Stock/"+company.getCompanyCode();
		Connection conn = Jsoup.connect(endpoint);
		Document document;
		try {
			document = conn.get();
			Elements themeElements = document.select("#ulStockRelationTheme");

		    for (Element element : themeElements) {
		    	for(Element el : element.select("li")) {
		    		String keywordId = el.attr("data-idx");
		    		String themevalue = el.getElementsByClass("label").text();
		    		logger.debug("themevalue : "+themevalue+"   "+keywordId);
	
		    		Theme theme = new Theme();
		    		theme.setThemeName(themevalue);
		    		theme.setThemeEtc(keywordId);
		    		Theme savedTheme;
		    		if(themeJpaService.existsByThemeEtc(keywordId)) {
		    			savedTheme = themeJpaService.findTopThemeByThemeEtc(keywordId).get(0);
		    			logger.debug("theme exitsts "+savedTheme.getThemeEtc() + savedTheme.getId());
		    		}else {
		    			savedTheme = theme;
		    			logger.debug("theme doesn't exist "+savedTheme.getThemeEtc());
		    		}
		    		companyThemeReason(company, savedTheme);
		    	}
		    	
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	//연관사유
	public void companyThemeReason(Company company, Theme theme) {
		String endpoint = "https://finance.finup.co.kr/Stock/GetStockRelationThemeData";
		Connection conn = Jsoup.connect(endpoint)
				.header("Content-Type", "multipart/form-data")
				.data("stockCode",company.getCompanyCode())
				.data("keywordIdx",theme.getThemeEtc());;
		
		Document document;
		try {
			document = conn.post();
			System.out.println(document.toString());
			Elements reasonElement = document.select("#divThemeDescription");

		    StringBuilder sb = new StringBuilder();
		    for (Element element : reasonElement) {
		    	String reason = element.text(); 
		    	logger.debug("reason is "+reason);
		    	Reasons reasons = new Reasons();
		    	reasons.setCompany(company);
		    	reasons.setTheme(theme);
		    	reasons.setReason(reason);
		    	try {
		    		reasonsJpaService.createReasons(reasons);
		    	}catch(Exception e) {
		    		e.printStackTrace();
		    		break;
		    	}
		    	
		    	//CPNTHM cpnthm = new CPNTHM();
		    	//cpnthm.setCompanyId(company.getId());
		    	//cpnthm.setThemeId(theme.getId());
		    	//cpnthm.setReason(reason);
		    	//cpnthmJpaService.createCPNTHM(cpnthm);
		    }
		    logger.debug(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	 * 핀업스탁
	 * @return
	 */
	public List<ThemeCaptureChart> parseThemeCaptureChart() {
		String endpoint = "https://stockdata.finup.co.kr/api/ThemeCaptureChart";
		WebClient webClient = WebClient.builder().baseUrl(endpoint).build();
		
		Map<String, Object> bodyMap = new HashMap<>();
		bodyMap.put("CaptureIdx", 10);
		//bodyMap.put("CaptureIdx", 11);

		List<ThemeCaptureChart> webClientResponse = new ArrayList<>();
		try {
			webClientResponse = webClient.post()
                    .bodyValue(bodyMap)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                        throw new RuntimeException("4xx");
                    })
                    .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                        throw new RuntimeException("5xx");
                    })
                    .bodyToMono(new ParameterizedTypeReference<List<ThemeCaptureChart>>() {})
                    .block();
		}catch(Exception e) {
			return null;
		}
		logger.debug("웹클라이언트 결과" + webClientResponse.size() + "   ");
		return webClientResponse;
		
	}
	public void temp() {
		ThemeCaptureChart themeChart = getWebclient().post()
				.uri("/ThemeCaptureChart")
				.bodyValue("{\"CaptureIdx\":10}")
				.retrieve()
				.bodyToMono(ThemeCaptureChart.class)
				.block();
				
				Map<String, String> params = new LinkedHashMap<>();
				params.put("body", "{\"CaptureIdx\":10}");
				
				ThemeResponse resultList = (ThemeResponse) apiResponse("https://stockdata.finup.co.kr/api", params).block();

	}
	
	public Mono<Object> apiResponse(String url, Map<String, String> params){
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.setAll(params);
		
		return WebClient.create()
		.get()
		.uri(url, (uriBuilder -> uriBuilder.queryParams(formData).build()))
		.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		.exchangeToMono(response -> {
			if(response.statusCode() == HttpStatus.OK) {
				return response.bodyToMono(String.class);
			}else {
				System.out.println("api error");
				return Mono.empty();
			}
		});
	}
	
	public WebClient getWebclient() {
		WebClient client = WebClient.builder()
				.baseUrl("https://stockdata.finup.co.kr/api")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
		
		//String response = client.get().uri()
		
		return client;
	}
    
    
    
    @Transactional
    public int insertLargeVolumeCompanyData(String filename) {
        List<Company> companyList;
        try {
        	companyList = readByLine(filename);
            System.out.println("파싱이 끝났습니다.");
            companyList.stream()
                    .parallel()
                    .forEach(company -> {
                        try {
                            companyJpaService.createCompany(company); // db에 insert하는 구간
                        } catch (Exception e) {
                            System.out.printf("id:%s 레코드에 문제가 있습니다.\n",company.getCompanyCode());
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return companyList.size();
    }
    
    public List<Company> readByLine(String filename) throws IOException {
        // 삽
        List<Company> result = new ArrayList<>();
        BufferedReader reader = new BufferedReader(
                new FileReader(filename)
        );
        Charset.forName("UTF-8");
        
        String str;
        while ((str = reader.readLine()) != null) {
            try {
            	if(str.contains("KR")){
            		result.add(parse(str));
            	}
            }catch(Exception e){
            	System.out.println(e);
                System.out.printf("파싱 중 문제가 생겨 이 라인은 넘어갑니다. 파일내용 : %s\n", str);
            }
        }
        reader.close();
        return result;
    }
    public Company parse(String str) {
    	str = str.replaceAll("\"", "");
    	String[] splitted = str.split(",");
        Company company = new Company();
        //company.setId();
        company.setCompanyCode(splitted[1]);
        company.setCompanyName(splitted[2]);
        company.setCompanyShortName(splitted[3]);
        company.setStockListingDate(splitted[5]);
        company.setExchangeMarket(splitted[6]);
        company.setStockType(splitted[9]);
        company.setPerValue(splitted[10]);
        company.setNumberOfStock(splitted[11]);
        System.out.println(company.getCompanyShortName());
        return company;
    }

   	
   	@GetMapping(value = "/getAllNews")
       public List<News> getAllNews(){
       	System.out.println("getAllNews");
       	List<News> newsList = newsJpaService.getNewss();
       	System.out.println("getAllNews " + newsList.size());
          return newsList;
       }
   	
       @GetMapping(value = "/newsTempDB")
       public NaverNews newsLive(){
       	int display = 5;

       	List<Theme> themeList = themeJpaService.getThemes();
       	
       	NaverNews newsList = new NaverNews();
       	for(Theme theme : themeList) {
       		newsList = parseNews(theme.getThemeName(), display);
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
       		}
       	}
       	
          return newsList;
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

   	
   	 @GetMapping(value = "/newsLiveTest")
   	    public NaverNews newsLiveTest(){
   	    	int display = 10;

   	    	String companyName = "삼성전자";
   	    	NaverNews newsList = parseNews(companyName, display);
   	       return newsList;
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


}
