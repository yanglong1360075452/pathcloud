package com.lifetech.dhap.pathcloud.wechat.data;

/**
 * Created by LiuMei on 2017-06-08.
 */
public enum WechatApplyType {


    Normal(1,"常规染色"),
    Freeze(2,"冰冻预约"),
    Dye(3,"染色申请"),
    ;

    private Integer nCode;

    private String name;


    WechatApplyType(Integer _nCode, String _name) {
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

    public static WechatApplyType valueOf(Integer code) {
        for (WechatApplyType applyType : WechatApplyType.values()){
            if (applyType.toCode().equals(code)){
                return applyType;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (WechatApplyType applyType : WechatApplyType.values()){
            if (applyType.toCode().equals(code)){
                return applyType.toString();
            }
        }
        return null;
    }
}
