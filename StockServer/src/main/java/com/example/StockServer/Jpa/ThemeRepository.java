package com.example.StockServer.Jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.StockServer.dao.Theme;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Integer> {  // JpaRepository를 상속하여 사용. <객체, ID>
	
	List<Theme> findTopByThemeEtc(String themeEtc);
	
	boolean existsByThemeEtc(String themeEtc);

}