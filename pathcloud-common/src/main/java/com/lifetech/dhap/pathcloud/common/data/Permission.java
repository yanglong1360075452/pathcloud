package com.lifetech.dhap.pathcloud.common.data;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-11-08-11:13
 */
public enum Permission {
    Admin(0, "系统管理"),
    Apply(1, "病理申请"),
    FirstDiagnose(2, "一级诊断"),
    Grossing(4, "取材"),
    Dehydrate(8, "脱水"),
    Embedding(16, "包埋"),
    Slice(32, "切片"),
    Dye(64, "染色"),
    SlideMake(128, "制片确认"),
    Registration(256, "登记"),
    Archive(512, "存档"),
    InfoQuery(1024, "信息查询"),
    Report(2048, "报告"),
    SecondDiagnose(4096, "二级诊断"),
    ThirdDiagnose(8192, "三级诊断"),
    Statistic(16384, "'统计报表'"),
    SpecialDye(32768, "'特染'"),
    IHC(65536, "'免疫组化'"),
    Frozen(131072, "'冰冻取材'"),
    Reagent(262144, "'试剂耗材'"),
    ;

    private Integer nCode;

    private String name;


    Permission(Integer _nCode, String _name) {
        this.nCode = _nCode;
        this.name = _name;
    }

    public Integer toCode() {
        return this.nCode;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static Permission valueOf(Integer code) {
        for (Permission permission : Permission.values()) {
            if (permission.toCode().equals(code)) {
                return permission;
            }
        }
        return Permission.Admin;
    }
}
