package com.lifetech.dhap.pathcloud.setting.domain;

import com.lifetech.dhap.pathcloud.setting.domain.model.TemplateSetting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TemplateSettingRepository {

    int deleteByPrimaryKey(@Param("templateId") Integer templateId, @Param("operatorId") Long operatorId);

    int deleteByTemplateId(Integer templateId);

    int insert(TemplateSetting record);

    TemplateSetting selectByPrimaryKey(@Param("templateId") Integer templateId, @Param("operatorId") Long operatorId,
                                       @Param("position")Integer position);

    List<TemplateSetting> selectAll();

    int updateByPrimaryKey(TemplateSetting record);

    List<TemplateSetting> selectByOperatorId(@Param("operatorId") Long operatorId,@Param("position") Integer position);
}