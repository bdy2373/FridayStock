//package com.example.StockServer;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.io.IOException;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.example.StockServer.Jpa.CompanyRepository;
//import com.example.StockServer.Jpa.CompanyJpaService;
//import com.example.StockServer.dao.Company;
//
//@SpringBootTest
//public class JpaTest {
//
//    @Autowired
//    private CompanyRepository companyRepository;
//
//    @Test
//    void post() {
//
//    	Company company = Company.builder().companyName("test").companyCode("test").build();
//
//    	Company saverCompany = companyRepository.save(company);
//
//        Assertions.assertThat(company.getCompanyName()).isEqualTo(saverCompany.getCompanyName());
//        
//        System.out.println("아이디확인 :" + saverCompany.getId());
//        
//        //companyRepository.deleteAll();
//    }
//    
//    
//}
