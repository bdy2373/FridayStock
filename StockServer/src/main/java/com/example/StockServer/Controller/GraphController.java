package com.example.StockServer.Controller;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
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
            value = "이미지 테스트용 "
            , notes = "이미지 테스트용 ")
    @GetMapping(value = "/testImage", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] testImage() throws IOException {
    	
    	String pathOfImage = "/root/FridayStock/StockServer/imgs/352820.KS.png";
 
        logger.debug("pathOfImage is "+ pathOfImage);
        
        BufferedImage image;
        //로컬 파일을 사용하는 경우 
        File imageFile = new File(pathOfImage);
        image = ImageIO.read(imageFile);
        
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", stream);
        } catch(IOException e) {
            // This *shouldn't* happen with a ByteArrayOutputStream, but if it
            // somehow does happen, then we don't want to just ignore it
            throw new RuntimeException(e);
        }
        return stream.toByteArray();
       
    	
    }
    
    @ApiOperation(
            value = "path 확인용 "
            , notes = "path 확인용 ")
    @GetMapping(value = "/testPath")
    public String testPath() throws IOException {
    	
    	Path path = Paths.get("");
        return path.toString();
       
    	
    }
    
    @ApiOperation(
            value = "회사 이름을 이용해서 그래프 받아오기 - 로컬 확인용"
            , notes = "회사 이름 -> python -> 응답 파일 전송")
    @GetMapping(value = "/getGraphLocal/{companyName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getGraphLocal(@PathVariable String companyName) throws IOException {

    	Company findCompany = companyJpaService.findByCompanyShortName(companyName);
    	
    	//cmd python3 실행
    	givenPythonScript_whenPythonProcessExecuted_thenSuccess(findCompany.getCompanyShortName());
    	Path path = Paths.get("");
    	System.out.println("절대경로" + path.toAbsolutePath().toString());
    	//이미지 경로 받아와서 실행
        String pathOfImage = "./imgs/"+findCompany.getCompanyCode();
        if("KOSPI".equalsIgnoreCase(findCompany.getExchangeMarket())) {
        	pathOfImage = pathOfImage + ".KS.png";
        }else if("KOSDAQ".equalsIgnoreCase(findCompany.getExchangeMarket())) {
        	pathOfImage = pathOfImage + ".KQ.png";
        }else {
        	return null;
        }
        logger.debug("pathOfImage is "+ pathOfImage);
    	
        BufferedImage image;
        //로컬 파일을 사용하는 경우 
        File imageFile = new File(pathOfImage);
        setPermission(imageFile , true, true, true);
        while(!imageFile.exists()) {
        	try {
				Thread.sleep(100);
				if(imageFile.exists()) break;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        image = ImageIO.read(imageFile);
        
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", stream);
        } catch(IOException e) {
            // This *shouldn't* happen with a ByteArrayOutputStream, but if it
            // somehow does happen, then we don't want to just ignore it
            throw new RuntimeException(e);
        }
        return stream.toByteArray();
        
    	
    }
	
    @ApiOperation(
            value = "회사 이름을 이용해서 그래프 받아오기"
            , notes = "회사 이름 -> python -> 응답 파일 전송")
    @GetMapping(value = "/getGraph/{companyName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getGraph(@PathVariable String companyName) throws IOException {

    	Company findCompany = companyJpaService.findByCompanyShortName(companyName);
    	
    	//cmd python3 실행
    	givenPythonScript_whenPythonProcessExecuted_thenSuccess(findCompany.getCompanyShortName());
    	Path path = Paths.get("");
    	logger.debug("절대경로" + path.toAbsolutePath().toString());
    	//이미지 경로 받아와서 실행
        String pathOfImage = "/root/FridayStock/StockServer/imgs/"+findCompany.getCompanyCode();
        if("KOSPI".equalsIgnoreCase(findCompany.getExchangeMarket())) {
        	pathOfImage = pathOfImage + ".KS.png";
        }else if("KOSDAQ".equalsIgnoreCase(findCompany.getExchangeMarket())) {
        	pathOfImage = pathOfImage + ".KQ.png";
        }else {
        	return null;
        }
        
        logger.debug("pathOfImage is "+ pathOfImage);
    	
        BufferedImage image;
        //로컬 파일을 사용하는 경우 
        File imageFile = new File(pathOfImage);
        setPermission(imageFile , true, true, true);
        while(!imageFile.exists()) {
        	try {
				Thread.sleep(100);
				if(imageFile.exists()) break;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        image = ImageIO.read(imageFile);
        
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", stream);
        } catch(IOException e) {
            // This *shouldn't* happen with a ByteArrayOutputStream, but if it
            // somehow does happen, then we don't want to just ignore it
            throw new RuntimeException(e);
        }
        return stream.toByteArray();
        
    	
    }
    private boolean setPermission(File target , boolean read, boolean write, boolean exe) {
		try {
			target.setReadable(read);
			target.setWritable(write);
			target.setExecutable(exe);
			return true;
			
		} catch (Exception e) { 
			e.printStackTrace(); 
			return false; 
		}
	}
    
    //파이썬 돌려주기
    public void givenPythonScript_whenPythonProcessExecuted_thenSuccess(String companyShortName) throws ExecuteException, IOException {
    	logger.debug("companyShortName" + companyShortName);
    	String[] command = new String[3];
        command[0] = "python3";
        command[1] = "/root/FridayStock/StockServer/yahoo.py";
        command[2] = companyShortName;
        
        String[] command2 = new String[2];
        command2[0] = "cd";
        command2[1] = "/root/FridayStock/StockServer/";
      
        try {
        	//execPython(command2);
        	execPython(command);
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void execPython(String[] command) throws IOException, InterruptedException {
    	System.out.println("command 출력"+command[0]);
        CommandLine commandLine = CommandLine.parse(command[0]);
        int n = command.length;
        for (int i = 1; i < n; i++) {
            commandLine.addArgument(command[i]);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        int result = executor.execute(commandLine);
        logger.debug("result: " + result);
        logger.debug("output: " + outputStream.toString());
     
    }    
    
}
