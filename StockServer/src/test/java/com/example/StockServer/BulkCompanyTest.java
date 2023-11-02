//package com.example.StockServer;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import javax.transaction.Transactional;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.example.StockServer.Jpa.CompanyJpaService;
//import com.example.StockServer.Jpa.ThemeJpaService;
//import com.example.StockServer.dao.Company;
//import com.example.StockServer.dao.Theme;
//
//import lombok.RequiredArgsConstructor;
//
////@DataJpaTest
//@SpringBootTest
//@RequiredArgsConstructor
//public class BulkCompanyTest {
//
//	@Autowired
//    private final CompanyJpaService companyService;
//    
//	@Autowired
//    private final ThemeJpaService themeJpaService;
//    
//
//    @Test
//    @DisplayName("existsByThemeEtc")
//    @Autowired
//    void existsByThemeEtc() throws IOException {
//    	
//    	String themevalue = "150775";
//    	
//    	
//    	List<Theme> savedTheme2 = themeJpaService.findTopThemeByThemeEtc(themevalue);
//    	System.out.println("existsByThemeEtc"+savedTheme2);
//    	
//    	boolean savedTheme = themeJpaService.existsByThemeEtc(themevalue);
//    	System.out.println("existsByThemeEtc"+savedTheme);
//    	
//    
//       
//    }
//    
//    @Test
//    @DisplayName("addAllTest")
//    void addAllTest() throws IOException {
//        String filename="E:\\2023\\StockServer\\data_2149_20231026.csv";
//        insertLargeVolumeCompanyData(filename);
//       
//    }
//    
//    @Test
//    @DisplayName("deleteAllTest")
//    void deleteAllTest(){
//    	System.out.println("sizee" + companyService.getCompanys());
//        companyService.deleteAllCompany();
//       // assertEquals(0, hospitalDao.getCount());
//    }
//    
//    
//    
//    @Transactional
//    public int insertLargeVolumeCompanyData(String filename) {
//        List<Company> companyList;
//        try {
//        	companyList = readByLine(filename);
//            System.out.println("파싱이 끝났습니다.");
//            companyList.stream()
//                    .parallel()
//                    .forEach(company -> {
//                        try {
//                            companyService.createCompany(company); // db에 insert하는 구간
//                        } catch (Exception e) {
//                            System.out.printf("id:%s 레코드에 문제가 있습니다.\n",company.getCompanyCode());
//                            throw new RuntimeException(e);
//                        }
//                    });
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        if (!Optional.of(companyList).isEmpty()) {
//            return companyList.size();
//        } else {
//            return 0;
//        }
//    }
//    
//    public List<Company> readByLine(String filename) throws IOException {
//        // 삽
//        List<Company> result = new ArrayList<>();
//        BufferedReader reader = new BufferedReader(
//                new FileReader(filename)
//        );
//        Charset.forName("UTF-8");
//        
//        String str;
//        while ((str = reader.readLine()) != null) {
//            try {
//            	if(str.contains("KR")){
//            		result.add(parse(str));
//            	}
//            }catch(Exception e){
//            	System.out.println(e);
//                System.out.printf("파싱 중 문제가 생겨 이 라인은 넘어갑니다. 파일내용 : %s\n", str);
//            }
//        }
//        reader.close();
//        return result;
//    }
//    public Company parse(String str) {
//    	str = str.replaceAll("\"", "");
//    	String[] splitted = str.split(",");
//        Company company = new Company();
//        //company.setId();
//        company.setCompanyCode(splitted[1]);
//        company.setCompanyName(splitted[2]);
//        company.setCompanyShortName(splitted[3]);
//        company.setStockListingDate(splitted[5]);
//        company.setExchangeMarket(splitted[6]);
//        company.setStockType(splitted[9]);
//        company.setPerValue(splitted[10]);
//        company.setNumberOfStock(splitted[11]);
//        System.out.println(company.getCompanyShortName());
//        return company;
//    }
//}
