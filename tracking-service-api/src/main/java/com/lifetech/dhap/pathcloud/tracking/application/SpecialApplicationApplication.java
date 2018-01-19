package com.lifetech.dhap.pathcloud.tracking.application;

import com.lifetech.dhap.pathcloud.tracking.application.condition.SpecialApplicationCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.SpecialApplicationDto;

import java.util.List;

/**
 * Created by LiuMei on 2017-09-25.
 */
public interface SpecialApplicationApplication {

    /**
     * 添加特殊申请
     * @param data
     */
    void add(SpecialApplicationDto data);

    /**
     * 根据编号查询记录
     * @param number
     * @return
     */
    SpecialApplicationDto getByNumber(String number);

    /**
     * 根据病理号和申请类型查询特殊申请记录
     * @param pathNo
     * @param type
     * @return
     */
    SpecialApplicationDto getByPathNoAndType(String pathNo,Integer type);

    /**
     * 根据条件查询记录列表
     * @param condition
     * @return
     */
    List<SpecialApplicationDto> getListByCondition(SpecialApplicationCondition condition);
    Long countByCondition(SpecialApplicationCondition condition);

    /**
     * 根据ID查询记录
     * @param id
     * @return
     */
    SpecialApplicationDto getById(long id);

    /**
     * 更新
     * @param dto
     */
    void update(SpecialApplicationDto dto);

    /**
     * 根据特殊申请对应TrackingID获取特殊申请状态现在应该的状态(根据玻片状态判断)
     * @param trackingId
     * @return
     */
    Integer getSlideMinStatusByTrackingId(long trackingId);

    /**
     * 根据病理号查询冰冻号
     * @param pathNo
     * @return
     */
    List<String> getFrozenNumbersByPathNo(String pathNo);

    /**
     * 根据病理号查询冰冻诊断结果
     * @param pathNo
     * @return
     */
    List<String> getFrozenResultByPathNo(String pathNo);
}
