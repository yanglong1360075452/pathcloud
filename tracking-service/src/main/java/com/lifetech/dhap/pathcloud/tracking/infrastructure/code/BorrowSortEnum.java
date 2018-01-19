package com.lifetech.dhap.pathcloud.tracking.infrastructure.code;

/**
 * Created by LuoMo on 2016-11-30.
 */
public enum BorrowSortEnum {
    Default(0,"id"),
    SerialNo(1,"serial_number"),
    BlockSubId(2,"blockSubId"),
    SlideSubId(3,"slideSubId"),
    Marker(4,"ihcMarker"),
    PatientName(5,"patientName"),
    No(6,"archiveBox"),
    Borrower(7,"borrowName"),
    Unit(8,"unit"),
    Phone(9,"borrowPhone"),
    BorrowTime(10,"createTime"),
    PlanBack(11,"planBack"),
    ActualBack(12,"realBack"),
    Delay(13,"overdue")
    ;

    private Integer nCode;

    private String name;


    BorrowSortEnum(Integer _nCode, String _name) {
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

    public static BorrowSortEnum valueOf(Integer code) {
        for (BorrowSortEnum sortByEnum : BorrowSortEnum.values()){
            if (sortByEnum.toCode().equals(code)){
                return sortByEnum;
            }
        }
        return BorrowSortEnum.Default;
    }
}
