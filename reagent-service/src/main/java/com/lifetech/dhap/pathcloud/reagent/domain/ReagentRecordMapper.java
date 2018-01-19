package com.lifetech.dhap.pathcloud.reagent.domain;

import com.lifetech.dhap.pathcloud.reagent.domain.model.ReagentRecord;
import com.lifetech.dnap.pathcloud.reagent.application.condition.StoreCondition;

import java.util.List;

public interface ReagentRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReagentRecord record);

    ReagentRecord selectByPrimaryKey(Long id);

    List<ReagentRecord> selectAll();

    List<ReagentRecord> selectByCondition(StoreCondition condition);
    Long countByCondition(StoreCondition condition);

    int updateByPrimaryKey(ReagentRecord record);
}