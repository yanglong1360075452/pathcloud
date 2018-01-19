package com.lifetech.dhap.pathcloud.common.data;

/**
 * Created by LiuMei on 2017-12-07.
 *
 * 固定的染色类别
 */
public enum SpecialDyeFix {

    White(-1,"白片"),
    HE(0,"HE"),
    IHC(1,"免疫组化"),
    ;

    private Integer nCode;

    private String name;


    SpecialDyeFix(Integer _nCode, String _name) {
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

    public static SpecialDyeFix valueOf(Integer code) {
        for (SpecialDyeFix specialDyeFix : SpecialDyeFix.values()){
            if (specialDyeFix.toCode().equals(code)){
                return specialDyeFix;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (SpecialDyeFix specialDyeFix : SpecialDyeFix.values()){
            if (specialDyeFix.toCode().equals(code)){
                return specialDyeFix.name;
            }
        }
        return null;
    }
    
}
