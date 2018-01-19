package com.lifetech.dnap.pathcloud.reagent.application.infrastructure.code;

/**
 * Created by HP on 2017/9/26.
 */
public enum PreProcess {

    NoRepair(1,"无需修复"),
    EDTA(2,"EDTA热修复"),
    Citric(3,"柠檬酸热修复"),
    ;

    private Integer nCode;

    private String name;


    PreProcess(Integer _nCode, String _name) {
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

    public static PreProcess valueOf(Integer code) {
        for (PreProcess receiveStatus : PreProcess.values()){
            if (receiveStatus.toCode().equals(code)){
                return receiveStatus;
            }
        }
        return null;
    }


    public static String getNameByCode(Integer code) {
        for (PreProcess receiveStatus : PreProcess.values()){
            if (receiveStatus.toCode().equals(code)){
                return receiveStatus.name;
            }
        }
        return null;
    }
}
