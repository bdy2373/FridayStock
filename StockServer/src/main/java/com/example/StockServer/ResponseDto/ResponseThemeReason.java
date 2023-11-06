package com.example.StockServer.ResponseDto;

import java.util.List;

import com.example.StockServer.dao.News;
import com.example.StockServer.dao.Theme;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ResponseThemeReason{
	Theme theme;
	String reason;
	List<News> newsList;
}