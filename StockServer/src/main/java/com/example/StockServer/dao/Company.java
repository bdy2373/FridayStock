package com.example.StockServer.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
/**
 * CREATE TABLE IF NOT EXISTS `db-test-dayoung`.TBL_COMPANY (
	ID INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    COMPANY_CODE VARCHAR(20) NOT NULL UNIQUE,
    COMPANY_NAME VARCHAR(128) NOT NULL,
    COMPANY_SHORT_NAME VARCHAR(30),
    STOCK_LISTING_DATE VARCHAR(20),
    EXCHANGE_MARKET VARCHAR(20) COMMENT "KOSPI/KOSDAQ",
    STOCK_TYPE VARCHAR(20) COMMENT "보통주/우량주...",
	PER_VALUE VARCHAR(20) COMMENT "액면가",
    NUMBER_OF_STOCK BIGINT(255) UNSIGNED COMMENT "상장주식수"
);
 * @author bdy23
 *
 */
@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // JPA에서 lazy관련 에러 날 경우 사용
@Entity  // 객체와 테이블 매핑
@Table(name = "TBL_COMPANY")  // 테이블 지정
public class Company {
    @Id  // Primary Key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // AUTO_INCREMENT 설정 (id값이 null일 경우 자동 생성)
    @Column(name = "ID")  // 컬럼 지정
    private Integer id;

    @NotNull
    @Column(name = "COMPANY_CODE")
    private String companyCode;
    
    @NotNull
    @Column(name = "COMPANY_NAME")
    private String companyName;
    
    @Column(name = "COMPANY_SHORT_NAME")
    private String companyShortName;
    
    @Column(name = "STOCK_LISTING_DATE")
    private String stockListingDate;
    
    @Column(name = "EXCHANGE_MARKET")
    private String exchangeMarket;
    
    @Column(name = "STOCK_TYPE")
    private String stockType;
    
    @Column(name = "PER_VALUE")
    private String perValue;
    
    @Column(name = "NUMBER_OF_STOCK")
    private String numberOfStock;

	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}
    
}