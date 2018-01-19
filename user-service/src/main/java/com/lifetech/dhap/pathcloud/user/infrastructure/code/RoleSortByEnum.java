package com.lifetech.dhap.pathcloud.user.infrastructure.code;

/**
 * Created by LuoMo on 2016-11-16.
 */
public enum RoleSortByEnum {
    Id(0,"id"),
    Name(1,"name"),
    CreateTime(2,"create_time")
    ;

    private Integer nCode;

    private String name;


    RoleSortByEnum(Integer _nCode, String _name) {
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

    public static RoleSortByEnum valueOf(Integer code) {
        for (RoleSortByEnum roleSortByEnum : RoleSortByEnum.values()){
            if (roleSortByEnum.toCode().equals(code)){
                return roleSortByEnum;
            }
        }
        return RoleSortByEnum.Id;
    }
}
