package com.example.StockServer.Response;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * "lastBuildDate": "Mon, 30 Oct 2023 01:20:57 +0900",
    "total": 103418,
    "start": 1,
    "display": 10,
    "items": [
        {
            "title": "<b>이재명</b>, 선거법 위반 혐의 재판 또 불출석…李 없이 진행",
            "originallink": "https://www.yna.co.kr/view/AKR20231027064000004?input=1195m",
            "link": "https://n.news.naver.com/mnews/article/001/0014291948?sid=102",
            "description": "'국정감사' 사유…이달 13일 이어 두번 연속 출석 안해 더불어민주당 <b>이재명</b> 대표가 27일 공직선거법 위반... 여파와 <b>구속</b>영장 청구 등으로 재판부가 기일을 변경했다. 이 대표는 지난 대선 직전인 2021년 대장동... ",
            "pubDate": "Fri, 27 Oct 2023 11:29:00 +0900"
        },
        {
            "title": "‘선거법 위반’ <b>이재명</b> 또 불출석…재판은 그대로 진행",
            "originallink": "https://news.kbs.co.kr/news/pc/view/view.do?ncd=7803837&ref=A",
            "link": "https://n.news.naver.com/mnews/article/056/0011590709?sid=102",
            "description": "<b>이재명</b> 더불어민주당 대표가 자신의 공직선거법 위반 혐의 재판에 또 한 번 출석하지 않았지만, 재판은 이... 이 대표의 공직선거법 위반 혐의 재판은 지난 8일과 22일에도 진행될 예정이었지만, 이 대표의 단식과 <b>구속</b>영... ",
            "pubDate": "Fri, 27 Oct 2023 15:45:00 +0900"
        },
 * @author bdy23
 *
 */

@ToString
@Getter
@Setter
//@Builder
public class NaverNews {


	String lastBuildDate;
	int total;
	int start;
	int display;
	
	private List<Items> items = new ArrayList<>();
	
	@Getter
    @Setter
    @ToString
    //@Builder
    public static class Items{
        private String title;
        private String originallink;
        private String link;
        private String description;
        private String pubDate;

    }
}
