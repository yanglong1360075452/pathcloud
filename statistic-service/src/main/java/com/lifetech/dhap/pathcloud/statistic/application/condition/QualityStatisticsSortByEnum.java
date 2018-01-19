package com.lifetech.dhap.pathcloud.statistic.application.condition;



/**
 * Created by HP on 2017/8/7.
 */
public enum QualityStatisticsSortByEnum {
    Id(0,"null"),
    User_name(1,"operatorId"),
    First_name(2,"operatorId"),
    Operation_name(3,"operation"),
    Block_total(4,"blockTotal"),
    Re_grossing_total(5,"reGrossingTotal"),
    Re_grossing_ratio(6,"reGrossingTotal"),
    Re_section_total(7,"reSectionTotal"),
    Re_section_ratio(8,"reSectionTotal"),
    Low_score_total(9,"lowScoreTotal"),
    Low_score_ratio(10,"lowScoreTotal"),
    Score(11,"score"),

    ;

    private Integer nCode;

    private String name;


    QualityStatisticsSortByEnum(Integer _nCode, String _name) {
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

    public static QualityStatisticsSortByEnum valueOf(Integer code) {
        for (QualityStatisticsSortByEnum sortByEnum : QualityStatisticsSortByEnum.values()){
            if (sortByEnum.toCode().equals(code)){
                return sortByEnum;
            }
        }
        return QualityStatisticsSortByEnum.Id;
    }

    public static String getNameByCode(Integer code) {
        for (QualityStatisticsSortByEnum status : QualityStatisticsSortByEnum.values()){
            if (status.toCode().equals(code)){
                return status.toString();
            }
        }
        return null;
    }
}
