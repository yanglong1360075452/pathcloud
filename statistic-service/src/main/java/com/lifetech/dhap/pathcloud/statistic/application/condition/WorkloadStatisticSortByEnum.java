package com.lifetech.dhap.pathcloud.statistic.application.condition;

/**
 * Created by HP on 2017/8/7.
 */
public enum WorkloadStatisticSortByEnum {

    Id(0,"null"),
    User_name(1,"operatorId"),
    First_name(2,"operatorId"),
    Num(3,"num"),

    ;

    private Integer nCode;

    private String name;


    WorkloadStatisticSortByEnum(Integer _nCode, String _name) {
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

    public static WorkloadStatisticSortByEnum valueOf(Integer code) {
        for (WorkloadStatisticSortByEnum sortByEnum : WorkloadStatisticSortByEnum.values()){
            if (sortByEnum.toCode().equals(code)){
                return sortByEnum;
            }
        }
        return WorkloadStatisticSortByEnum.Id;
    }

    public static String getNameByCode(Integer code) {
        for (WorkloadStatisticSortByEnum status : WorkloadStatisticSortByEnum.values()){
            if (status.toCode().equals(code)){
                return status.toString();
            }
        }
        return null;
    }
}
