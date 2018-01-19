package com.lifetech.dhap.pathcloud.setting.application.dto;

import java.util.List;

/**
 * Created by LiuMei on 2016-12-27.
 */
public class TrackingListDto extends ParamSettingDto {

    private Boolean checked;

    private List<Integer> status;

    private List<Integer> operation;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public List<Integer> getStatus() {
        return status;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
    }

    public List<Integer> getOperation() {
        return operation;
    }

    public void setOperation(List<Integer> operation) {
        this.operation = operation;
    }
}
