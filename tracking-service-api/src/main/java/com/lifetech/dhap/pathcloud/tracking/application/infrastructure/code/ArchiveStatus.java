package com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code;

/**
 * Created by LiuMei on 2017-06-07.
 */
public enum ArchiveStatus {
    drying(0,"晾片"),
    archive(1,"存档"),
    borrow(2,"借出"),
    ;

    private Integer nCode;

    private String name;


    ArchiveStatus(Integer _nCode, String _name) {
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

    public static ArchiveStatus valueOf(Integer code) {
        for (ArchiveStatus archiveStatus : ArchiveStatus.values()){
            if (archiveStatus.toCode().equals(code)){
                return archiveStatus;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (ArchiveStatus archiveStatus : ArchiveStatus.values()){
            if (archiveStatus.toCode().equals(code)){
                return archiveStatus.toString();
            }
        }
        return null;
    }
    
}
