package com.lifetech.dhap.pathcloud.application.application;

import com.lifetech.dhap.pathcloud.application.application.condition.PathologyCondition;
import com.lifetech.dhap.pathcloud.application.application.condition.PathologyQueryCondition;
import com.lifetech.dhap.pathcloud.application.application.condition.WechatCondition;
import com.lifetech.dhap.pathcloud.application.application.dto.*;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;

import java.util.Date;
import java.util.List;

/**
 * Created by LuoMo on 2016-11-23.
 */
public interface PathologyApplication {

    /**
     * 登记
     * @param pathologyDto
     * @return
     */
    PathologyDto register(PathologyDto pathologyDto,String letter,Integer code);

    /**
     * 撤销登记
     * @param pathologyDto
     */
    void cancelPathology(PathologyDto pathologyDto);

    /**
     * 根据条件查询病理记录--取材台查询
     * @param con
     * @return
     */
    List<PathologyDto> getGrossingPathologiesByCondition(PathologyCondition con);
    Long countGrossingPathologiesByCondition(PathologyCondition con);

    /**
     * 取材台
     * 根据病理ID查询病理信息
     * @param id
     * @return
     */
    PathologyDto  getGrossingPathologiesById(long id);

    /**
     *根据病理号查询病理信息
     * @param serialNumber
     * @return
     */
    PathologyDto getPathologyBySerialNumber(String serialNumber);

    PathologyDto getSimplePathById(long id);

    PathologyDto getSimplePathByNo(String pathNo);

    PathologyDto getPathologyByApplicationId(long applicationId) throws BuzException;

    PathologyExpandDto getPathologyExpandById(Long id);

    void updatePathology(PathologyDto pathologyDto) throws BuzException;

    /**
     * 根据条件查询病理记录--诊断查询
     * @param con
     * @return
     */
    List<PathologyDiagnoseDto> getDiagnosePathologiesByCondition(PathologyCondition con);
    Long countDiagnosePathologiesByCondition(PathologyCondition con);

    /**
     * 待诊断列表
     * @param condition
     * @return
     */
    List<PathologyDto> getPrepareDiagnose(PathologyCondition condition);
    Long countPrepareDiagnose(PathologyCondition condition);

    /**
     * 根据病理ID获取报告信息
     * @param pathId
     * @return
     */
    ReportInfoDto getReportInfo(long pathId,Long specialApplyId);

    /**
     * 查询工作台-查询病理信息
     * @param condition
     * @return
     */
    List<PathologyQueryDto> getPathologiesByCondition(PathologyQueryCondition condition);
    Long countPathologiesByCondition(PathologyQueryCondition condition);

    /**
     * 根据检查类别查询病理数
     * @param code
     * @return
     */
    long countPathologyByInspectCategory(int code);

    List<PathologyDto> getReportPic(List<Long> pathIds);

    /**
     * 查询用户最后登记的病例ID
     * @param userId
     * @return
     */
    PathologyDto getLastRegisterPathology(long userId,Date createTime);

    List<WechatInfoDto> getMyApplications(WechatCondition con);

    Long getMyApplicationsTotal(WechatCondition con);

    /**
     * 根据序列名称查询下一个值
     * @param seqName
     * @return
     */
    Integer getNextValue(String seqName);

    /**
     * 查询病理是否有脱钙的蜡块
     * @param pathId
     * @return
     */
    Boolean decalcify(long pathId);
}
