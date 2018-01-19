package com.lifetech.dhap.pathcloud.setting.application.infrastructure.code;

/**
 * @author LiuMei
 * @date 2017-10-17.
 */
public enum  TrackingList {

    Application(1,"病理申请"),
    Register(2,"登记"),
    Grossing(3,"取材"),
    Dehydrate(4,"脱水"),
    Embed(5,"包埋"),
    Section(6,"切片"),
    Dye(7,"染色"),
    Confirm(8,"制片确认"),
    Diagnose(9,"病理诊断"),
    Report(10,"报告"),
    Archive(11,"存档"),
            ;
    /**
     * 定义私有变量
     */
    private Integer nCode;

    private String name;

    /**
     * 构造函数，枚举类型只能为私有
     * @param _nCode
     * @param _name
     */
    TrackingList(Integer _nCode, String _name) {
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

    public static TrackingList valueOf(Integer code) {
        for (TrackingList trackingList : TrackingList.values()){
            if (trackingList.toCode().equals(code)){
                return trackingList;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (TrackingList status : TrackingList.values()){
            if (status.toCode().equals(code)){
                return status.toString();
            }
        }
        return null;
    }
}
