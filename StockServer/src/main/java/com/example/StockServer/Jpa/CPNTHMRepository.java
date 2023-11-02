package com.example.StockServer.Jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.StockServer.dao.CPNTHM;

@Repository
public interface CPNTHMRepository extends JpaRepository<CPNTHM, Integer> {  // JpaRepository를 상속하여 사용. <객체, ID>
}