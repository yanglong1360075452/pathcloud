package com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-07-14:46
 */
public enum TrackingOperation {
    grossing(1,"取材"),
    grossingConfirm(2,"取材确认"),
    dehydrate(3,"脱水"),
    dehydrateEnd(4,"结束脱水"),
    embed(5,"包埋"),
    section(6,"切片"),
    dye(7,"染色"),
    completionConfirm(8,"制片确认"),
    slideDistribute(9,"派片"),
    firstDiagnose(10,"一级诊断"),
    secondDiagnose(11,"二级诊断"),
    thirdDiagnose(12,"三级诊断"),
    report(13,"出报告"),
    archive(14,"存档"),
    watchSlide(15,"查看玻片"),
    errorEnd(16,"异常终止"),
    applyDeepSection(17,"深切"),
    dyeConfirm(18,"染色确认"),
    applyReGrossing(19,"重补取"),
    applyReSection(20,"申请重切"),
    consult(21,"会诊"),
    mounting(22,"封片"),
    printReport(23,"打印报告"),
    cancelDye(24,"撤销染色申请"),
    printSign(25,"打印签收单"),
    confirmSign(26,"签收确认"),
    register(27,"登记"),
    cancelRegister(28,"撤销登记"),
    embedPause(29,"包埋暂停"),
    specialDyeApply(30,"辅助检查"),
    drying(31,"晾片"),
    embedPauseCancel(32,"取消包埋暂停"),
    grossingCancel(33,"取材撤销"),
    printEmbedBox(34,"打印包埋盒"),
    frozenGrossing(35,"冰冻取材"),
    frozenSection(36,"冰冻切片"),
    printSlide(37,"打印玻片"),
    saveDiagnose(38,"保存诊断结果"),
    ;

    private Integer nCode;

    private String name;


    TrackingOperation(Integer _nCode, String _name) {
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

    public static TrackingOperation valueOf(Integer code) {
        for (TrackingOperation trackingOperation : TrackingOperation.values()){
            if (trackingOperation.toCode().equals(code)){
                return trackingOperation;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (TrackingOperation trackingOperation : TrackingOperation.values()){
            if (trackingOperation.toCode().equals(code)){
                return trackingOperation.toString();
            }
        }
        return null;
    }
}
