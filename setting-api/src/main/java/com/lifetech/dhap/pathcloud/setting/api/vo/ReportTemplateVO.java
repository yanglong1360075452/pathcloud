package com.lifetech.dhap.pathcloud.setting.api.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HP on 2017/1/12.
 */
public class ReportTemplateVO  implements Serializable{

    private String imageURL;

    private  String reportBigName;

    private  String reportSmallName;

    private List<ProjectContentVO> patientInformation;

    private List<ProjectContentVO> diagnosticContent;

    private List<ProjectContentVO> diagnosticInformation;

    private String hospitalInformation;

    private String specialTip;

    private String subheading;

    public String getSubheading() {
        return subheading;
    }

    public void setSubheading(String subheading) {
        this.subheading = subheading;
    }

    public String getReportSmallName() {
        return reportSmallName;
    }

    public void setReportSmallName(String reportSmallName) {
        this.reportSmallName = reportSmallName;
    }

    public List<ProjectContentVO> getPatientInformation() {
        return patientInformation;
    }

    public void setPatientInformation(List<ProjectContentVO> patientInformation) {
        this.patientInformation = patientInformation;
    }

    public List<ProjectContentVO> getDiagnosticContent() {
        return diagnosticContent;
    }

    public void setDiagnosticContent(List<ProjectContentVO> diagnosticContent) {
        this.diagnosticContent = diagnosticContent;
    }

    public List<ProjectContentVO> getDiagnosticInformation() {
        return diagnosticInformation;
    }

    public void setDiagnosticInformation(List<ProjectContentVO> diagnosticInformation) {
        this.diagnosticInformation = diagnosticInformation;
    }

    public String getHospitalInformation() {
        return hospitalInformation;
    }

    public void setHospitalInformation(String hospitalInformation) {
        this.hospitalInformation = hospitalInformation;
    }

    public String getSpecialTip() {
        return specialTip;
    }

    public void setSpecialTip(String specialTip) {
        this.specialTip = specialTip;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getReportBigName() {
        return reportBigName;
    }

    public void setReportBigName(String reportBigName) {
        this.reportBigName = reportBigName;
    }
}
