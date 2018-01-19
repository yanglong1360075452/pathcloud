package com.lifetech.dhap.pathcloud.tracking.infrastructure.code;

/**
 * Created by LiuMei on 2017-01-09.
 */
public enum  AbnormalHandle {

    ReGrossing(1,"重补取"),
    ReSection(2,"重切"),
    ErrorEnd(3,"异常终止"),
    Notification(4,"通知技术人员"),
            ;

    private Integer nCode;

    private String name;


    AbnormalHandle(Integer _nCode, String _name) {
        this.nCode = _nCode;
        this.name = _name;
    }

    public Integer toCode(){
        return this.nCode;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static AbnormalHandle valueOf(Integer code) {
        for (AbnormalHandle abnormalHandle : AbnormalHandle.values()){
            if (abnormalHandle.toCode().equals(code)){
                return abnormalHandle;
            }
        }
        return null;
    }
}
