package com.lifetech.dhap.pathcloud.tracking.infrastructure.code;

/**
 * Created by LuoMo on 2016-11-30.
 */
public enum DistributeSortEnum {
    Default(0,"t2.operation_time"),
    SerialNo(1,"p.serial_number"),
    SlideCount(2,"count(s.id)"),
    GrossingDoctor(3,"t.operator_id"),
    Departments(4,"p.departments"),
    ConfirmTime(5,"t2.operation_time"),
    ConfirmOperator(6,"t2.operator_id"),
    ;

    private Integer nCode;

    private String name;


    DistributeSortEnum(Integer _nCode, String _name) {
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

    public static DistributeSortEnum valueOf(Integer code) {
        for (DistributeSortEnum sortByEnum : DistributeSortEnum.values()){
            if (sortByEnum.toCode().equals(code)){
                return sortByEnum;
            }
        }
        return DistributeSortEnum.Default;
    }
}
