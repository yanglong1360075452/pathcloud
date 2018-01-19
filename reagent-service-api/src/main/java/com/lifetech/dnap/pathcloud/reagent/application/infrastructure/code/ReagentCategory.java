package com.lifetech.dnap.pathcloud.reagent.application.infrastructure.code;

/**
 * Created by HP on 2017/9/26.
 */
public enum ReagentCategory {

    Reagent(1, "试剂"),
    Consumable(2, "耗材"),
    ;

    private Integer nCode;

    private String name;


    ReagentCategory(Integer _nCode, String _name) {
        this.nCode = _nCode;
        this.name = _name;
    }

    public Integer toCode() {
        return this.nCode;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static ReagentCategory valueOf(Integer code) {
        for (ReagentCategory reagentCategory : ReagentCategory.values()) {
            if (reagentCategory.toCode().equals(code)) {
                return reagentCategory;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (ReagentCategory reagentCategory : ReagentCategory.values()) {
            if (reagentCategory.toCode().equals(code)) {
                return reagentCategory.name;
            }
        }
        return null;
    }
}
