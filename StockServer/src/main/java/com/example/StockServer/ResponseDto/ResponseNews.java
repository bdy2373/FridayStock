package com.example.StockServer.ResponseDto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.StockServer.dao.Theme;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
public class ResponseNews{
    private Integer id;

    private String newsTitle;

    private String newsLink;
    
    private String newsOriginalLink;
    
    private String pubDate;
    
    private String description;
    
    private String reason;


	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}
}