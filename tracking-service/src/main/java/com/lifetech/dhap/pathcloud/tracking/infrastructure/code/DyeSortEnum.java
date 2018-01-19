package com.lifetech.dhap.pathcloud.tracking.infrastructure.code;

/**
 * Created by LuoMo on 2016-11-30.
 */
public enum DyeSortEnum {
    Default(0,"b.status"),
    SerialNo(1,"p.serial_number"),
    BlockSubId(2,"bb.sub_id"),
    SlideSubId(3,"b.sub_id"),
    GrossingDoctor(4,"t.operator_id"),
    BodyPart(5,"b.body_part"),
    Count(6,"b.count"),
    Biaoshi(7,"b.biaoshi"),
    DyeOperator(8,"t1.operator_id"),
    DyeTime(9,"t1.create_time"),
    Status(10,"b.status")
    ;

    private Integer nCode;

    private String name;


    DyeSortEnum(Integer _nCode, String _name) {
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

    public static DyeSortEnum valueOf(Integer code) {
        for (DyeSortEnum sortByEnum : DyeSortEnum.values()){
            if (sortByEnum.toCode().equals(code)){
                return sortByEnum;
            }
        }
        return DyeSortEnum.Default;
    }
}
