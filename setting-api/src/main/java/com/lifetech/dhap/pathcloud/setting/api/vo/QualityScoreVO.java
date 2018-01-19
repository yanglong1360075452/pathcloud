package com.lifetech.dhap.pathcloud.setting.api.vo;

/**
 * Created by HP on 2017/3/8.
 */
public class QualityScoreVO {

    private Integer code;

    private Integer workstation;
    private String workstationName;

    private String oneScore;

    private String twoScore;

    private String threeScore;

    private String fourScore;

    private String fiveScore;

    private Integer qualified;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getWorkstation() {
        return workstation;
    }

    public void setWorkstation(Integer workstation) {
        this.workstation = workstation;
    }

    public String getWorkstationName() {
        return workstationName;
    }

    public void setWorkstationName(String workstationName) {
        this.workstationName = workstationName;
    }

    public String getOneScore() {
        return oneScore;
    }

    public void setOneScore(String oneScore) {
        this.oneScore = oneScore;
    }

    public String getTwoScore() {
        return twoScore;
    }

    public void setTwoScore(String twoScore) {
        this.twoScore = twoScore;
    }

    public String getThreeScore() {
        return threeScore;
    }

    public void setThreeScore(String threeScore) {
        this.threeScore = threeScore;
    }

    public String getFourScore() {
        return fourScore;
    }

    public void setFourScore(String fourScore) {
        this.fourScore = fourScore;
    }

    public String getFiveScore() {
        return fiveScore;
    }

    public void setFiveScore(String fiveScore) {
        this.fiveScore = fiveScore;
    }

    public Integer getQualified() {
        return qualified;
    }

    public void setQualified(Integer qualified) {
        this.qualified = qualified;
    }
}
