package com.lifetech.dnap.pathcloud.reagent.application.infrastructure.code;

/**
 * Created by HP on 2017/9/26.
 */
public enum ReagentLiquidType {

    ConcentratedLiquid(1,"浓缩液"),
    WorkingLiquid(2,"工作液"),
    ;

    private Integer nCode;

    private String name;


    ReagentLiquidType(Integer _nCode, String _name) {
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

    public static ReagentLiquidType valueOf(Integer code) {
        for (ReagentLiquidType receiveStatus : ReagentLiquidType.values()){
            if (receiveStatus.toCode().equals(code)){
                return receiveStatus;
            }
        }
        return null;
    }


    public static String getNameByCode(Integer code) {
        for (ReagentLiquidType receiveStatus : ReagentLiquidType.values()){
            if (receiveStatus.toCode().equals(code)){
                return receiveStatus.name;
            }
        }
        return null;
    }
}
