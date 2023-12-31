package com.example.StockServer.Controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.StockServer.Jpa.CompanyJpaService;
import com.example.StockServer.Jpa.NewsJpaService;
import com.example.StockServer.Jpa.ReasonsJpaService;
import com.example.StockServer.Jpa.ThemeJpaService;
import com.example.StockServer.ResponseDto.ResponseCTRN;
import com.example.StockServer.ResponseDto.ResponseThemeReason;
import com.example.StockServer.dao.Company;
import com.example.StockServer.dao.News;
import com.example.StockServer.dao.Reasons;
import com.example.StockServer.dao.Theme;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins="*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/Company")
public class CompanyController {
	
	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
	

    private final CompanyJpaService companyJpaService;
	private final ThemeJpaService themeJpaService;
	private final ReasonsJpaService reasonsJpaService;
	private final NewsJpaService newsJpaService;
	
    @ApiOperation(
            value = "회사이름을 이용하여 회사 정보 검색"
            , notes = "회사 이름(풀네임) LIKE 검색 지원")
    @GetMapping(value = "/findByCompanyNameContaining/{companyName}")
    public List<Company> findByCompanyNameContaining(@PathVariable String companyName){
    	logger.debug("company name is? "+companyName);
    	List<Company> companyList = companyJpaService.findByCompanyNameContaining(companyName);
    	logger.debug("findByCompanyNameContaining " + companyList.size());
       return companyList;
    }
    
    @ApiOperation(
            value = "회사요약명을 이용하여 회사 정보 검색"
            , notes = "회사 이름(요약명) LIKE 검색 지원")
    @GetMapping(value = "/findByCompanyShortNameContaining/{companyShortName}")
    public List<Company> findByCompanyShortNameContaining(@PathVariable String companyShortName){
    	logger.debug("company name is? "+companyShortName);
    	List<Company> companyList = companyJpaService.findByCompanyShortNameContaining(companyShortName);
    	logger.debug("findByCompanyShortNameContaining " + companyList.size());
       return companyList;
    }
    
    @ApiOperation(
            value = "회사종목코드를 이용하여 회사 정보 검색"
            , notes = "종목코드를 정확하게 입력해야 나옴")
    @GetMapping(value = "/findByCompanyCode/{companyCode}")
    public Company getCompany(@PathVariable String companyCode){
    	logger.debug("company code is? "+companyCode);
    	Company findCompany = companyJpaService.findByCompanyCode(companyCode);
    	logger.debug("getCompanyCode " + findCompany.getCompanyName());
        return companyJpaService.findByCompanyCode(companyCode);
       // assertEquals(0, hospitalDao.getCount());
    }
    
    @ApiOperation(
            value = "회사종목코드를 이용하여 회사 정보 검색 - 심화 (테마까지 출력됨)"
            , notes = "종목코드를 정확하게 입력해야 나옴")
    @GetMapping(value = "/getCompanyTheme/{companyCode}")
    public List<Reasons> getCompanyTheme(@PathVariable String companyCode){
    	logger.debug("company code is? "+companyCode);
    	Company findCompany = companyJpaService.findByCompanyCode(companyCode);
    	List<Reasons> reasons = reasonsJpaService.getReasonsWithCompany(findCompany);
    	logger.debug("getCompanyCode " + reasons.size());
        return reasons;
    }
    
    @ApiOperation(
            value = "회사종목코드를 이용하여 회사 정보 검색 - 심화 (news 까지 죄다 호출됨)"
            , notes = "종목코드를 정확하게 입력해야 나옴")
    @GetMapping(value = "/getCompanyThemeReasonNews/{companyCode}")
    public ResponseCTRN getCompanyThemeReasonNews(@PathVariable String companyCode){
    	logger.debug("company code is? "+companyCode);
    	
    	ResponseCTRN response = new ResponseCTRN();
    	
    	Company findCompany = companyJpaService.findByCompanyCode(companyCode);
    	response.setCompany(findCompany);
    	
    	List<Reasons> reasons = reasonsJpaService.getReasonsWithCompany(findCompany);
    	List<ResponseThemeReason> rtrList = new ArrayList<>();
    	for(Reasons reason : reasons) {
    		ResponseThemeReason themeReason = new ResponseThemeReason();
    		themeReason.setReason(reason.getReason());
    		Theme themeTemp = reason.getTheme();
    		themeReason.setTheme(themeTemp);
    		List<News> newsList = newsJpaService.getTop7Newss(themeTemp.getId());
    		themeReason.setNewsList(newsList);
    		rtrList.add(themeReason);
    	}
    	response.setThemeReasonList(rtrList);
    	
    	logger.debug("getCompanyCode " + reasons.size());
        return response;
    }
    
    @ApiOperation(
            value = "회사종목코드를 이용하여 회사 정보 검색 - 심화 (news 까지 죄다 호출됨)"
            , notes = "종목명을 정확하게 입력해야 나옴")
    @GetMapping(value = "/getCompanyThemeReasonNews2/{companyName}")
    public ResponseCTRN getCompanyThemeReasonNews2(@PathVariable String companyName){
    	logger.debug("company name is? "+companyName);
    	
    	ResponseCTRN response = new ResponseCTRN();
    	
    	//Company findCompany = companyJpaService.findByCompanyCode(companyCode);
    	Company findCompany = companyJpaService.findByCompanyShortName(companyName);
    	response.setCompany(findCompany);
    	
    	List<Reasons> reasons = reasonsJpaService.getReasonsWithCompany(findCompany);
    	List<ResponseThemeReason> rtrList = new ArrayList<>();
    	for(Reasons reason : reasons) {
    		ResponseThemeReason themeReason = new ResponseThemeReason();
    		themeReason.setReason(reason.getReason());
    		Theme themeTemp = reason.getTheme();
    		themeReason.setTheme(themeTemp);
    		List<News> newsList = newsJpaService.getTop7Newss(themeTemp.getId());
    		themeReason.setNewsList(newsList);
    		rtrList.add(themeReason);
    	}
    	response.setThemeReasonList(rtrList);
    	
    	logger.debug("getCompanyCode " + reasons.size());
        return response;
    }
    
    @ApiOperation(
            value = "회사 전체 조회"
            , notes = "회사 전체 조회")
	@GetMapping(value = "/getAllCompanies")
    public List<Company> getAllCompanies(){
    	logger.debug("getAllCompanies");
    	List<Company> companyList = companyJpaService.getCompanys();
    	logger.debug("getAllCompanies " + companyList.size());
       return companyList;
    }
    

}
