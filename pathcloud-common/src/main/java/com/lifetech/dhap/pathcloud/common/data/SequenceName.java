package com.lifetech.dhap.pathcloud.common.data;

/**
 * Created by LiuMei on 2017-09-25.
 */
public enum SequenceName {

    FrozenNumber("frozenNumber","frozen_number"),
    IhcNumber("ihcNumber","ihc_number"),
    DyeNumber("dyeNumber","dye_number"),
    ConsultNumber("consultNumber","consult_number"),
    ;

    private String param;

    private String name;


    SequenceName(String param, String name) {
        this.param = param;
        this.name = name;
    }


    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static String getNameByParam(String param) {
        for (SequenceName sequenceName : SequenceName.values()){
            if (sequenceName.getParam().equals(param)){
                return sequenceName.name;
            }
        }
        return null;
    }
    
}
