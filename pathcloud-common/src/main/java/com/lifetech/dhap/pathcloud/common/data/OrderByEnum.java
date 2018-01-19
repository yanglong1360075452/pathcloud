package com.lifetech.dhap.pathcloud.common.data;

/**
 * Created by HP on 2016/6/3.
 */
public enum  OrderByEnum {
    Asc(1, "asc"),
    Desc(0, "desc")
    ;

    private Integer nCode;

    private String name;


    OrderByEnum(Integer _nCode, String _name) {
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

    public static OrderByEnum valueOf(Integer code) {
        switch (code) {
            case 1:
                return OrderByEnum.Asc;
            case 0:
                return OrderByEnum.Desc;
            default:
                return OrderByEnum.Desc;
        }
    }

    public static OrderByEnum valueOf(Boolean code) {
        if(code){
            return OrderByEnum.Asc;
        }else{
            return OrderByEnum.Desc;
        }
    }
}
