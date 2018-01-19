package com.lifetech.dhap.pathcloud.wechat.api.vo;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LiuMei on 2017-02-09.
 * <p>
 * 查询工作台-样本信息-制片信息
 */
public class ApplicationDetailVO implements Comparable<ApplicationDetailVO> {

    private String blockSubId;

    private String slideSubId;

    private Integer operation;
    private String operationDesc;

    private String operator;

    private Date operationTime;

    private String note;

    private String noteType;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getBlockSubId() {
        return blockSubId;
    }

    public void setBlockSubId(String blockSubId) {
        this.blockSubId = blockSubId;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public String getOperationDesc() {
        return operationDesc;
    }

    public void setOperationDesc(String operationDesc) {
        this.operationDesc = operationDesc;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public String getSlideSubId() {
        return slideSubId;
    }

    public void setSlideSubId(String slideSubId) {
        this.slideSubId = slideSubId;
    }

    @Override
    public int compareTo(ApplicationDetailVO o) {
        if (this.blockSubId.equals(o.blockSubId) && this.slideSubId == null) {
            return operationTime.compareTo(o.getOperationTime());
        } else if(this.blockSubId.equals(o.blockSubId) && this.slideSubId != null && this.slideSubId.equals(o.getSlideSubId())){
            return operationTime.compareTo(o.getOperationTime());
        }  else if(this.blockSubId.equals(o.blockSubId) && this.slideSubId != null && !this.slideSubId.equals(o.getSlideSubId())){
            if(o.getSlideSubId() == null){
                return 1;
            }else{
                return this.slideSubId.compareTo(o.getSlideSubId());
            }
        } else{
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher num1 = pattern.matcher(o.blockSubId);
            Matcher num2 = pattern.matcher(this.blockSubId);
            if (num1.matches() && num2.matches()) {
                int i = Integer.parseInt(this.blockSubId);
                int m = Integer.parseInt(o.blockSubId);
                if (i < m) {
                    return -1;
                } else {
                    return 1;
                }
            } else if (!num1.matches() && !num2.matches()) {
                if (o.blockSubId.length() == this.blockSubId.length()) {
                    return this.blockSubId.compareTo(o.blockSubId);
                }else {
                    if(o.blockSubId.length()>this.blockSubId.length()){
                        return -1;
                    }else {
                        return 1;
                    }
                }
            }else {
                return this.blockSubId.compareTo(o.blockSubId);
            }
        }
    }
}
