package com.lifetech.dnap.pathcloud.reagent.application.infrastructure.code;

/**
 * Created by HP on 2017/9/26.
 */
public enum ReceiveStatus {

    Qualified(1,"合格"),
    Damaged(2,"破损")
    ;

    private Integer nCode;

    private String name;


    ReceiveStatus(Integer _nCode, String _name) {
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

    public static ReceiveStatus valueOf(Integer code) {
        for (ReceiveStatus receiveStatus : ReceiveStatus.values()){
            if (receiveStatus.toCode().equals(code)){
                return receiveStatus;
            }
        }
        return null;
    }


    public static String getNameByCode(Integer code) {
        for (ReceiveStatus receiveStatus : ReceiveStatus.values()){
            if (receiveStatus.toCode().equals(code)){
                return receiveStatus.name;
            }
        }
        return null;
    }
}
