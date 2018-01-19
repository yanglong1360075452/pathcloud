package com.lifetech.dhap.pathcloud.statistic.application.dto;

import com.lifetech.dhap.pathcloud.statistic.domain.model.PersonInspectCategory;

import java.util.List;

/**
 * Created by HP on 2017/2/13.
 */
public class PersonInspectCategoryDto {

    private String userName;

    private String firstName;

    private List<PersonInspectCategory> items;

    private Long total2;

    private String station;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
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

    public List<PersonInspectCategory> getItems() {
        return items;
    }

    public void setItems(List<PersonInspectCategory> items) {
        this.items = items;
    }

    public Long getTotal2() {
        return total2;
    }

    public void setTotal2(Long total2) {
        this.total2 = total2;
    }
}
