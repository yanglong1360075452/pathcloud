package com.lifetech.dhap.pathcloud.setting.application;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.setting.application.dto.*;

import java.util.List;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-07-13:18
 */
public interface ParamSettingApplication {

    /**
     * 根据key获取设置内容
     * JSON转成数组
     * @return
     */
    List getContentToListByKey(String key) throws BuzException;

    /**
     * 根据key获取设置内容
     * @param key
     * @return
     * @throws BuzException
     */
    String getContentByKey(String key) throws BuzException;

    /**
     * 根据key和code获取描述
     * @param key
     * @param code
     * @return
     * @throws BuzException
     */
    String getNameByKeyAndCode(String key,int code) throws BuzException;

    /**
     * 根据key和描述获取code
     * @param key
     * @param name
     * @return
     * @throws BuzException
     */
    Integer getCodeByKeyAndName(String key,String name) throws BuzException;

    /**
     * 根据key,添加content内容
     * @param key
     * @param paramSettingDto
     * @return
     * @throws BuzException
     */
    ParamSettingDto addParam(String key,ParamSettingDto paramSettingDto) throws BuzException;

    /**
     * 根据key,减少content内容
     * @param key
     * @param code
     * @throws BuzException
     */
    void deleteParam(String key,int code) throws BuzException;

    /**
     * 根据key,修改content内容
     * @param key
     * @param content
     * @throws BuzException
     */
    void updateContentByKey(String key,String content) throws BuzException;

    /**
     * 根据 当前状态以及 蜡块或玻片ID 查询下一状态
     * @param status
     * @param id
     * @return
     * @throws BuzException
     */
    Integer getNextStatusByStatusAndId(int status,long id) throws BuzException;


    /**
     * 根据操作查询上一操作
     * @param operation
     * @return
     */
    Integer getPreOperationByOperation(int operation) throws BuzException;

    /**
     * 添加检查类别
     * @param dto
     */
    void addInspectCategory(InspectCategoryDto dto);

    /**
     * 删除检查类别
     * @param code
     */
    void  deleteInspectCategory(int code);

    /**
     * 根据code获取检查类别
     * @param code
     * @return
     */
    InspectCategoryDto getInspectCategoryByCode(int code);

    /**
     * 添加质控评分设置
     * @param
     */
    void addQualityScore(QualityScoreDto qualityScoreDto);

    /**
     * 更改质控评分设置
     * @param
     * @param qualityScoreDto
     */
    void updateQualityScore(String key, QualityScoreDto qualityScoreDto);

    /**
     * 删除质控评分
     * @param
     * @param
     */
    void deleteQualityScoreByCode(String key, int code);

    /**
     * 根据工作台查询合格标准分数
     * @param workstation
     * @return
     */
    Integer getQualifiedScoreByWorkstation(Integer workstation);

    /**
     * 查询特染类别描述
     * @param specialDye
     * @return
     */
    String getSpecialDyeDesc(int specialDye);

    void addDepartment(DepartmentSettingDto departmentSettingDto);

    void deleteDepartmentsCategory(int id);

    void renameCategory(DepartmentSettingDto dsd);

    void editDepartmentName(int id, int code, String name);

    /**
     * 修改默认信息查询时间
     * @param code
     */
    void updateQueryTimeRange(int code);

    /**
     * 获取科室
     */
    List<DepartmentSettingDto> getDepartments();

    void updateDyeType(String s, ParamSettingDto paramSettingDto);

    PathNoRuleDto getPathNoRule();

    void addInspectHospital(InspectHospitalDto inspectHospitalDto);

    void deleteInspectHospitals(Integer code);

    String getHospitalDescByCode(Integer code);

    /**
     * 查询科室描述
     * @param code
     * @return
     */
    String getDepartmentDescByCode(int code);

}
