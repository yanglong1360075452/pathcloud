package com.lifetech.dhap.pathcloud.reagent.domain;

import com.lifetech.dhap.pathcloud.reagent.domain.model.Reagent;
import com.lifetech.dnap.pathcloud.reagent.application.condition.ReagentCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReagentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Reagent record);

    Reagent selectByPrimaryKey(Long id);

    List<Reagent> selectAll();

    int updateByPrimaryKey(Reagent record);

    long last();

    List<Reagent> selectByCondition(ReagentCondition condition);

    Long countByCondition(ReagentCondition condition);

    Reagent selectByTypeAndName(@Param("type") Integer type, @Param("name") String name);
}