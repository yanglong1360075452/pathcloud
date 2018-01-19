package com.lifetech.dhap.pathcloud.setting.api.vo;

import java.util.List;

/**
 * Created by HP on 2017/5/3.
 */
public class DepartmentQueryVO {

    private Integer id;
    private String departmentCategory;

    private List<Department> departments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentCategory() {
        return departmentCategory;
    }

    public void setDepartmentCategory(String departmentCategory) {
        this.departmentCategory = departmentCategory;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
