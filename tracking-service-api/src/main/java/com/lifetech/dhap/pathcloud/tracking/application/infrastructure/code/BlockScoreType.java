package com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code;

/**
 * Created by LiuMei on 2017-06-08.
 */
public enum BlockScoreType {

    Diagnose(1,"诊断"),
    Production(2,"制片确认"),
            ;

    private Integer nCode;

    private String name;


    BlockScoreType(Integer _nCode, String _name) {
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

    public static BlockScoreType valueOf(Integer code) {
        for (BlockScoreType data : BlockScoreType.values()){
            if (data.toCode().equals(code)){
                return data;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (BlockScoreType data : BlockScoreType.values()){
            if (data.toCode().equals(code)){
                return data.toString();
            }
        }
        return null;
    }

}
