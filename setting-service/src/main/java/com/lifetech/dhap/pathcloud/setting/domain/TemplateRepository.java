package com.lifetech.dhap.pathcloud.setting.domain;

import com.lifetech.dhap.pathcloud.setting.domain.model.Template;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TemplateRepository {

    int deleteByPrimaryKey(Integer id);

    int deleteByParent(Integer id);

    int insert(Template record);

    Template selectByPrimaryKey(Integer id);

    List<Template> selectAll();

    int updateByPrimaryKey(Template record);

    List<Template> selectByParent(@Param("parent") Integer parent,@Param("position") String position);

    Integer last();

    /**
     * 通过模板名查询template
     *
     * */
    Template selectTemplateByName(@Param("name") String name, @Param("parent") Integer parent,
                                  @Param("position") String position,@Param("category") Integer category,@Param("level") Integer level);


    List<Template> getCategoryByPositionAndParent(@Param("position") String position, @Param("parent") Integer parent);

    int renameCategory(Template template);


    List<Template> selectOperatorUsedTemplate(@Param("operator") Long operator,@Param("position") Integer position);

    int editTemplate(Template template);


}