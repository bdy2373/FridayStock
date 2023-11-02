package com.example.StockServer.Jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.StockServer.dao.Company;
import com.example.StockServer.dao.Reasons;

@Repository
public interface ReasonsRepository extends JpaRepository<Reasons, Integer> {  // JpaRepository를 상속하여 사용. <객체, ID>

	List<Reasons> findAllByCompany(Company company);
}