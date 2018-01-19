package com.lifetech.dhap.pathcloud.application.infrastructure.code;

/**
 * Created by LuoMo on 2016-11-30.
 */
public enum DyeApplySortEnum {
    Default(0,"ai.create_time desc,bi.serial_number,bi.sub_id "),
    SerialNo(1,"bi.serial_number"),
    BlockSubId(2,"bi.sub_id"),
    DyeType(3,"bi.special_dye"),
    Marker(4,"bi.ihc_marker"),
    Note(5,"bi.note"),
    ApplyUser(6,"ai.apply_user"),
    Phone(7,"ai.apply_tel"),
    ApplyTime(8,"ai.create_time"),
    Status(9,"bi.status"),
    BlockStatus(10,"b.status"),
    ;

    private Integer nCode;

    private String name;


    DyeApplySortEnum(Integer _nCode, String _name) {
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

    public static DyeApplySortEnum valueOf(Integer code) {
        for (DyeApplySortEnum sortByEnum : DyeApplySortEnum.values()){
            if (sortByEnum.toCode().equals(code)){
                return sortByEnum;
            }
        }
        return DyeApplySortEnum.Default;
    }
}
