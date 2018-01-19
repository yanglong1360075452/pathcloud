package com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code;

/**
 * @author LiuMei
 * @date 2017-11-08.
 */
public enum FrozenCoincidence {

    Coincidence(1,"符合"),
    IncompleteCoincidence(2,"不完全符合"),
    Inconformity(3,"不符合"),
    ;

    private Integer nCode;

    private String name;


    FrozenCoincidence(Integer _nCode, String _name) {
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

    public static FrozenCoincidence valueOf(Integer code) {
        for (FrozenCoincidence frozenCoincidence : FrozenCoincidence.values()){
            if (frozenCoincidence.toCode().equals(code)){
                return frozenCoincidence;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (FrozenCoincidence frozenCoincidence : FrozenCoincidence.values()){
            if (frozenCoincidence.toCode().equals(code)){
                return frozenCoincidence.toString();
            }
        }
        return null;
    }
    
}
