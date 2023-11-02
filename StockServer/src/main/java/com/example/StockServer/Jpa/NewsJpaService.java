package com.example.StockServer.Jpa;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.StockServer.dao.News;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsJpaService {

    private final NewsRepository newsRepository;

    /**
     * News 생성
     *
     * @param News
     * @return
     */
    public News createNews(News news) {
        News savedNews = newsRepository.save(news);  // JpaRepository에서 제공하는 save() 함수
        return savedNews;
    }

    /**
     * News 수정
     * JPA Repository의 save Method를 사용하여 객체를 업데이트 할 수 있습니다.
     * Entity News에 @Id로 설정한 키 값이 존재할 경우 해당하는 데이터를 업데이트 해줍니다.
     * 만약 수정하려는 Entity News 객체에 @Id 값이 존재하지 않으면 Insert 되기 때문에
     * 아래와 같이 업데이트 하고자 하는 News가 존재하는지 체크하는 로직을 추가하였습니다.
     *
     * @param News
     * @return
     */
    public News updateNews(News news) {
        News updatedNews = null;
        try {
            News existNews = getNews(news.getId());
            if (!ObjectUtils.isEmpty(existNews)) {
                updatedNews = newsRepository.save(news);  // JpaRepository에서 제공하는 save() 함수
            }
        } catch (Exception e) {
            log.info("[Fail] e: " + e.toString());
        } finally {
            return updatedNews;
        }
    }

    /**
     * News List 조회
     * 
     * @return
     */
    public List<News> getNewss() {
        return newsRepository.findAll();  // JpaRepository에서 제공하는 findAll() 함수
    }
    

    /**
     * Id에 해당하는 News 조회
     * 
     * @param id
     * @return
     */
    public News getNews(Integer id) {
        return newsRepository.getById(id);  // JpaRepository에서 제공하는 getById() 함수
    }
    
 


    /**
     * Id에 해당하는 News 삭제
     * 
     * @param id
     */
    public void deleteNews(Integer id) {
    	newsRepository.deleteById(id);  // JpaRepository에서 제공하는 deleteById() 함수
    }
    
    /**
     * Id에 해당하는 News 삭제
     * 
     * @param id
     */
    public void deleteAllNews() {
    	newsRepository.deleteAll();  
    }
    
    
}
