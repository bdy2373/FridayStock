package com.example.StockServer.Jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.StockServer.dao.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
 
	// JpaRepository를 상속하여 사용. <객체, ID>

	// TeamRepository.java
	//@Query("SELECT distinct company_name FROM tbl_company t join fetch t.members ")
	//public List<Team> findAllWithMemberUsingFetchJoin();
	

}