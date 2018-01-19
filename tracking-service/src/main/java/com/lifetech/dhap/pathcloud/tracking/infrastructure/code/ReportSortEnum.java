package com.lifetech.dhap.pathcloud.tracking.infrastructure.code;

/**
 * Created by LuoMo on 2016-11-30.
 */
public enum ReportSortEnum {
    Default(0,"operation"),
    SerialNo(1,"serial_number"),
    PatientName(2,"patient_name"),
    DiagnoseDoctor(3,"operatorId"),
    Departments(4,"departments"),
    InspectDoctor(5,"doctor"),
    ReportTime(6,"reportTime"),
    ReportStatus(7,"DATEDIFF(reportTime,create_time)"),
    Delay(8,"DATEDIFF(reportTime,create_time)"),
    PrintStatus(9,"printTime")
    ;

    private Integer nCode;

    private String name;


    ReportSortEnum(Integer _nCode, String _name) {
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

    public static ReportSortEnum valueOf(Integer code) {
        for (ReportSortEnum sortByEnum : ReportSortEnum.values()){
            if (sortByEnum.toCode().equals(code)){
                return sortByEnum;
            }
        }
        return ReportSortEnum.Default;
    }
}
