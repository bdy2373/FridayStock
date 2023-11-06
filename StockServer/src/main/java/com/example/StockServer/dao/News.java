package com.example.StockServer.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // JPA에서 lazy관련 에러 날 경우 사용
@Entity  // 객체와 테이블 매핑
@Table(name = "TBL_NEWS")  // 테이블 지정
public class News {
    @Id  // Primary Key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // AUTO_INCREMENT 설정 (id값이 null일 경우 자동 생성)
    @Column(name = "ID")  // 컬럼 지정
    private Integer id;

    @NotNull
    @Column(name = "NEWS_TITLE")
    private String newsTitle;

    @Column(name = "NEWS_LINK")
    private String newsLink;
    
    @Column(name = "NEWS_ORIGINAL_LINK")
    private String newsOriginalLink;
    
    @Column(name = "NEWS_PUBDATE")
    private String pubDate;
    
    @Column(name = "NEWS_DESCRIPTION")
    private String description;
    
    @JsonIgnore
    @ManyToOne // Many = News, One = Theme
	@JoinColumn(name="THEME_ID") // foreign key (THEME_ID) references User (ID)
	private Theme theme; // DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다. //참조 할 테이블
    
    @Column(name = "NEWS_REASON")
    private String reason;


	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}
}