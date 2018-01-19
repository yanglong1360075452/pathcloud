package com.lifetech.dhap.pathcloud.notification.infrastructure.code;

/**
 * 消息来源
 *
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-07-14:46
 */
public enum NotificationSource {

    grossing(1,"取材"),
    grossingConfirm(2,"取材确认"),
    dehydrate(3,"脱水"),
    dehydrateEnd(4,"结束脱水"),
    embed(5,"包埋"),
    section(6,"切片"),
    dye(7,"染色"),
    completionConfirm(8,"制片确认"),
    slideDistribute(9,"派片"),
    diagnose(10,"诊断"),
    report(12,"出报告"),
    archive(13,"存档"),
    ;

    private Integer nCode;

    private String name;


    NotificationSource(Integer _nCode, String _name) {
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

    public static NotificationSource valueOf(Integer code) {
        for (NotificationSource applicationStatus : NotificationSource.values()){
            if (applicationStatus.toCode().equals(code)){
                return applicationStatus;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (NotificationSource status : NotificationSource.values()){
            if (status.toCode().equals(code)){
                return status.toString();
            }
        }
        return null;
    }

}
