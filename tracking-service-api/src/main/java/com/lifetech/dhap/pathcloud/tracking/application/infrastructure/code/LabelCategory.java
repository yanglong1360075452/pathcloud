package com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code;

/**
 * Created by HP on 2017/9/27.
 */
public enum LabelCategory {

    Patient(1,"罕见"),
    FollowUp(2,"随访"),
    ScientificResearch(3,"科研"),
    teaching(4,"教学"),
    ;

    private Integer nCode;

    private String name;


    LabelCategory(Integer _nCode, String _name) {
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

    public static LabelCategory valueOf(Integer code) {
        for (LabelCategory labelCategory : LabelCategory.values()){
            if (labelCategory.toCode().equals(code)){
                return labelCategory;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (LabelCategory labelCategory : LabelCategory.values()){
            if (labelCategory.toCode().equals(code)){
                return labelCategory.toString();
            }
        }
        return null;
    }
}
