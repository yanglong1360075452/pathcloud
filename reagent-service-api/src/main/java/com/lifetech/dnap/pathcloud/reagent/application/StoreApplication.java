package com.lifetech.dnap.pathcloud.reagent.application;

import com.lifetech.dnap.pathcloud.reagent.application.condition.StoreCondition;
import com.lifetech.dnap.pathcloud.reagent.application.dto.StoreDto;

import java.util.List;

/**
 * @author LiuMei
 * @date 2017-12-01.
 */
public interface StoreApplication {

    /**
     * 入库
     * @param storeDto
     */
    void inStore(StoreDto storeDto);

    /**
     * 修改库存
     * @param storeDto
     */
    void updateStore(StoreDto storeDto);

    /**
     * 根据试剂耗材ID/批次号/订单号查询入库信息
     * @param reagentId
     * @param batchNumber
     * @param orderNumber
     * @return
     */
    StoreDto getByUnique(long reagentId,String batchNumber,String orderNumber);

    /**
     * '根据ID查询
     * @param storeId
     * @return
     */
    StoreDto getById(long storeId);

    /**
     * 根据条件查询
     * @param condition
     * @return
     */
    List<StoreDto> getList(StoreCondition condition);
    Long countByCondition(StoreCondition condition);

    /**
     * 获取当前可使用的库存
     * @param condition
     * @return
     */
    StoreDto getCurrentUse(StoreCondition condition);

    /**
     * 调整库存
     * @param storeDto
     */
    void adjust(StoreDto storeDto);
}
