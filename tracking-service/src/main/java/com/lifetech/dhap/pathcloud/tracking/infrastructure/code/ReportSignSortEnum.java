package com.lifetech.dhap.pathcloud.tracking.infrastructure.code;

/**
 * Created by LuoMo on 2016-11-30.
 */
public enum ReportSignSortEnum {
    Default(0,"reportTime"),
    SerialNo(1,"serialNumber"),
    PatientName(2,"name"),
    DiagnoseDoctor(3,"diagnoseDoctor"),
    Departments(4,"departments"),
    InspectDoctor(5,"doctor"),
    ReportTime(6,"reportTime"),
    ;

    private Integer nCode;

    private String name;


    ReportSignSortEnum(Integer _nCode, String _name) {
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

    public static ReportSignSortEnum valueOf(Integer code) {
        for (ReportSignSortEnum sortByEnum : ReportSignSortEnum.values()){
            if (sortByEnum.toCode().equals(code)){
                return sortByEnum;
            }
        }
        return ReportSignSortEnum.Default;
    }
}
