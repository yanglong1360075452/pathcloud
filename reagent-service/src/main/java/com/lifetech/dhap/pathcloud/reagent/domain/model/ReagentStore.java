package com.lifetech.dhap.pathcloud.reagent.domain.model;

/**
 * @author LiuMei
 * @date 2017-12-01.
 */
public class ReagentStore extends Store {

    private String reagentName;

    private Integer reagentCategory;

    private Integer reagentType;

    private Integer status;

    public String getReagentName() {
        return reagentName;
    }

    public void setReagentName(String reagentName) {
        this.reagentName = reagentName;
    }

    public Integer getReagentCategory() {
        return reagentCategory;
    }

    public void setReagentCategory(Integer reagentCategory) {
        this.reagentCategory = reagentCategory;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReagentType() {
        return reagentType;
    }

    public void setReagentType(Integer reagentType) {
        this.reagentType = reagentType;
    }
}
