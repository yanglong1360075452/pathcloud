package com.lifetech.dnap.pathcloud.reagent.application.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

/**
 * Created by HP on 2017/9/26.
 */
public class ReagentCondition extends PageCondition{

    private String filter;

    private Integer category;

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
