package com.lifetech.dhap.pathcloud.application.application.infrastructure.code;

/**
 * Created by LiuMei on 2017-04-01.
 */
public enum IhcApplicationStatus {

    PrepareConfirm(1,"等待执行"),
    Delay(2,"延迟执行"),
    Cancel(3,"已撤销"),
    Confirm(4,"执行中"),
    HadSection(5,"已切片"),
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
    IhcApplicationStatus(Integer _nCode, String _name) {
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

    public static IhcApplicationStatus valueOf(Integer code) {
        for (IhcApplicationStatus ihcApplicationStatus : IhcApplicationStatus.values()){
            if (ihcApplicationStatus.toCode().equals(code)){
                return ihcApplicationStatus;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (IhcApplicationStatus status : IhcApplicationStatus.values()){
            if (status.toCode().equals(code)){
                return status.toString();
            }
        }
        return null;
    }
}
