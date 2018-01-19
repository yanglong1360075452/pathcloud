package com.lifetech.dhap.pathcloud.tracking.infrastructure.code;

/**
 * Created by HP on 2017/7/10.
 */
public enum CaseSortByEnum {

    Id(0,"p.id"),
    His_id(1,"a.his_id"),
    Serial_number(2,"p.serial_number"),
    Patient_name(3,"a.patient_name"),
    Sex(4,"a.sex"),
    Age(5,"a.age"),
    Admission_no(6,"a.admission_no"),
    Departments(7,"a.departments"),
    Create_by(8,"a.create_by"),
    Hospital(9,"a.hospital"),
    Create_time(10," p.create_time"),
    Operation_time(11,"pt.operation_time"),
    Status(12," p.status"),
    ;

    private Integer nCode;

    private String name;


    CaseSortByEnum(Integer _nCode, String _name) {
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

    public static CaseSortByEnum valueOf(Integer code) {
        for (CaseSortByEnum sortByEnum : CaseSortByEnum.values()){
            if (sortByEnum.toCode().equals(code)){
                return sortByEnum;
            }
        }
        return CaseSortByEnum.Id;
    }

    public static String getNameByCode(Integer code) {
        for (CaseSortByEnum status : CaseSortByEnum.values()){
            if (status.toCode().equals(code)){
                return status.toString();
            }
        }
        return null;
    }
}
