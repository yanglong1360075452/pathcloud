package com.lifetech.dhap.pathcloud.setting.application;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.setting.application.dto.TemplateDto;

import java.util.List;

/**
 * Created by HP on 2016/12/7.
 */
public interface TemplateApplication {
    /**
     * 根据父id查询模板
     * @param parent
     * @return
     * @throws BuzException
     */
    List<TemplateDto> getTemplates(Integer parent,String position)throws BuzException;

    /**
     * 创建模板
     * @param templateDto
     * @return
     * @throws BuzException
     */
    TemplateDto createTemplate(TemplateDto templateDto)throws BuzException;

    /**
     * 查询模板
     * @param name
     * @return
     * @throws BuzException
     */
    TemplateDto getTemplateByName(String name,Integer parent,String position,Integer category,Integer level)throws  BuzException;

    /**
     * 根据模板id查询模板
     * @param id
     * @return
     * @throws BuzException
     */
    TemplateDto getTemplateById(Integer id)throws BuzException;


    /**
     * 删除模板
     * @param id
     * @return
     * @throws BuzException
     */
    void deleteTemplate(Integer id)throws  BuzException;

    /**
     * 模板设置为常用模板
     * @param id
     * @param userId
     */
    void templateSetting(Integer id, Long userId,Integer position);

    /**
     * 获取用户常用模板
     * @param operator
     * @return
     */
    List<TemplateDto> getOperatorUsedTemplate(Long operator,Integer position);


    /**
     * 编辑模板
     * @param templateDto
     * @return
     */
    void editTemplate(TemplateDto templateDto);

    /**
     * 重命名模板分类
     * @param templateDto
     * @return
     * @throws BuzException
     */
    void renameCategory(TemplateDto templateDto)throws  BuzException;

    /**
     * 根据位置或parent获取分类
     * @param position
     * @param parentId
     * @return
     */
    List<TemplateDto> getCategory(String position,Integer parentId) throws BuzException;
}
