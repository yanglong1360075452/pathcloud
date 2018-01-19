package com.lifetech.dhap.pathcloud.localdaemon.device.constants;

/**
 * Created by LiuMei on 2017-08-31.
 */
public enum InstrumentStatus {

    Normal(0, "正常"),
    Disabled(1, "禁用"),
    Repairs(2, "报修"),
    ;

    private int code;
    private String name;

    InstrumentStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(int code){
        for (InstrumentStatus status : InstrumentStatus.values()){
            if (status.toCode().equals(code)){
                return status.getName();
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer toCode(){
        return code;
    }

}
