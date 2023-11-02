package com.example.StockServer.Jpa;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.StockServer.dao.Company;
import com.example.StockServer.dao.Reasons;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReasonsJpaService {

	@Autowired
    ReasonsRepository reasonRepository;

    /**
     * Reasons 생성
     *
     * @param Reasons
     * @return
     */
    public Reasons createReasons(Reasons reason) {
        Reasons savedReasons = reasonRepository.save(reason);  // JpaRepository에서 제공하는 save() 함수
        return savedReasons;
    }

    /**
     * Reasons 수정
     * JPA Repository의 save Method를 사용하여 객체를 업데이트 할 수 있습니다.
     * Entity Reasons에 @Id로 설정한 키 값이 존재할 경우 해당하는 데이터를 업데이트 해줍니다.
     * 만약 수정하려는 Entity Reasons 객체에 @Id 값이 존재하지 않으면 Insert 되기 때문에
     * 아래와 같이 업데이트 하고자 하는 Reasons가 존재하는지 체크하는 로직을 추가하였습니다.
     *
     * @param Reasons
     * @return
     */
    public Reasons updateReasons(Reasons reason) {
        Reasons updatedReasons = null;
        try {
            Reasons existReasons = getReasons(reason.getId());
            if (!ObjectUtils.isEmpty(existReasons)) {
                updatedReasons = reasonRepository.save(reason);  // JpaRepository에서 제공하는 save() 함수
            }
        } catch (Exception e) {
            log.info("[Fail] e: " + e.toString());
        } finally {
            return updatedReasons;
        }
    }

    /**
     * Reasons List 조회
     * 
     * @return
     */
    public List<Reasons> getReasonss() {
        return reasonRepository.findAll();  // JpaRepository에서 제공하는 findAll() 함수
    }
    
    public List<Reasons> getReasonsWithCompany(Company company) {
        return reasonRepository.findAllByCompany(company);  // JpaRepository에서 제공하는 findAll() 함수
    }

    /**
     * Id에 해당하는 Reasons 조회
     * 
     * @param id
     * @return
     */
    public Reasons getReasons(Integer id) {
        return reasonRepository.getById(id);  // JpaRepository에서 제공하는 getById() 함수
    }

    /**
     * Id에 해당하는 Reasons 삭제
     * 
     * @param id
     */
    public void deleteReasons(Integer id) {
    	reasonRepository.deleteById(id);  // JpaRepository에서 제공하는 deleteById() 함수
    }
}
