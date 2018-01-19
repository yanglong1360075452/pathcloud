package com.lifetech.dhap.pathcloud.application.infrastructure.code;

/**
 * Created by LuoMo on 2016-11-30.
 */
public enum QueryFieldType {

    any("0","p.diagnose,p.jujian_note,p.micro_diagnose"),
    serialNumber("1","p.serial_number"),
    hisId("2","a.his_id"),
    admissionNo("3","a.admission_no"),
    patientName("4","a.patient_name"),
    diagnoseNote("5","p.diagnose"),
    inspectionItem("6","a.inspection_item"),
    jujianNote("7","p.jujian_note"),
    mircoNote("8","p.micro_diagnose"),
    ;

    private String nCode;

    private String name;


    QueryFieldType(String _nCode, String _name) {
        this.nCode = _nCode;
        this.name = _name;
    }

    public String toCode(){
        return this.nCode;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static String getNameByCode(String code) {
        for (QueryFieldType status : QueryFieldType.values()){
            if (status.toCode().equals(code)){
                return status.toString();
            }
        }
        return null;
    }

}
