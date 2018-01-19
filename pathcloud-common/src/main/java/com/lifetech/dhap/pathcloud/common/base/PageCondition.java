package com.lifetech.dhap.pathcloud.common.base;

/**
 * Created by gdw on 4/18/16.
 *
 * 分页条件基类
 */
public class PageCondition {

    private Integer start;

    private Integer size;

    private String order;

    public PageCondition(){
        this.start = 0;
        this.size = 10;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
