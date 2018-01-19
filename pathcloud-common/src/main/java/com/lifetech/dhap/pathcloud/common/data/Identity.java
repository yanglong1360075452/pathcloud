package com.lifetech.dhap.pathcloud.common.data;

/**
 * Created by LiuMei on 2017-03-31.
 *
 * 科研申请--申请者身份
 */
public enum Identity {

    Undergraduate(0,"本科生"),
    Master(1,"硕士研究生"),
    Doctor(2,"博士研究生"),
    PostDoctor(3,"博士后"),
    Clinic(4,"临床医生"),
    Research(5,"研究人员"),
    Technicist(6,"技术人员"),
    Other(7,"其他")
    ;

    private Integer nCode;

    private String name;


    Identity(Integer _nCode, String _name) {
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

    public static Identity valueOf(Integer code) {
        for (Identity identity : Identity.values()){
            if (identity.toCode().equals(code)){
                return identity;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (Identity identity : Identity.values()){
            if (identity.toCode().equals(code)){
                return identity.name;
            }
        }
        return null;
    }

}
