package com.lifetech.dhap.pathcloud.setting.application.infrastructure.code;

/**
 * Created by HP on 2017/1/9.
 */
public enum TemplateCategory {

    Classify(0,"分类"),
    Template(1,"模板"),
    ;


    private Integer nCode;
    private String name;

    TemplateCategory(Integer _nCode, String _name){
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

    public static TemplateCategory valueOf(Integer code) {
        for (TemplateCategory data : TemplateCategory.values()){
            if (data.toCode().equals(code)){
                return data;
            }
        }
        return null;
    }


}
