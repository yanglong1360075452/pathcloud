package com.lifetech.dhap.pathcloud.common.data;

/**
 * 病理特殊申请
 */
public enum SpecialApplyType {

    Normal(0,"常规"),
    Frozen(1,"冰冻"),
    IHC(2,"免疫组化"),
    Dye(3,"特染"),
    Consult(4,"会诊"),
    ;

    private Integer nCode;

    private String name;


    SpecialApplyType(Integer _nCode, String _name) {
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

    public static SpecialApplyType valueOf(Integer code) {
        for (SpecialApplyType taskType : SpecialApplyType.values()){
            if (taskType.toCode().equals(code)){
                return taskType;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (SpecialApplyType taskType : SpecialApplyType.values()){
            if (taskType.toCode().equals(code)){
                return taskType.name;
            }
        }
        return null;
    }

}
