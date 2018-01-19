package com.lifetech.dhap.pathcloud.setting.api.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HP on 2017/1/9.
 */
public class DiagnoseTemplateContentVO implements Serializable{

    private String projectName;

    private List<ProjectContentVO> projectContentVO;

    private  String other;

    private String projectContentValue;

    private Boolean projectNameCheck;

    private Boolean projectContentCheck;

    public Boolean getProjectContentCheck() {
        return projectContentCheck;
    }

    public void setProjectContentCheck(Boolean projectContentCheck) {
        this.projectContentCheck = projectContentCheck;
    }

    public Boolean getProjectNameCheck() {
        return projectNameCheck;
    }

    public void setProjectNameCheck(Boolean projectNameCheck) {
        this.projectNameCheck = projectNameCheck;
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<ProjectContentVO> getProjectContentVO() {
        return projectContentVO;
    }

    public void setProjectContentVO(List<ProjectContentVO> projectContentVO) {
        this.projectContentVO = projectContentVO;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getProjectContentValue() {
        return projectContentValue;
    }

    public void setProjectContentValue(String projectContentValue) {
        this.projectContentValue = projectContentValue;
    }
}
