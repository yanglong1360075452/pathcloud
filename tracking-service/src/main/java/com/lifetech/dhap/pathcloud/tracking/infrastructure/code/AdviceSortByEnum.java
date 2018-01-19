package com.lifetech.dhap.pathcloud.tracking.infrastructure.code;

/**
 * Created by HP on 2017/7/10.
 */
public enum AdviceSortByEnum {

    Id(0,"p.id"),
    Serial_number(1,"p.serialNumber"),
    sub_id(2,"p.blockSubId"),
    Patient_name(3,"p.patientName"),
    Operation(4,"p.operation"),
    //Operator_id(5,"p.operatorId"),
    Operation_time(6,"p.operationTime"),
    Status(7,"p.status"),
    ;

    private Integer nCode;

    private String name;


    AdviceSortByEnum(Integer _nCode, String _name) {
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

    public static AdviceSortByEnum valueOf(Integer code) {
        for (AdviceSortByEnum sortByEnum : AdviceSortByEnum.values()){
            if (sortByEnum.toCode().equals(code)){
                return sortByEnum;
            }
        }
        return AdviceSortByEnum.Id;
    }

    public static String getNameByCode(Integer code) {
        for (AdviceSortByEnum status : AdviceSortByEnum.values()){
            if (status.toCode().equals(code)){
                return status.toString();
            }
        }
        return null;
    }
}
