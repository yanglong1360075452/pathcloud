package com.lifetech.dhap.pathcloud.tracking.infrastructure.code;

/**
 * Created by LuoMo on 2016-11-30.
 */
public enum GrossingSortByEnum {

    Id(0,"t.id"),
    SerialNo(1,"t.serial_number"),
    PatientName(2,"t2.patient_name"),
    Departments(3,"t2.departments"),
    SubId(4,"t1.sub_id"),
    BodyPart(5,"t1.body_part"),
    Count(6,"t1.count"),
    Biaoshi(7,"t1.biaoshi"),
    OperatorId(8,"t3.operator_id"),
    SecOperatorId(9,"t3.sec_operator_id"),
    InstrumentId(10,"t3.instrument_id"),
    OperationTime(11,"t3.operation_time"),
    Status(12,"t1.status")
    ;

    private Integer nCode;

    private String name;


    GrossingSortByEnum(Integer _nCode, String _name) {
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

    public static GrossingSortByEnum valueOf(Integer code) {
        for (GrossingSortByEnum sortByEnum : GrossingSortByEnum.values()){
            if (sortByEnum.toCode().equals(code)){
                return sortByEnum;
            }
        }
        return GrossingSortByEnum.Id;
    }
}
