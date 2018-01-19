package com.lifetech.dhap.pathcloud.common.data;

/**
 * Created by LuoMo on 2016-11-30.
 */
public enum PathologyStatus {
    PrepareRegister(9,"待登记"),
    PrepareGrossing(10,"待取材"),
    PrepareGrossingConfirm(11,"待取材确认"),
    PrepareDehydrate(12,"待脱水"),
    Dehydrating(13,"脱水中"),
    PrepareEmbed(14,"待包埋"),
    PrepareSection(15,"待切片"),
    PrepareDye(16, "待染色"),
    PrepareMounting(17, "待封片"),
    PrepareCompletionConfirm(18,"待制片确认"),
    PrepareCompletionDistribute(19,"待派片"),
    PrepareFirstDiagnose(20,"待一级诊断"),
    PrepareSecondDiagnose(21,"待二级诊断"),
    PrepareThirdDiagnose(22,"待三级诊断"),
    PrepareConsult(23,"待会诊"),
    PrepareReport(24,"待发报告"),
    Report(25,"已发报告"),
    PrepareArchiving(28,"待存档"),
    Archiving(29,"已存档"),
    Ending(30,"已结束"),
    ErrorEnding(40,"异常终止"),
    Cancel(41,"已撤销"),
    ;


    private Integer nCode;

    private String name;


    PathologyStatus(Integer _nCode, String _name) {
        this.nCode = _nCode;
        this.name = _name;
    }

    public Integer toCode(){
        return this.nCode;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static PathologyStatus valueOf(Integer code) {
        for (PathologyStatus status : PathologyStatus.values()){
            if (status.toCode().equals(code)){
                return status;
            }
        }
        return PathologyStatus.PrepareGrossing;
    }

    public static String getNameByCode(Integer code) {
        for (PathologyStatus status : PathologyStatus.values()){
            if (status.toCode().equals(code)){
                return status.name;
            }
        }
        return null;
    }
}
