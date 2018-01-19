package com.lifetech.dhap.pathcloud.tracking.api.vo;

/**
 * Created by LiuMei on 2017-04-17.
 */
public class ReportPicVO {

    private long id;

    private Boolean special;

    private String reportPic;

    public Boolean getSpecial() {
        return special;
    }

    public void setSpecial(Boolean special) {
        this.special = special;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReportPic() {
        return reportPic;
    }

    public void setReportPic(String reportPic) {
        this.reportPic = reportPic;
    }
}
