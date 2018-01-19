package com.lifetech.dhap.pathcloud.wechat.api.vo;

import java.util.List;

/**
 * Created by LiuMei on 2017-04-01.
 */
public class IhcApplicationVO {

    private Long id;

    private String applyTel;

    private String applyUser;

    private Integer departments;
    private String departmentsDesc;

    private Long source; //用此字段标识申请入口-诊断工作台申请值为1

    private List<IhcBlockVO> ihcBlocks;

    public Long getSource() {
        return source;
    }

    public void setSource(Long source) {
        this.source = source;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplyTel() {
        return applyTel;
    }

    public void setApplyTel(String applyTel) {
        this.applyTel = applyTel;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
    }

    public String getDepartmentsDesc() {
        return departmentsDesc;
    }

    public void setDepartmentsDesc(String departmentsDesc) {
        this.departmentsDesc = departmentsDesc;
    }

    public List<IhcBlockVO> getIhcBlocks() {
        return ihcBlocks;
    }

    public void setIhcBlocks(List<IhcBlockVO> ihcBlocks) {
        this.ihcBlocks = ihcBlocks;
    }
}
