package com.lifetech.dhap.pathcloud.reagent.api.vo;

/**
 * Created by HP on 2017/9/26.
 */
public class ReagentVO {

    private Long id;

    private String name;

    private Integer category;

    private Integer type;

    private String cloneNumber;

    private Integer preProcess;

    private String positivePosition;

    private String diagnose;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getCloneNumber() {
        return cloneNumber;
    }

    public void setCloneNumber(String cloneNumber) {
        this.cloneNumber = cloneNumber;
    }

    public Integer getPreProcess() {
        return preProcess;
    }

    public void setPreProcess(Integer preProcess) {
        this.preProcess = preProcess;
    }

    public String getpositivePosition() {
        return positivePosition;
    }

    public void setpositivePosition(String positivePosition) {
        this.positivePosition = positivePosition;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }
}
