package com.lifetech.dhap.pathcloud.application.infrastructure.code;

public enum BookingFlag {
    zero(0,"00:00", "00:30"),
    zeroHalf(1,"00:30","01:00" ),
    one(2,"01:00","01:30"),
    oneHalf(3,"01:30", "02:00"),
    two(4, "02:00", "02:30"),
    twoHalf(5, "02:30", "03:00"),
    threeHalf(6, "03:00", "03:30"),
    three(7, "03:30", "04:00"),

    eight(16, "08:00", "08:30"),
    eightHalf(17, "08:30", "09:00"),
    nine(18, "09:00", "09:30"),
    nineHalf(19, "09:30", "10:00"),
    ten(20, "10:00", "10:30"),
    tenHalf(21, "10:30", "11:00"),
    eleven(22, "11:00", "11:30"),
    elevenHalf(23, "11:30", "12:00"),
    twelve(24, "12:00", "12:30"),
    twelveHalf(25, "12:30", "13:00"),
    thirteen(26, "13:00", "13:30"),
    thirteenHalf(27, "13:30", "14:00"),
    fourteen(28, "14:00", "14:30"),
    fourteenHalf(29, "14:30", "15:00"),
    fifteen(30, "15:00", "15:30"),
    fifteenHalf(31, "15:30", "16:00"),
    sixteen(32, "16:00", "16:30"),
    sixteenHalf(33, "16:30", "17:00"),
    seventeen(34, "17:00", "17:30"),
    seventeenHalf(35, "17:30", "18:00"),

    ;

    private Integer nCode;

    private String name;

    private String endTime;


    BookingFlag(Integer _nCode, String _name, String endTime) {
        this.nCode = _nCode;
        this.name = _name;
        this.endTime = endTime;
    }

    public Integer toCode(){
        return this.nCode;
    }

    public String toName() {
        return this.name;
    }

    public String toEndTime() {
        return this.endTime;
    }

    public static BookingFlag valueOf(Integer code) {
        for (BookingFlag applicationStatus : BookingFlag.values()){
            if (applicationStatus.toCode().equals(code)){
                return applicationStatus;
            }
        }
        return null;
    }

    public static String getStartTimeByCode(Integer code) {
        for (BookingFlag applicationStatus : BookingFlag.values()){
            if (applicationStatus.toCode().equals(code)){
                return applicationStatus.toName();
            }
        }
        return null;
    }

    public static String getEndTimeByCode(Integer code) {
        for (BookingFlag applicationStatus : BookingFlag.values()){
            if (applicationStatus.toCode().equals(code)){
                return applicationStatus.toEndTime();
            }
        }
        return null;
    }
}
