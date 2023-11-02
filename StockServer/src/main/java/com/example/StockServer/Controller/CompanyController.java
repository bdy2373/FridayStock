package com.example.StockServer.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.StockServer.Jpa.CompanyJpaService;
import com.example.StockServer.Jpa.ReasonsJpaService;
import com.example.StockServer.Jpa.ThemeJpaService;
import com.example.StockServer.dao.Company;
import com.example.StockServer.dao.Reasons;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/Company")
public class CompanyController {
	
	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
	

    private final CompanyJpaService companyJpaService;
	private final ThemeJpaService themeJpaService;
	private final ReasonsJpaService reasonsJpaService;
	
    @ApiOperation(
            value = "회사이름을 이용하여 회사 정보 검색"
            , notes = "회사 이름(풀네임) LIKE 검색 지원")
    @GetMapping(value = "/findByCompanyNameContaining/{companyName}")
    public List<Company> findByCompanyNameContaining(@PathVariable String companyName){
    	System.out.println("company name is? "+companyName);
    	List<Company> companyList = companyJpaService.findByCompanyNameContaining(companyName);
    	System.out.println("findByCompanyNameContaining " + companyList.size());
       return companyList;
    }
    
    @ApiOperation(
            value = "회사요약명을 이용하여 회사 정보 검색"
            , notes = "회사 이름(요약명) LIKE 검색 지원")
    @GetMapping(value = "/findByCompanyNameContaining/{companyShortName}")
    public List<Company> findByCompanyShortNameContaining(@PathVariable String companyShortName){
    	System.out.println("company name is? "+companyShortName);
    	List<Company> companyList = companyJpaService.findByCompanyNameContaining(companyShortName);
    	System.out.println("findByCompanyNameContaining " + companyList.size());
       return companyList;
    }
    
    @ApiOperation(
            value = "회사종목코드를 이용하여 회사 정보 검색"
            , notes = "종목코드를 정확하게 입력해야 나옴")
    @GetMapping(value = "/findByCompanyCode/{companyCode}")
    public Company getCompany(@PathVariable String companyCode){
    	System.out.println("company code is? "+companyCode);
    	Company findCompany = companyJpaService.findByCompanyCode(companyCode);
    	System.out.println("getCompanyCode " + findCompany.getCompanyName());
        return companyJpaService.findByCompanyCode(companyCode);
       // assertEquals(0, hospitalDao.getCount());
    }
    
    @ApiOperation(
            value = "회사종목코드를 이용하여 회사 정보 검색 - 심화 (테마까지 출력됨)"
            , notes = "종목코드를 정확하게 입력해야 나옴")
    @GetMapping(value = "/getCompanyTheme/{companyCode}")
    public List<Reasons> getCompanyTheme(@PathVariable String companyCode){
    	System.out.println("company code is? "+companyCode);
    	Company findCompany = companyJpaService.findByCompanyCode(companyCode);
    	List<Reasons> reasons = reasonsJpaService.getReasonsWithCompany(findCompany);
    	System.out.println("getCompanyCode " + reasons.size());
        return reasons;
    }

}
