package com.lifetech.dhap.pathcloud.tracking.api.vo;

import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LiuMei on 2017-02-09.
 * <p>
 * 查询工作台-样本信息-制片信息
 */
public class QueryConfirmVO implements Comparable<QueryConfirmVO> {

    private String blockSubId;

    private String slideSubId;

    private Integer operation;
    private String operationDesc;

    private UserSimpleVO operator;

    private Date operationTime;

    private Long waitTime;

    private String note;

    private String noteType;

    private String marker;

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Long waitTime) {
        this.waitTime = waitTime;
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

    public UserSimpleVO getOperator() {
        return operator;
    }

    public void setOperator(UserSimpleVO operator) {
        this.operator = operator;
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

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    @Override
    public int compareTo(QueryConfirmVO o) {
        if(this.blockSubId == null && o.getBlockSubId() == null) {
            return 0;
        }
        if(this.blockSubId == null) {
            return -1;
        }
        if(o.getBlockSubId() == null) {
            return 1;
        }
        if (this.blockSubId.equals(o.blockSubId) && this.slideSubId == null) {
            return operationTime.compareTo(o.getOperationTime());
        } else if(this.blockSubId.equals(o.blockSubId) && this.slideSubId != null){
            if(o.getSlideSubId() == null){
                return 1;
            }else if(this.slideSubId.equals(o.getSlideSubId())){
                return operationTime.compareTo(o.getOperationTime());
            }else{
                return this.slideSubId.compareTo(o.getSlideSubId());
            }
        } else {
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
