package com.example.StockServer.Controller;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins="*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/Graph")
public class GraphController {
    @ApiOperation(
            value = "회사 이름을 이용해서 그래프 받아오기"
            , notes = "회사 이름 -> python -> 응답 파일 전송")
    @GetMapping(value = "/getGraph/{companyName}")
    public byte[] getGraph(@PathVariable String companyName) throws IOException {
    	
    	String pathOfImage = "생성된 이미지의 경로";
        InputStream in = getClass().getResourceAsStream("/com/baeldung/produceimage/image.jpg");
        return in.readAllBytes();
    }
}
