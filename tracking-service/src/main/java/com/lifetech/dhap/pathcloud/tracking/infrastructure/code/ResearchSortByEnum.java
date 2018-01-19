package com.lifetech.dhap.pathcloud.tracking.infrastructure.code;

/**
 * Created by HP on 2017/7/10.
 */
public enum ResearchSortByEnum {
    Id(0,"p.id"),
    SerialNumber(1,"p.serial_number"),
    Applicant(2,"a.applicant"),
    Status(3,"p.status"),
    Phone(4,"a.doctor_tel"),
//    Identity(5,""),
//    Tutor(6,""),
    Departments(7,"a.departments"),
//    ProjectType(8,""),
//    ProjectCode(9,""),
    ApplyTime(10,"a.create_time"),
    ;

    private Integer nCode;

    private String name;


    ResearchSortByEnum(Integer _nCode, String _name) {
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

    public static ResearchSortByEnum valueOf(Integer code) {
        for (ResearchSortByEnum sortByEnum : ResearchSortByEnum.values()){
            if (sortByEnum.toCode().equals(code)){
                return sortByEnum;
            }
        }
        return ResearchSortByEnum.Id;
    }

    public static String getNameByCode(Integer code) {
        for (ResearchSortByEnum status : ResearchSortByEnum.values()){
            if (status.toCode().equals(code)){
                return status.toString();
            }
        }
        return null;
    }
}
