package com.example.StockServer.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
/**
 * CREATE TABLE IF NOT EXISTS `db-test-dayoung`.TBL_CPN_THM (
	ID INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    COMPANY_ID INTEGER(10) NOT NULL references TBL_COMPANY(ID),
    THEME_ID INTEGER(10) NOT NULL references TBL_THEME(ID),
    REASON VARCHAR(256)
);

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
@Table(name = "TBL_CPN_THM")  // 테이블 지정
public class CPNTHM {
    @Id  // Primary Key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // AUTO_INCREMENT 설정 (id값이 null일 경우 자동 생성)
    @Column(name = "ID")  // 컬럼 지정
    private Integer id;

    @NotNull
    @Column(name = "COMPANY_ID")
    private Integer companyId;
    
    @NotNull
    @Column(name = "THEME_ID")
    private Integer themeId;
    
    @Column(name = "REASON", length = 10000)
    private String reason;

	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}
    
}