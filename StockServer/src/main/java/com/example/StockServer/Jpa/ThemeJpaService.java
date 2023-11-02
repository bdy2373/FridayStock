package com.example.StockServer.Jpa;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.StockServer.dao.Theme;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThemeJpaService {

	@Autowired
    ThemeRepository themeRepository;

    /**
     * Theme 생성
     *
     * @param Theme
     * @return
     */
    public Theme createTheme(Theme theme) {
        Theme savedTheme = themeRepository.save(theme);  // JpaRepository에서 제공하는 save() 함수
        return savedTheme;
    }

    /**
     * Theme 수정
     * JPA Repository의 save Method를 사용하여 객체를 업데이트 할 수 있습니다.
     * Entity Theme에 @Id로 설정한 키 값이 존재할 경우 해당하는 데이터를 업데이트 해줍니다.
     * 만약 수정하려는 Entity Theme 객체에 @Id 값이 존재하지 않으면 Insert 되기 때문에
     * 아래와 같이 업데이트 하고자 하는 Theme가 존재하는지 체크하는 로직을 추가하였습니다.
     *
     * @param Theme
     * @return
     */
    public Theme updateTheme(Theme theme) {
        Theme updatedTheme = null;
        try {
            Theme existTheme = getTheme(theme.getId());
            if (!ObjectUtils.isEmpty(existTheme)) {
                updatedTheme = themeRepository.save(theme);  // JpaRepository에서 제공하는 save() 함수
            }
        } catch (Exception e) {
            log.info("[Fail] e: " + e.toString());
        } finally {
            return updatedTheme;
        }
    }

    /**
     * Theme List 조회
     * 
     * @return
     */
    public List<Theme> getThemes() {
        return themeRepository.findAll();  // JpaRepository에서 제공하는 findAll() 함수
    }

    /**
     * Id에 해당하는 Theme 조회
     * 
     * @param id
     * @return
     */
    public Theme getTheme(Integer id) {
        return themeRepository.getById(id);  // JpaRepository에서 제공하는 getById() 함수
    }
    
    public List<Theme> findTopThemeByThemeEtc(String themeEtc) {
        return themeRepository.findTopByThemeEtc(themeEtc); // JpaRepository에서 제공하는 getById() 함수
    }
    
    public boolean existsByThemeEtc(String themeEtc) {
        return themeRepository.existsByThemeEtc(themeEtc); // JpaRepository에서 제공하는 getById() 함수
    }

    /**
     * Id에 해당하는 Theme 삭제
     * 
     * @param id
     */
    public void deleteTheme(Integer id) {
    	themeRepository.deleteById(id);  // JpaRepository에서 제공하는 deleteById() 함수
    }
}
