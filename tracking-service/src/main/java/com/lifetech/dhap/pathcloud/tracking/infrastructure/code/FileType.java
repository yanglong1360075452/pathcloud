package com.lifetech.dhap.pathcloud.tracking.infrastructure.code;

/**
 * Created by LuoMo on 2016-11-30.
 */
public enum FileType {

    Image(1,"image"),
    Video(2,"video"),
    Audio(3,"audio"),
    PDF(4,"pdf"),
    ScanImage(5,"scanImage"),
    ;

    private Integer nCode;

    private String name;


    FileType(Integer _nCode, String _name) {
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

    public static FileType valueOf(Integer code) {
        for (FileType sortByEnum : FileType.values()){
            if (sortByEnum.toCode().equals(code)){
                return sortByEnum;
            }
        }
        return FileType.Image;
    }
}
