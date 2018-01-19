package com.lifetech.dnap.pathcloud.reagent.application.infrastructure.code;

/**
 * Created by HP on 2017/9/26.
 */
public enum ReagentType {

    NormalReagent(1, "常规试剂"),
    IHCReagent(2, "免疫组化试剂"),
    Danger(3, "危险品"),
    Flammable(4, "易燃易爆"),
    EmbeddingBox(10, "包埋盒"),
    NormalSlide(11, "常规玻片")
    ;

    private Integer nCode;

    private String name;


    ReagentType(Integer _nCode, String _name) {
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

    public static ReagentType valueOf(Integer code) {
        for (ReagentType reagentCategory : ReagentType.values()) {
            if (reagentCategory.toCode().equals(code)) {
                return reagentCategory;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (ReagentType reagentCategory : ReagentType.values()) {
            if (reagentCategory.toCode().equals(code)) {
                return reagentCategory.name;
            }
        }
        return null;
    }
}
