package com.example.StockServer.Jpa;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.StockServer.dao.Company;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyJpaService {

    private final CompanyRepository companyRepository;

    /**
     * Company 생성
     *
     * @param Company
     * @return
     */
    public Company createCompany(Company company) {
        Company savedCompany = companyRepository.save(company);  // JpaRepository에서 제공하는 save() 함수
        return savedCompany;
    }

    /**
     * Company 수정
     * JPA Repository의 save Method를 사용하여 객체를 업데이트 할 수 있습니다.
     * Entity Company에 @Id로 설정한 키 값이 존재할 경우 해당하는 데이터를 업데이트 해줍니다.
     * 만약 수정하려는 Entity Company 객체에 @Id 값이 존재하지 않으면 Insert 되기 때문에
     * 아래와 같이 업데이트 하고자 하는 Company가 존재하는지 체크하는 로직을 추가하였습니다.
     *
     * @param Company
     * @return
     */
    public Company updateCompany(Company company) {
        Company updatedCompany = null;
        try {
            Company existCompany = getCompany(company.getId());
            if (!ObjectUtils.isEmpty(existCompany)) {
                updatedCompany = companyRepository.save(company);  // JpaRepository에서 제공하는 save() 함수
            }
        } catch (Exception e) {
            log.info("[Fail] e: " + e.toString());
        } finally {
            return updatedCompany;
        }
    }

    /**
     * Company List 조회
     * 
     * @return
     */
    public List<Company> getCompanys() {
        return companyRepository.findAll();  // JpaRepository에서 제공하는 findAll() 함수
    }
    

    /**
     * Id에 해당하는 Company 조회
     * 
     * @param id
     * @return
     */
    public Company getCompany(Integer id) {
        return companyRepository.getById(id);  // JpaRepository에서 제공하는 getById() 함수
    }
    
    public Company findByCompanyCode(String companyCode) {
        return companyRepository.findByCompanyCode(companyCode);  // JpaRepository에서 제공하는 getById() 함수
    }
    
    
    public List<Company> findByCompanyNameContaining(String companyName) {
        return companyRepository.findByCompanyNameContaining(companyName);  // JpaRepository에서 제공하는 getById() 함수
    }


    /**
     * Id에 해당하는 Company 삭제
     * 
     * @param id
     */
    public void deleteCompany(Integer id) {
    	companyRepository.deleteById(id);  // JpaRepository에서 제공하는 deleteById() 함수
    }
    
    /**
     * Id에 해당하는 Company 삭제
     * 
     * @param id
     */
    public void deleteAllCompany() {
    	companyRepository.deleteAll();  
    }
    
    
}
