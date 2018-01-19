package com.lifetech.dhap.pathcloud.setting.api.vo;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-07-13:33
 */
public class ParamSettingVO {

    private String param;

    private Integer code;

    private String name;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
