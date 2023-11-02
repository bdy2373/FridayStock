package com.example.StockServer.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *     {
        "KeywordIdx": 152119,
        "Keyword": "123456123",
        "Diff": -11.81,
        "Score": 53.6,
        "Percentage": 144.9,
        "CaptureItemIdx": 20231020152950,
        "CaptureDT": "2023-10-20 15:29:50",
        "Rank": 1,
        "New": 0,
        "Hot": 0
    }
    
 * @author bdy23
 *
 */
@ToString
@Getter
@Setter
@Builder
public class ThemeCaptureChart {
	String KeywordIdx;
	String Keyword;
	String Diff;
	String Score;
	String Percentage;
	String CaptureItemIdx;
	String Rank;
	String New;
	String Hot;
}
