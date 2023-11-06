package com.example.StockServer.ResponseDto;

import java.util.List;

import com.example.StockServer.dao.Company;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
public class ResponseCTRN {
	
	Company company;
	List<ResponseThemeReason> themeReasonList;

}
