package com.lifetech.dhap.pathcloud.tracking.api.impl;

import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.tracking.api.DehydrateBasketApi;
import com.lifetech.dhap.pathcloud.tracking.application.DehydrateBasketApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.BasketCon;
import com.lifetech.dhap.pathcloud.common.data.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.DefaultValue;
import java.util.List;

/**
 * Created by LiuMei on 2016-12-09.
 */
@Component("dehydrateBasketApi")
public class DehydrateBasketApiImpl implements DehydrateBasketApi {

    @Autowired
    private DehydrateBasketApplication dehydrateBasketApplication;

    @Override
    public ResponseVO getDehydrateBaskets(Integer status, @DefaultValue("1") Integer page, @DefaultValue("10") Integer length) throws BuzException {
        if(status == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        BasketCon con = new BasketCon();
        con.setSize(length);
        con.setStart((page - 1) * length);
        con.setStatus(status);
        Long total = dehydrateBasketApplication.countBasketsByCondition(con);
        return new PageDataVO(page,length,total,dehydrateBasketApplication.getDehydrateBasketsByCondition(con));
    }

    @Override
    public ResponseVO grossingGetBaskets() throws BuzException {
        Integer status = PathologyStatus.PrepareGrossingConfirm.toCode();
        BasketCon con = new BasketCon();
        con.setSize(Integer.MAX_VALUE);
        con.setStatus(status);
        Long userId = UserContext.getLoginUserID();
        List<Integer> permissionsCode = UserContext.getLoginUserPermissions();
        if(permissionsCode != null && permissionsCode.size() > 0){
            for(Integer permission : permissionsCode){
                if(permission.equals(Permission.Admin.toCode())){
                    userId = null;
                }
            }
        }
        con.setUserId(userId);
        return new ResponseVO(dehydrateBasketApplication.getDehydrateBasketsByCondition(con));
    }
}
