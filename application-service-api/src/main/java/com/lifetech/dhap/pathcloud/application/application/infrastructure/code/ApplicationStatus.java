package com.lifetech.dhap.pathcloud.application.application.infrastructure.code;

/**
 * Created by LuoMo on 2016-11-22.
 */
public enum ApplicationStatus {
    PrepareRegister(1,"待登记"),
    Register(2,"已登记"),
    Reject(3,"已拒收"),
    Cancel(4,"已撤销"),
    Ending(30,"已结束")
    ;

    private Integer nCode;

    private String name;


    ApplicationStatus(Integer _nCode, String _name) {
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

    public static ApplicationStatus valueOf(Integer code) {
        for (ApplicationStatus applicationStatus : ApplicationStatus.values()){
            if (applicationStatus.toCode().equals(code)){
                return applicationStatus;
            }
        }
        return null;
    }
}
