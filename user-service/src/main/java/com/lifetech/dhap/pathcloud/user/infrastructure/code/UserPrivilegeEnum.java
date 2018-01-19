package com.lifetech.dhap.pathcloud.user.infrastructure.code;

/**
 * Created by gdw on 4/18/16.
 */
public enum UserPrivilegeEnum {

    Admin(true, "管理员"),
    Normal(false, "普通用户")
    ;

    private Boolean nCode;

    private String name;


    UserPrivilegeEnum(Boolean _nCode, String _name) {
        this.nCode = _nCode;
        this.name = _name;
    }

    public Boolean toCode(){
        return this.nCode;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static UserPrivilegeEnum valueOf(Integer code) {
        switch (code) {
            case 1:
                return UserPrivilegeEnum.Admin;
            case 0:
                return UserPrivilegeEnum.Normal;
            default:
                return UserPrivilegeEnum.Normal;
        }
    }

    public static UserPrivilegeEnum valueOf(Boolean code) {
        if(code.equals(true)){
            return UserPrivilegeEnum.Admin;
        }else{
            return UserPrivilegeEnum.Normal;
        }
    }

}
