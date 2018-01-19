package com.lifetech.dhap.pathcloud.application.application.infrastructure.code;

/**
 * Created by LiuMei on 2017-12-07.
 */
public enum BlockSpecialDye {

    NotApply(0,"未申请染色"),
    HadApply(1,"申请染色")
    ;

    private Integer nCode;

    private String name;


    BlockSpecialDye(Integer _nCode, String _name) {
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

    public static BlockSpecialDye valueOf(Integer code) {
        for (BlockSpecialDye data : BlockSpecialDye.values()){
            if (data.toCode().equals(code)){
                return data;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (BlockSpecialDye data : BlockSpecialDye.values()){
            if (data.toCode().equals(code)){
                return data.name;
            }
        }
        return null;
    }
    
}
