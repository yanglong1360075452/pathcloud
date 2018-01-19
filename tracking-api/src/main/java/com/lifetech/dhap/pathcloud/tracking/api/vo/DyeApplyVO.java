package com.lifetech.dhap.pathcloud.tracking.api.vo;

import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LiuMei on 2017-04-26.
 */
public class DyeApplyVO implements Serializable {

    private Long applyId;

    private UserSimpleVO applicant;

    private Integer specialDye;
    private String specialDyeDesc;

    private List<SectionSlideVO> sectionSlides;

    private String note;

    public List<SectionSlideVO> getSectionSlides() {
        return sectionSlides;
    }

    public void setSectionSlides(List<SectionSlideVO> sectionSlides) {
        this.sectionSlides = sectionSlides;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public UserSimpleVO getApplicant() {
        return applicant;
    }

    public void setApplicant(UserSimpleVO applicant) {
        this.applicant = applicant;
    }

    public Integer getSpecialDye() {
        return specialDye;
    }

    public void setSpecialDye(Integer specialDye) {
        this.specialDye = specialDye;
    }

    public String getSpecialDyeDesc() {
        return specialDyeDesc;
    }

    public void setSpecialDyeDesc(String specialDyeDesc) {
        this.specialDyeDesc = specialDyeDesc;
    }



    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
