package com.lifetech.dhap.pathcloud.application.domain;

import com.lifetech.dhap.pathcloud.application.domain.model.ApplicationIhc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 特染申请
 */
public interface ApplicationIhcRepository {

    int deleteByPrimaryKey(Long id);

    int insert(ApplicationIhc record);

    ApplicationIhc selectByPrimaryKey(Long id);

    List<ApplicationIhc> selectAll();

    int updateByPrimaryKey(ApplicationIhc record);

    long last();

    /**
     * 根据病理号查询申请特染信息
     * @param pathNo
     * @return
     */
    ApplicationIhc selectSpecialDyeByPathNo(String pathNo);

    /**
     * 根据病理号和申请类别查询申请信息
     * @param pathNo
     * @param specialDye
     * @return
     */
    ApplicationIhc selectByPathNoAndSpecialDye(@Param("pathNo") String pathNo, @Param("specialDye") int specialDye);
}