package com.lifetech.dhap.pathcloud.tracking.infrastructure.code;

/**
 * Created by LuoMo on 2016-11-30.
 */
public enum DistributeHistorySortEnum {
    Default(0,"t3.operation_time"),
    SerialNo(1,"p.serial_number"),
    BlockSubId(2,"b.sub_id"),
    SlideSubId(3,"s.sub_id"),
    GrossingDoctor(4,"t.operator_id"),
    Departments(5,"p.departments"),
    ConfirmOperator(6,"t2.operator_id"),
    ConfirmTime(7,"t2.operation_time"),
    Receiver(8,"t3.note"),
    DistributeTime(9,"t3.operation_time"),
    ;

    private Integer nCode;

    private String name;


    DistributeHistorySortEnum(Integer _nCode, String _name) {
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

    public static DistributeHistorySortEnum valueOf(Integer code) {
        for (DistributeHistorySortEnum sortByEnum : DistributeHistorySortEnum.values()){
            if (sortByEnum.toCode().equals(code)){
                return sortByEnum;
            }
        }
        return DistributeHistorySortEnum.Default;
    }
}
