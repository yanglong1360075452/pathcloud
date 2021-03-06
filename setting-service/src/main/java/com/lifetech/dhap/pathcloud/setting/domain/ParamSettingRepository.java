package com.lifetech.dhap.pathcloud.setting.domain;

import com.lifetech.dhap.pathcloud.setting.domain.model.ParamSetting;
import java.util.List;

public interface ParamSettingRepository {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table param_setting
     *
     * @mbggenerated Tue Dec 06 13:12:36 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table param_setting
     *
     * @mbggenerated Tue Dec 06 13:12:36 CST 2016
     */
    int insert(ParamSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table param_setting
     *
     * @mbggenerated Tue Dec 06 13:12:36 CST 2016
     */
    ParamSetting selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table param_setting
     *
     * @mbggenerated Tue Dec 06 13:12:36 CST 2016
     */
    List<ParamSetting> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table param_setting
     *
     * @mbggenerated Tue Dec 06 13:12:36 CST 2016
     */
    int updateByPrimaryKey(ParamSetting record);

    String selectByKey(String key);

    int updateContentByKey(ParamSetting paramSetting);

    ParamSetting selectByKeyForUpdate(String key);
}