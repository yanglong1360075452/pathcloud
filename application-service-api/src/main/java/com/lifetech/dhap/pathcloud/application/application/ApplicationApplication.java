package com.lifetech.dhap.pathcloud.application.application;

import com.lifetech.dhap.pathcloud.application.application.condition.ApplicationCondition;
import com.lifetech.dhap.pathcloud.application.application.dto.*;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;

import java.util.Date;
import java.util.List;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-11-21-17:18
 */
public interface ApplicationApplication {

    /**
     * 创建病理申请单
     * @param applicationDto
     * @return
     * @throws BuzException
     */
    ApplicationDto createApplication(ApplicationDto applicationDto) throws BuzException;

    /**
     * 根据id获取病理申请
     * @param id
     * @return
     * @throws BuzException
     */
    ApplicationDto getApplicationById(Long id) throws BuzException;

    /**
     * 根据病理申请号获取病理申请
     * @param serialNumber
     * @return
     * @throws BuzException
     */
    ApplicationDto getApplicationBySerialNumber(String serialNumber) throws BuzException;

    /**
     * 根据病理号获取申请单信息
     * @param pathologyNo
     * @return
     * @throws BuzException
     */
    List<ApplicationDto> getApplicationByPathologyNo(String pathologyNo) throws BuzException;

    ApplicationDto getLastApplicationByPathNo(String pathNo) throws BuzException;

    List<ApplicationDto> getApplicationByCondition(ApplicationCondition con) throws BuzException;

    /**
     * 根据条件获取申请列表, 获取申请概要信息
     * @param con
     * @return
     * @throws BuzException
     */
    List<ApplicationBriefDto> getApplicationBriefByCondition(ApplicationCondition con) throws BuzException;

    Long getApplicationTotalByCondition(ApplicationCondition con) throws BuzException;

    /**
     * 登记工作站样本信息查询
     *
     * @param con
     * @return
     */
    List<ApplicationSampleDto> getApplicationAndSamplesByCondition(ApplicationCondition con);

    Long countApplicationAndSamplesByCondition(ApplicationCondition con);
    /**
     * 更新申请表status
     * @param applicationDto
     * @return
     * @throws BuzException
     */
    void updateApplicationStatus(ApplicationDto applicationDto)throws BuzException;

    /**
     * 获取送检医生列表
     * @return
     */
    List<UserSimpleDto> getInspectDoctor();

    /**
     * 添加冰冻预约
     * @param dto
     */
    void addBooking(BookingAddDto dto);

    /**
     * 获取冰冻预约信息
     * @param startTime
     * @param endTime
     * @return
     */
    List<FreezeBookingDto> freezeBookingQuery(Date startTime, Date endTime);

    /**
     * 撤销冰冻预约
     * @param applicationDto
     */
    void cancelFreezeApplication(ApplicationDto applicationDto);

    Long countDepartments(Integer code);

    List<SampleDto> samplesToDtos(Object o);

    ApplicationDto getApplicationBySampleSerialNumber(String serialNumber);

    /**
     * 根据病理ID查询申请ID
     * @param pathId
     * @return
     */
    List<Long> getIdsByPathId(long pathId);
}
