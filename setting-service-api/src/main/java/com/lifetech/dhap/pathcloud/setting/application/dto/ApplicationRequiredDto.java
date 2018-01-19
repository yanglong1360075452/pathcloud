package com.lifetech.dhap.pathcloud.setting.application.dto;

/**
 * Created by LiuMei on 2017-03-23.
 */
public class ApplicationRequiredDto {

    private Integer code;

    private String name;

    private Boolean required;

    private String description;

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

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
