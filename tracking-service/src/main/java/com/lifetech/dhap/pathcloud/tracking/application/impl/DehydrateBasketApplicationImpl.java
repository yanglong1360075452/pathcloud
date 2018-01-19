package com.lifetech.dhap.pathcloud.tracking.application.impl;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.tracking.application.DehydrateBasketApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.BasketCon;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BasketDto;
import com.lifetech.dhap.pathcloud.tracking.domain.BlockRepository;
import com.lifetech.dhap.pathcloud.tracking.domain.model.Basket;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMei on 2016-12-09.
 */
@Service
public class DehydrateBasketApplicationImpl implements DehydrateBasketApplication {

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private UserApplication userApplication;

    @Override
    public List<BasketDto> getDehydrateBasketsByCondition(BasketCon con) throws BuzException {
        Integer status = con.getStatus();
        Long userId = con.getUserId();
        if (status == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<Basket> baskets = blockRepository.selectBasketsInfoByCondition(con);
        List<BasketDto> basketDtos = null;
        if (baskets != null && baskets.size() > 0) {
            basketDtos = new ArrayList<>();
            BasketDto basketDto;
            for (Basket basket : baskets) {
                basketDto = new BasketDto();
                BeanUtils.copyProperties(basket, basketDto);
                Long recorders = blockRepository.selectRecorderByBasket(basket.getBasketNumber(), status, userId);
                if (recorders != null) {
                    basketDto.setRecorder(userApplication.getUserSimpleInfoById(recorders).getFirstName());
                }
                basketDtos.add(basketDto);
            }
        }
        return basketDtos;
    }

    @Override
    public Long countBasketsByCondition(BasketCon con) throws BuzException {
        return blockRepository.countBasketsInfoByCondition(con);
    }
}
