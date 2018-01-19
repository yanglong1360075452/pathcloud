package com.lifetech.dhap.pathcloud.application.domain.model;

/**
 * Created by LiuMei on 2017-04-10.
 */
public class BlockIhcExtend extends BlockIhc {

    private String applyUser;

    private String applyTel;

    public String getApplyTel() {
        return applyTel;
    }

    public void setApplyTel(String applyTel) {
        this.applyTel = applyTel;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }
}
