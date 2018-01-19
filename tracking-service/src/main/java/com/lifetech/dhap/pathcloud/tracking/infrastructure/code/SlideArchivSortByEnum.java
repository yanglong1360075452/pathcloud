package com.lifetech.dhap.pathcloud.tracking.infrastructure.code;

/**
 * Created by HP on 2017/8/7.
 */
public enum SlideArchivSortByEnum {

    Id(0,"blockArchiveId"),
    Serial_number(1,"serialNumber"),
    Block_subId(2,"blockSubId"),
    Slide_subId(3,"slideSubId"),
    Ihc_marker(4,"ihcMarker"),
    Patient_name(5,"patientName"),
    Drying_box(6,"dryingBox"),
    Drying_createBy(7,"dryingCreateBy"),
    Drying_createTime(8,"dryingCreateTime"),
    Archive_box(9,"archiveBox"),
    Archive_createBy(10,"archiveCreateBy"),
    Archive_createTime(11," archiveCreateTime"),
    Status(12,"status"),
    ;

    private Integer nCode;

    private String name;


    SlideArchivSortByEnum(Integer _nCode, String _name) {
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

    public static SlideArchivSortByEnum valueOf(Integer code) {
        for (SlideArchivSortByEnum sortByEnum : SlideArchivSortByEnum.values()){
            if (sortByEnum.toCode().equals(code)){
                return sortByEnum;
            }
        }
        return SlideArchivSortByEnum.Id;
    }

    public static String getNameByCode(Integer code) {
        for (SlideArchivSortByEnum status : SlideArchivSortByEnum.values()){
            if (status.toCode().equals(code)){
                return status.toString();
            }
        }
        return null;
    }
}
