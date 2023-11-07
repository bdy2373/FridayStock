package com.example.StockServer.Controller;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.StockServer.Jpa.CompanyJpaService;
import com.example.StockServer.dao.Company;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins="*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/Graph")
public class GraphController {
	
	private static final Logger logger = LoggerFactory.getLogger(GraphController.class);

    private final CompanyJpaService companyJpaService;
	
    @ApiOperation(
            value = "회사 이름을 이용해서 그래프 받아오기"
            , notes = "회사 이름 -> python -> 응답 파일 전송")
    @GetMapping(value = "/getGraph/{companyName}")
    public byte[] getGraph(@PathVariable String companyName) throws IOException {

    	Company findCompany = companyJpaService.findByCompanyShortName(companyName);
    	
    	//cmd python3 실행
    	int result = givenPythonScript_whenPythonProcessExecuted_thenSuccess(findCompany.getCompanyShortName());
    	
    	if (result!=0) {
    		//이미지 경로 받아와서 실행
        	String pathOfImage = "/imgs/"+findCompany.getCompanyCode();
        	if("KOSPI".equalsIgnoreCase(findCompany.getCompanyCode())) {
        		pathOfImage = pathOfImage + ".KS";
        	}else if("KOSDAQ".equalsIgnoreCase(findCompany.getCompanyCode())) {
        		pathOfImage = pathOfImage + ".KQ";
        	}
        	logger.debug("pathOfImage is "+ pathOfImage);
        	
            InputStream in = getClass().getResourceAsStream(pathOfImage);
            
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = in.read(data, 0, data.length)) != -1) {
              buffer.write(data, 0, nRead);
            }
            return buffer.toByteArray();
    	}else {
    		return null;
    	}
    	
    }
    
    //파이썬 돌려주기
    public int givenPythonScript_whenPythonProcessExecuted_thenSuccess(String companyShortName) throws ExecuteException, IOException {
    	String[] command = new String[4];
        command[0] = "python3";
        //command[1] = "\\workspace\\java-call-python\\src\\main\\resources\\test.py";
        command[1] = "yahoo.py";
        command[2] = companyShortName;
        int result = 0;
        try {
            result = execPython(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static int execPython(String[] command) throws IOException, InterruptedException {
        CommandLine commandLine = CommandLine.parse(command[0]);
        for (int i = 1, n = command.length; i < n; i++) {
            commandLine.addArgument(command[i]);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        int result = executor.execute(commandLine);
        logger.debug("result: " + result);
        logger.debug("output: " + outputStream.toString());
        return result;

    }    
    
}
