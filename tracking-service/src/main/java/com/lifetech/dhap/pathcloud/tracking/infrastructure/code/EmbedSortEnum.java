package com.lifetech.dhap.pathcloud.tracking.infrastructure.code;

/**
 * Created by LuoMo on 2016-11-30.
 */
public enum EmbedSortEnum {
    Default(0,"b.status"),
    SerialNo(1,"p.serial_number"),
    BlockSubId(2,"b.sub_id"),
    patientName(3,"p.patient_name"),
    Departments(4,"p.departments"),
    GrossingDoctor(5,"t.operator_id"),
    BodyPart(6,"b.body_part"),
    Count(7,"b.count"),
    Biaoshi(8,"b.biaoshi"),
    EmbedOperator(9,"t1.operator_id"),
    EmbedTime(10,"t1.operation_time"),
    Note(11,"t1.note_type"),
    Status(12,"b.status")
    ;

    private Integer nCode;

    private String name;


    EmbedSortEnum(Integer _nCode, String _name) {
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

    public static EmbedSortEnum valueOf(Integer code) {
        for (EmbedSortEnum sortByEnum : EmbedSortEnum.values()){
            if (sortByEnum.toCode().equals(code)){
                return sortByEnum;
            }
        }
        return EmbedSortEnum.Default;
    }
}
