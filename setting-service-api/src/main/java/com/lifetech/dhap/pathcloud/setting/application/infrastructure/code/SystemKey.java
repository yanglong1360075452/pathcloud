package com.lifetech.dhap.pathcloud.setting.application.infrastructure.code;

/**
 * Created by LiuMei on 2017-02-22.
 */
public enum  SystemKey {
    /**
     * 系统设置参数
     * param 代表查询参数
     * key 对应参数设置表key字段
     */
    TrackingList("trackingList","tracking_list"),
    TrackingStatus("trackingStatus","tracking_status"),
    TrackingOperation("trackingOperation","tracking_operation"),
    FreezeTrackingList("freezeTrackingList","freeze_tracking_list"),
    FreezeTrackingStatus("freezeTrackingStatus","freeze_tracking_status"),
    FreezeTrackingOperation("freezeTrackingOperation","freeze_tracking_operation"),
    InspectCategory("inspectCategory","inspect_category"),
    ApplicationRequired("applicationRequired","application_required"),
    SpecialDye("specialDye","special_dye"),
    ReportDeadline("reportDeadline","report_deadline"),
    /**
     * 疑难杂症报告期限
     */
    DifficultDeadline("difficultDeadline","difficult_deadline"),
    /**
     * 脱钙报告期限
     */
    DecalcifyDeadline("decalcifyDeadline","decalcify_deadline"),
    QueryTimeRange("queryTimeRange","query_time_range"),
    PrepareGrossingAlarm("prepareGrossingAlarm","prepare_grossing_alarm"),
    PrepareGrossingConfirmAlarm("prepareGrossingConfirmAlarm","prepare_grossing_confirm_alarm"),
    PrepareDehydrateAlarm("prepareDehydrateAlarm","prepare_dehydrate_alarm"),
    PrepareEmbedAlarm("prepareEmbedAlarm","prepare_embed_alarm"),
    PrepareSectionAlarm("prepareSectionAlarm","prepare_section_alarm"),
    PrepareDyeAlarm("prepareDyeAlarm","prepare_Dye_alarm"),
    PrepareConfirmAlarm("prepareConfirmAlarm","prepare_confirm_alarm"),
    FreezeReportDeadline("freezeReportDeadline","freeze_report_deadline"),
    IhcReportDeadline("ihcReportDeadline","ihc_report_deadline"),
    SpecialDyeReportDeadline("specialDyeReportDeadline","special_dye_report_deadline"),
    ConsultReportDeadline("consultReportDeadline","consult_report_deadline"),
    Hospital("hospital","hospital"),
    SampleCategory("sampleCategory","sample_category"),
    PathNoRule("pathNoRule","path_no_rule"),
    UsingFrozen("usingFrozen","using_frozen"),
    PrintFrozen("printFrozen","print_frozen"),
    MultiFrozen("multiFrozen","multi_frozen"),
    SpecialNumberPrint("specialNumberPrint","special_number_print"),
    ReagentUsage("reagentUsage","reagent_usage"),
    ;

    private String param;

    private String key;

    SystemKey(String param, String key) {
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

    public static SystemKey getKeyByParam(String param) {
        for (SystemKey systemKey : SystemKey.values()){
            if (systemKey.toParam().equals(param)){
                return systemKey;
            }
        }
        return null;
    }
}
