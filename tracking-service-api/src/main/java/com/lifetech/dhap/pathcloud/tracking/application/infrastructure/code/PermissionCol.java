package com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code;

/**
 * Created by HP on 2017/9/27.
 */
public enum PermissionCol {

    Private(1,"仅自己可见"),
    Public(2,"公开"),
    ;

    private Integer nCode;

    private String name;


    PermissionCol(Integer _nCode, String _name) {
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

    public static PermissionCol valueOf(Integer code) {
        for (PermissionCol permission : PermissionCol.values()){
            if (permission.toCode().equals(code)){
                return permission;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (PermissionCol permission : PermissionCol.values()){
            if (permission.toCode().equals(code)){
                return permission.toString();
            }
        }
        return null;
    }
}
