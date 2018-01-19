package com.lifetech.dhap.pathcloud.application.application.infrastructure.code;

/**
 * Created by LiuMei on 2017-03-28.
 */
public enum ApplyType {

    Clinic(1,"临床"),
    Research(2,"科研"),
    Consult(3,"会诊"),
    ;

    private Integer nCode;

    private String name;


    ApplyType(Integer _nCode, String _name) {
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

    public static ApplyType valueOf(Integer code) {
        for (ApplyType applyType : ApplyType.values()){
            if (applyType.toCode().equals(code)){
                return applyType;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (ApplyType applyType : ApplyType.values()){
            if (applyType.toCode().equals(code)){
                return applyType.name;
            }
        }
        return null;
    }
}
