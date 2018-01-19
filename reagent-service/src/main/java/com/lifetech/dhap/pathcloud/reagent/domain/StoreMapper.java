package com.lifetech.dhap.pathcloud.reagent.domain;

import com.lifetech.dhap.pathcloud.reagent.domain.model.ReagentStore;
import com.lifetech.dhap.pathcloud.reagent.domain.model.Store;
import com.lifetech.dnap.pathcloud.reagent.application.condition.StoreCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StoreMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Store record);

    Store selectByPrimaryKey(Long id);

    List<Store> selectAll();

    int updateByPrimaryKey(Store record);

    Store selectByUnique(@Param("reagentId") long reagentId,@Param("batchNumber") String batchNumber,@Param("orderNumber") String orderNumber);

    List<ReagentStore> selectByCondition(StoreCondition condition);
    Long countByCondition(StoreCondition condition);

    Store getCurrentUse(StoreCondition condition);
}