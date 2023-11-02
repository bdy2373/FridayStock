package com.example.StockServer.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.StockServer.Jpa.CompanyJpaService;
import com.example.StockServer.Jpa.ThemeJpaService;
import com.example.StockServer.dao.Theme;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins="*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/Theme")
public class ThemeController {
	
	private static final Logger logger = LoggerFactory.getLogger(ThemeController.class);
	

    private final CompanyJpaService companyJpaService;
	private final ThemeJpaService themeJpaService;
	
    @ApiOperation(
            value = "테마 전체 조회"
            , notes = "테마 전체 조회")
	@GetMapping(value = "/getAllThemes")
    public List<Theme> getAllThemes(){
    	System.out.println("getAllThemes");
    	List<Theme> ThemeList = themeJpaService.getThemes();
    	System.out.println("getAllThemes " + ThemeList.size());
       return ThemeList;
    }
	
   
}
