package com.example.StockServer.Jpa;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.StockServer.dao.CPNTHM;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CPNTHMJpaService {

	@Autowired
    CPNTHMRepository cpnthmRepository;

    /**
     * CPNTHM 생성
     *
     * @param CPNTHM
     * @return
     */
    public CPNTHM createCPNTHM(CPNTHM cpnthm) {
        CPNTHM savedCPNTHM = cpnthmRepository.save(cpnthm);  // JpaRepository에서 제공하는 save() 함수
        return savedCPNTHM;
    }

    /**
     * CPNTHM 수정
     * JPA Repository의 save Method를 사용하여 객체를 업데이트 할 수 있습니다.
     * Entity CPNTHM에 @Id로 설정한 키 값이 존재할 경우 해당하는 데이터를 업데이트 해줍니다.
     * 만약 수정하려는 Entity CPNTHM 객체에 @Id 값이 존재하지 않으면 Insert 되기 때문에
     * 아래와 같이 업데이트 하고자 하는 CPNTHM가 존재하는지 체크하는 로직을 추가하였습니다.
     *
     * @param CPNTHM
     * @return
     */
    public CPNTHM updateCPNTHM(CPNTHM cpnthm) {
        CPNTHM updatedCPNTHM = null;
        try {
            CPNTHM existCPNTHM = getCPNTHM(cpnthm.getId());
            if (!ObjectUtils.isEmpty(existCPNTHM)) {
                updatedCPNTHM = cpnthmRepository.save(cpnthm);  // JpaRepository에서 제공하는 save() 함수
            }
        } catch (Exception e) {
            log.info("[Fail] e: " + e.toString());
        } finally {
            return updatedCPNTHM;
        }
    }

    /**
     * CPNTHM List 조회
     * 
     * @return
     */
    public List<CPNTHM> getCPNTHMs() {
        return cpnthmRepository.findAll();  // JpaRepository에서 제공하는 findAll() 함수
    }

    /**
     * Id에 해당하는 CPNTHM 조회
     * 
     * @param id
     * @return
     */
    public CPNTHM getCPNTHM(Integer id) {
        return cpnthmRepository.getById(id);  // JpaRepository에서 제공하는 getById() 함수
    }

    /**
     * Id에 해당하는 CPNTHM 삭제
     * 
     * @param id
     */
    public void deleteCPNTHM(Integer id) {
    	cpnthmRepository.deleteById(id);  // JpaRepository에서 제공하는 deleteById() 함수
    }
}
