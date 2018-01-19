package com.lifetech.dhap.pathcloud.common.request;

/**
 * Created by gdw on 4/18/16.
 */
public class BasePageReq {

    private Integer page;

    private Integer size;

    private String order;

    private String sort;

    public BasePageReq(){
        this.page = 1;
        this.size = 10;
        this.sort = " desc";
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
