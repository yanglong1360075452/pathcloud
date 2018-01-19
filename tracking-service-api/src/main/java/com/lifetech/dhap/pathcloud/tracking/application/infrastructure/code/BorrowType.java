package com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code;

/**
 * Created by LiuMei on 2017-06-08.
 */
public enum  BorrowType {

    Patient(1,"病人"),
    Doctor(2,"医生"),
    Student(3,"学生"),
            ;

    private Integer nCode;

    private String name;


    BorrowType(Integer _nCode, String _name) {
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

    public static BorrowType valueOf(Integer code) {
        for (BorrowType borrowType : BorrowType.values()){
            if (borrowType.toCode().equals(code)){
                return borrowType;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (BorrowType borrowType : BorrowType.values()){
            if (borrowType.toCode().equals(code)){
                return borrowType.toString();
            }
        }
        return null;
    }

}
