package com.lifetech.dhap.pathcloud.tracking.api.vo;

import java.util.List;

public class SlideBackConfirmVO {

    private Long updateBy; //归还人

    private List<Long> borrows;

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public List<Long> getBorrows() {
        return borrows;
    }

    public void setBorrows(List<Long> borrows) {
        this.borrows = borrows;
    }
}
