package com.lifetech.dhap.pathcloud.statistic.application.dto;

import com.lifetech.dhap.pathcloud.statistic.domain.model.QualityPersonal;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-02-15-17:14
 */
public class QualityPersonalDto extends QualityPersonal {

    private String userName;//用户名

    private String firstName;//姓名

    private String operationName;//工位

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }
}
