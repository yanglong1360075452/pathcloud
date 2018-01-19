package com.lifetech.dhap.pathcloud.common.data;

/**
 * Created by HP on 2017/1/9.
 */
public enum TemplatePosition {

    GrossingPosition(1,"取材模板"),
    DiagnosePosition(2,"诊断模板-系统模板"),
    SpecialPosition(3,"特染模板"),
    ReportPosition(4,"报告摸板"),
    DiagnoseCustomPosition(5,"诊断模板-自定义模板"),
    GrossingCustomPosition(6,"取材模板-自定义模板"),
    ;


    private Integer nCode;
    private String name;

    TemplatePosition(Integer _nCode,String _name){
        this.nCode=_nCode;
        this.name=_name;
    }

    public Integer toCode(){
        return this.nCode;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static TemplatePosition valueOf(Integer code) {
        for (TemplatePosition position : TemplatePosition.values()){
            if (position.toCode().equals(code)){
                return position;
            }
        }
        return null;
    }


}
