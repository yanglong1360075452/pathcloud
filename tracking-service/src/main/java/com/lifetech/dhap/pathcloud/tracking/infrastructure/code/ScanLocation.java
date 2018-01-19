package com.lifetech.dhap.pathcloud.tracking.infrastructure.code;

/**
 * Created by LiuMei on 2017-07-26.
 */
public enum ScanLocation {

    Production(1,"production"),
    Archive(2,"archive")
    ;

    private Integer nCode;

    private String name;


    ScanLocation(Integer _nCode, String _name) {
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

    public static ScanLocation valueOf(Integer code) {
        for (ScanLocation scanLocation : ScanLocation.values()){
            if (scanLocation.toCode().equals(code)){
                return scanLocation;
            }
        }
        return ScanLocation.Production;
    }
    
}
