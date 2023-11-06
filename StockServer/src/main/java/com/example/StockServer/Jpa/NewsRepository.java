package com.example.StockServer.Jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.StockServer.dao.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
 
	// JpaRepository를 상속하여 사용. <객체, ID>

	// TeamRepository.java
	//@Query("SELECT distinct company_name FROM tbl_company t join fetch t.members ")
	//public List<Team> findAllWithMemberUsingFetchJoin();
	
	//@Query("SELECT c.themeId =  company_name FROM tbl_company c t join fetch t.members ")
	List<News> findTop7ByThemeId(int themeId);

}