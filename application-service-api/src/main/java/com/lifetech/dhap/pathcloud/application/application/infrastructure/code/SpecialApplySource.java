package com.lifetech.dhap.pathcloud.application.application.infrastructure.code;

/**
 * Created by LiuMei on 2017-12-07.
 */
public enum SpecialApplySource {

    SpecialApply(0,"特检申请工作站"),
    Diagnose(1,"诊断工作站")
    ;

    private Integer nCode;

    private String name;


    SpecialApplySource(Integer _nCode, String _name) {
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

    public static SpecialApplySource valueOf(Integer code) {
        for (SpecialApplySource data : SpecialApplySource.values()){
            if (data.toCode().equals(code)){
                return data;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (SpecialApplySource data : SpecialApplySource.values()){
            if (data.toCode().equals(code)){
                return data.name;
            }
        }
        return null;
    }
    
}
