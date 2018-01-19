package com.lifetech.dhap.pathcloud.user.infrastructure.code;

/**
 * Created by gdw on 4/18/16.
 */
public enum UserStatusEnum {

    OK(1, "正常"),
    FORBIDDEN(0, "禁用")
    ;

    private Integer nCode;

    private String name;


    UserStatusEnum(Integer _nCode, String _name) {
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

    public static UserStatusEnum valueOf(Integer code) {
        switch (code) {
            case 1:
                return UserStatusEnum.OK;
            case 0:
                return UserStatusEnum.FORBIDDEN;
            default:
                return UserStatusEnum.OK;
        }
    }

    public static UserStatusEnum valueOf(Boolean code) {
        if(code){
            return UserStatusEnum.OK;
        }else{
            return UserStatusEnum.FORBIDDEN;
        }
    }

}
