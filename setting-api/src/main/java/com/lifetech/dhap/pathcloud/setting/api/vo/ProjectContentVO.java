package com.lifetech.dhap.pathcloud.setting.api.vo;

import java.io.Serializable;

/**
 * Created by HP on 2017/1/11.
 */
public class ProjectContentVO implements Serializable{


    private  String name;
    private Boolean check;

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
