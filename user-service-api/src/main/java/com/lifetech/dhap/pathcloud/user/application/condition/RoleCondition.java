package com.lifetech.dhap.pathcloud.user.application.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

/**
 * Created by LuoMo on 2016-11-09.
 */
public class RoleCondition extends PageCondition{
    private String filter;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
