package com.lifetech.dnap.pathcloud.reagent.application;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dnap.pathcloud.reagent.application.condition.ReagentCondition;
import com.lifetech.dnap.pathcloud.reagent.application.condition.StoreCondition;
import com.lifetech.dnap.pathcloud.reagent.application.dto.ReagentDto;
import com.lifetech.dnap.pathcloud.reagent.application.dto.ReagentRecordDto;

import java.util.List;

/**
 * Created by HP on 2017/9/26.
 */
public interface ReagentApplication {

    /**
     *添加试剂耗材
     * @param reagentDto
     * @throws BuzException
     */
    void createReagent(ReagentDto reagentDto) throws BuzException;

    /**
     * 更新试剂耗材
     * @param reagentDto
     * @throws BuzException
     */
    void updateReagent(ReagentDto reagentDto) throws BuzException;

    /**
     * 获取试剂耗材列表
     * @param con
     * @return
     */
    List<ReagentDto> getReagentList(ReagentCondition con);
    Long countReagentByCondition(ReagentCondition con);

    /**
     * 根据类型和名称查询试剂耗材
     * @param name
     * @return
     */
    ReagentDto getReagentByTypeAndName(Integer type,String name);

    /**
     * 根据ID查询试剂耗材
     * @param id
     * @return
     */
    ReagentDto getReagentById(long id);

    /**
     * 单个添加使用记录
     * @param reagentRecordDto
     */
    void addReagentRecord(ReagentRecordDto reagentRecordDto);

    /**
     * 查询使用记录
     * @param condition
     * @return
     */
    List<ReagentRecordDto> getRecordByCondition(StoreCondition condition);
    Long countRecordByCondition(StoreCondition condition);
}
