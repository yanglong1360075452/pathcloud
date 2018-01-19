package com.lifetech.dhap.pathcloud.tracking.infrastructure.code;

/**
 * Created by LuoMo on 2016-11-30.
 */
public enum DiagnoseSortEnum {
    Default(0,"id"),
    SerialNo(1,"serial_number"),
    PatientName(2,"patient_name"),
    Departments(3,"departments"),
    First(4,"first_diagnose_doctor"),
    Second(5,"second_diagnose_doctor"),
    Third(6,"third_diagnose_doctor"),
    Report(7,"report_doctor"),
    ReportTime(8,"report_time"),
    Status(9,"status")
    ;

    private Integer nCode;

    private String name;


    DiagnoseSortEnum(Integer _nCode, String _name) {
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

    public static DiagnoseSortEnum valueOf(Integer code) {
        for (DiagnoseSortEnum sortByEnum : DiagnoseSortEnum.values()){
            if (sortByEnum.toCode().equals(code)){
                return sortByEnum;
            }
        }
        return DiagnoseSortEnum.Default;
    }
}
