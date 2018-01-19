package com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code;

/**
 * Created by LiuMei on 2017-06-08.
 */
public enum BorrowStatus {


    Borrow(1,"借阅"),
    Back(2,"归还"),
    ;

    private Integer nCode;

    private String name;


    BorrowStatus(Integer _nCode, String _name) {
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

    public static BorrowStatus valueOf(Integer code) {
        for (BorrowStatus borrowStatus : BorrowStatus.values()){
            if (borrowStatus.toCode().equals(code)){
                return borrowStatus;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (BorrowStatus borrowStatus : BorrowStatus.values()){
            if (borrowStatus.toCode().equals(code)){
                return borrowStatus.toString();
            }
        }
        return null;
    }
    
}
