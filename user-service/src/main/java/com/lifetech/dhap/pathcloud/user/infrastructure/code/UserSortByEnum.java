package com.lifetech.dhap.pathcloud.user.infrastructure.code;

/**
 * Created by HP on 2016/6/3.
 */
public enum  UserSortByEnum {
    Id(0,"u.id"),
    UserName(1,"u.user_name"),
    FirstName(2, "u.first_name"),
    Phone(3,"u.phone"),
    Email(4,"u.email"),
    Role(5,"u.role_id"),
    CreateTime(6,"u.create_time"),
    Status(7,"u.status")
    ;

    private Integer nCode;

    private String name;


    UserSortByEnum(Integer _nCode, String _name) {
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

    public static UserSortByEnum valueOf(Integer code) {
        for (UserSortByEnum userSortByEnum : UserSortByEnum.values()){
            if (userSortByEnum.toCode().equals(code)){
                return userSortByEnum;
            }
        }
        return UserSortByEnum.Id;
    }
}
