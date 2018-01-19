package com.lifetech.dhap.pathcloud.user.application.dto;

import java.io.Serializable;

/**
 * Created by LiuMei on 2016-12-14.
 */
public class UserSimpleDto implements Serializable{

    private Long id;

    private String userName;

    private String firstName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
