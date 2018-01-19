package com.lifetech.dhap.pathcloud.tracking.application;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.tracking.application.condition.BasketCon;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BasketDto;

import java.util.List;

/**
 * Created by LiuMei on 2016-12-09.
 */
public interface DehydrateBasketApplication {

    /**
     * 获取脱水篮信息列表
     * @return
     * @throws BuzException
     */
    List<BasketDto> getDehydrateBasketsByCondition(BasketCon con) throws BuzException;

    Long countBasketsByCondition(BasketCon con) throws BuzException;
}
