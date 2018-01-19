package com.lifetech.dhap.pathcloud.application.infrastructure.code;

/**
 * Created by LuoMo on 2016-11-30.
 */
public enum PathologySampleSortByEnum {
    Id(0,"id"),
    SerialNo(1,"serial_number"),
    SampleNo(2,"sample_number"),
    PathologyNo(3,"pathology_number"),
    SampleName(4,"name"),
    PatientName(5,"patient_name"),
    InspectionItem(6,"inspection_item"),
    Departments(7,"departments"),
    Doctor(8,"doctor"),
    DoctorTel(9,"doctor_tel"),
    InspectionDate(10,"create_time"),
    PathStatus(11,"pathology_status"),
    ApplicationStatus(12,"status"),
    AssignGrossing(13,"assign_grossing"),
    ;

    private Integer nCode;

    private String name;


    PathologySampleSortByEnum(Integer _nCode, String _name) {
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

    public static PathologySampleSortByEnum valueOf(Integer code) {
        for (PathologySampleSortByEnum sortByEnum : PathologySampleSortByEnum.values()){
            if (sortByEnum.toCode().equals(code)){
                return sortByEnum;
            }
        }
        return PathologySampleSortByEnum.Id;
    }

    public static String getNameByCode(Integer code) {
        for (PathologySampleSortByEnum status : PathologySampleSortByEnum.values()){
            if (status.toCode().equals(code)){
                return status.toString();
            }
        }
        return null;
    }
}
