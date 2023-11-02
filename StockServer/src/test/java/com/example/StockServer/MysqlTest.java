package com.example.StockServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.Test;

public class MysqlTest {

 private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
 private static final String URL = "jdbc:mysql://db-j70hi.pub-cdb.ntruss.com:3306/db-test-dayoung"; // jdbc:mysql://127.0.0.1:3306/�������� ����� ��Ű���̸�
 private static final String USER = "BDY_APP"; //DB ����ڸ�
 private static final String PW = "denalid@022";   //DB ����� ��й�ȣ
 
 @Test
 public void testConnection() throws Exception{
  Class.forName(DRIVER);
  
  try(Connection con = DriverManager.getConnection(URL, USER, PW)){
   System.out.println("스키마 "+con.getSchema());
   System.out.println("카탈로그 "+con.getCatalog());
   System.out.println("정보 "+con.nativeSQL("select * from `db-test-dayoung`.TBL_COMPANY;"));
   
   PreparedStatement psmt1 = con.prepareStatement("show tables");
   ResultSet row1 = psmt1.executeQuery(); 
   System.out.println("DB에서 변화가 반영된 ROW 수 :" + row1.getFetchSize());
   
   PreparedStatement psmt = con.prepareStatement("select * from `db-test-dayoung`.TBL_COMPANY;");
   ResultSet row = psmt.executeQuery(); 
   System.out.println("DB에서 변화가 반영된 ROW 수 :" + row.getFetchSize());
   
   System.out.println(con);
  }catch (Exception e) {
   System.out.println("�����߻�");
   e.printStackTrace();
  }
 }

}