package com.lifetech.dhap.pathcloud.tracking.infrastructure.code;

/**
 * Created by LuoMo on 2016-11-30.
 */
public enum SectionSortEnum {
    Default(0,"status"),
    SerialNo(1,"serialNumber"),
    BlockSubId(2,"subId"),
    SlideSubId(3,"slideId"),
    patientName(4,"patientName"),
    Departments(5,"departments"),
    GrossingDoctor(6,"grossingDoctorId"),
    BodyPart(7,"bodyPart"),
    Count(8,"count"),
    Biaoshi(9,"biaoshi"),
    SectionOperator(10,"operationDoctorId"),
    SectionTime(11,"operateTime"),
    Note(12,"note"),
    Status(13,"status")
    ;

    private Integer nCode;

    private String name;


    SectionSortEnum(Integer _nCode, String _name) {
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

    public static SectionSortEnum valueOf(Integer code) {
        for (SectionSortEnum sortByEnum : SectionSortEnum.values()){
            if (sortByEnum.toCode().equals(code)){
                return sortByEnum;
            }
        }
        return SectionSortEnum.Default;
    }
}
