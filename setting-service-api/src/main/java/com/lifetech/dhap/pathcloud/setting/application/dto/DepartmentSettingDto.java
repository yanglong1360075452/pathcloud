package com.lifetech.dhap.pathcloud.setting.application.dto;

/**
 * Created by HP on 2017/5/3.
 */
public class DepartmentSettingDto {

    private Integer id;

    private Integer code;

    private String departmentCategory;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDepartmentCategory() {
        return departmentCategory;
    }

    public void setDepartmentCategory(String departmentCategory) {
        this.departmentCategory = departmentCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
