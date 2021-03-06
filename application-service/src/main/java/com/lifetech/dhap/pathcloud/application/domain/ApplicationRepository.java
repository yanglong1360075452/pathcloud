package com.lifetech.dhap.pathcloud.application.domain;

import com.lifetech.dhap.pathcloud.application.application.condition.ApplicationCondition;
import com.lifetech.dhap.pathcloud.application.domain.model.Application;
import com.lifetech.dhap.pathcloud.application.domain.model.ApplicationSample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplicationRepository {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table application
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table application
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    int insert(Application record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table application
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    Application selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table application
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    List<Application> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table application
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    int updateByPrimaryKey(Application record);

    /**
     * 更改状态
     *
     * @param id
     * @return
     */
    int updateStatusById(@Param("id") Long id, @Param("status") int status, @Param("updateBy") Long updateBy,
                         @Param("rejectReason") String rejectReason, @Param("reasonType") String reasonType,@Param("pathId") Long pathId,
                         @Param("number") String number);

    Long last();

    /**
     * 条件分页查询
     *
     * @param con
     * @return
     */
    List<Application> selectByCondition(ApplicationCondition con);

    /**
     * 条件分页统计
     *
     * @param con
     * @return
     */
    Long countByCondition(ApplicationCondition con);

    /**
     * @param con
     * @return
     */
    List<ApplicationSample> selectSampleAndApplicationByCondition(ApplicationCondition con);

    Long countSampleAndApplicationByCondition(ApplicationCondition con);

    /**
     * 根据申请号查询申请单
     *
     * @param serialNumber
     * @return
     */
    Application selectBySerialNumber(String serialNumber);

    /**
     * 根据病理号查询申请单
     *
     * @param pathologyNo
     * @return
     */
    List<Application> selectByPathologyNo(String pathologyNo);

    /**
     * 根据病理号获取上一个申请单信息
     * @param pathNo
     * @return
     */
    Application selectLastByPathNo(String pathNo);

    List<Long> selectInspectDoctor();

    Long countDepartments(Integer code);

    Application getApplicationBySampleSerialNumber(String serialNumber);

    List<Long> selectIdsByPathId(long pathId);
}