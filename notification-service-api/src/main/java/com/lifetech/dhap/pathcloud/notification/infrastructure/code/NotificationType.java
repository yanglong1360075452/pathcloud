package com.lifetech.dhap.pathcloud.notification.infrastructure.code;

/**
 * Created by LiuMei on 2017-01-11.
 */
public enum NotificationType {

    withChoice(1,"需要选择处理方式"),
    withoutChoice(2,"不需要选择处理方式")
    ;

    private Integer nCode;

    private String name;


    NotificationType(Integer _nCode, String _name) {
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

    public static NotificationType valueOf(Integer code) {
        for (NotificationType type : NotificationType.values()){
            if (type.toCode().equals(code)){
                return type;
            }
        }
        return null;
    }
}
