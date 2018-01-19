package com.lifetech.dhap.pathcloud.setting.application.infrastructure.code;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-07-13:19
 */
public enum  ParamKey {
    /**
     * param 代表查询参数
     * key 对应参数设置表key字段
     */
    Departments("departments","departments"),
    BlockBiaoshi("blockBiaoshi","block_biaoshi"),
    BlockCountType("blockCountType","count_type"),
    FrozenCountType("frozenCountType","frozen_count_type"),
    BlockUnit("blockUnit","block_unit"),
    RejectReason("rejectReason","reject_reason"),
    EmbedRemark("embedRemark","embed_remark"),
    SectionRemark("sectionRemark","section_remark"),
    QualityScore("qualityScore","quality_Score"),
    PrintType("printType","print_type"),
    PrintCount("printCount","print_count"),
    GrossingConfirmPhoto("grossingConfirmPhoto","grossing_confirm_photo"),
    ApplicationDefault("applicationDefault","application_default"),
    grossingNote("grossingNote","grossing_note"),
    dyeType("dyeType","dye_type"),
    InspectHospital("inspectHospital","inspect_hospital"),
    SectionPrintMedium("sectionPrintMedium","section_print_medium"),
    SectionPrintWay("sectionPrintWay","section_print_way"),
    GrossingTemplate("grossingTemplate","grossing_template"),
    DiagnoseTemplate("diagnoseTemplate","diagnose_template"),
    ;

    private String param;

    private String key;

    ParamKey(String param, String key) {
        this.param = param;
        this.key = key;
    }

    public String toParam(){
        return this.param;
    }

    @Override
    public String toString() {
        return this.key;
    }

    public static ParamKey getKeyByParam(String param) {
        for (ParamKey paramKey : ParamKey.values()){
            if (paramKey.toParam().equals(param)){
                return paramKey;
            }
        }
        return null;
    }
}
