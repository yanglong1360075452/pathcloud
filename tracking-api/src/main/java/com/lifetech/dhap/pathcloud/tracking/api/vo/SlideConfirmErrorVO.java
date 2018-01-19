package com.lifetech.dhap.pathcloud.tracking.api.vo;

import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;

import java.util.Date;
import java.util.List;

/**
 * Created by LiuMei on 2017-01-06.
 */
public class SlideConfirmErrorVO {

    private long pathId;

    //病理号
    private String pathNo;

    //待制片确认玻片总数
    private Integer slidePrepareConfirmCount;

    private Integer lostSlideCount;

    //缺失玻片信息
    private List<SlideLostVO> lostSlides;

    //缺失蜡块信息
    private List<SlideLostVO> lostBlocks;

    private Integer status; //病理状态
    private String statusDesc;

    private UserSimpleVO lastOperator; //病理最后操作者

    private Date lastOperateTime; //病理最后操作时间

    private Boolean reGrossing; //是否重补取

    public Boolean getReGrossing() {
        return reGrossing;
    }

    public void setReGrossing(Boolean reGrossing) {
        this.reGrossing = reGrossing;
    }

    public UserSimpleVO getLastOperator() {
        return lastOperator;
    }

    public void setLastOperator(UserSimpleVO lastOperator) {
        this.lastOperator = lastOperator;
    }

    public Date getLastOperateTime() {
        return lastOperateTime;
    }

    public void setLastOperateTime(Date lastOperateTime) {
        this.lastOperateTime = lastOperateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public long getPathId() {
        return pathId;
    }

    public void setPathId(long pathId) {
        this.pathId = pathId;
    }

    public List<SlideLostVO> getLostBlocks() {
        return lostBlocks;
    }

    public void setLostBlocks(List<SlideLostVO> lostBlocks) {
        this.lostBlocks = lostBlocks;
    }

    public Integer getLostSlideCount() {
        return lostSlideCount;
    }

    public void setLostSlideCount(Integer lostSlideCount) {
        this.lostSlideCount = lostSlideCount;
    }

    public String getPathNo() {
        return pathNo;
    }

    public void setPathNo(String pathNo) {
        this.pathNo = pathNo;
    }

    public Integer getSlidePrepareConfirmCount() {
        return slidePrepareConfirmCount;
    }

    public void setSlidePrepareConfirmCount(Integer slidePrepareConfirmCount) {
        this.slidePrepareConfirmCount = slidePrepareConfirmCount;
    }

    public List<SlideLostVO> getLostSlides() {
        return lostSlides;
    }

    public void setLostSlides(List<SlideLostVO> lostSlides) {
        this.lostSlides = lostSlides;
    }
}
