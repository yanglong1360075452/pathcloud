package com.lifetech.dhap.pathcloud.application.infrastructure.code;

/**
 * Created by HP on 2017/8/7.
 */
public enum IhcSortByEnum {

    Id(0,"ai.create_time desc,bi.serial_number,bi.sub_id asc"),
    Serial_number(1,"bi.serial_number"),
    Sub_id(2,"bi.sub_id"),
    Special_dye(3,"bi.special_dye"),
    Apply_user(4,"ai.apply_user"),
    Create_time(5,"ai.create_time "),

    ;

    private Integer nCode;

    private String name;


    IhcSortByEnum(Integer _nCode, String _name) {
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

    public static IhcSortByEnum valueOf(Integer code) {
        for (IhcSortByEnum sortByEnum : IhcSortByEnum.values()){
            if (sortByEnum.toCode().equals(code)){
                return sortByEnum;
            }
        }
        return IhcSortByEnum.Id;
    }

    public static String getNameByCode(Integer code) {
        for (IhcSortByEnum status : IhcSortByEnum.values()){
            if (status.toCode().equals(code)){
                return status.toString();
            }
        }
        return null;
    }

}
