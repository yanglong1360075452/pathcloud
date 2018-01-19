package com.lifetech.dhap.pathcloud.localdaemon.device.constants;

/**
 * Created by LiuMei on 2017-08-24.
 */
public enum DehydratorStatus {

    Normal(0, "正常"),
    Error(1, "错误"),
    Pause(2, "暂停"),
    ;
    DehydratorStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(int code) {
        DehydratorStatus[] vals = DehydratorStatus.values();
        for (DehydratorStatus val: vals) {
            if (val.getCode() == code) {
                return val.getName();
            }
        }

        return null;
    }

    public int getCode() {
        return this.code;
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

    private int code;
    private String name;
    
}
