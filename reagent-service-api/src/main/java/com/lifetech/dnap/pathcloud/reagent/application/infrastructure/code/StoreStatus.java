package com.lifetech.dnap.pathcloud.reagent.application.infrastructure.code;

/**
 * Created by LiuMei on 2017-12-05.
 */
public enum  StoreStatus {

    Normal(1,"正常"),
    GoonExpired(2,"即将过期"),
    Expired(3,"过期"),
    Replenishment(4,"补货"),
    ;

    private Integer nCode;

    private String name;


    StoreStatus(Integer _nCode, String _name) {
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

    public static StoreStatus valueOf(Integer code) {
        for (StoreStatus storeStatus : StoreStatus.values()){
            if (storeStatus.toCode().equals(code)){
                return storeStatus;
            }
        }
        return null;
    }


    public static String getNameByCode(Integer code) {
        for (StoreStatus storeStatus : StoreStatus.values()){
            if (storeStatus.toCode().equals(code)){
                return storeStatus.name;
            }
        }
        return null;
    }
    
}
