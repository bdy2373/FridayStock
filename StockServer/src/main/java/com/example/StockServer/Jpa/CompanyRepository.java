package com.example.StockServer.Jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.StockServer.dao.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
 
	// JpaRepository를 상속하여 사용. <객체, ID>
	
	Company findByCompanyCode(String companyCode);
	List<Company> findByCompanyNameContaining(String companyName);
	
	Company findByCompanyShortName(String companyName);
	List<Company> findByCompanyShortNameContaining(String companyShortName);
	
	
	// TeamRepository.java
	//@Query("SELECT distinct company_name FROM tbl_company t join fetch t.members ")
	//public List<Team> findAllWithMemberUsingFetchJoin();

}