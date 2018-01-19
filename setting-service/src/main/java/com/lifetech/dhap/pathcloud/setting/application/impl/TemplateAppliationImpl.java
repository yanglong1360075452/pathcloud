package com.lifetech.dhap.pathcloud.setting.application.impl;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.setting.application.TemplateApplication;
import com.lifetech.dhap.pathcloud.setting.application.dto.TemplateDto;
import com.lifetech.dhap.pathcloud.setting.domain.TemplateRepository;
import com.lifetech.dhap.pathcloud.setting.domain.TemplateSettingRepository;
import com.lifetech.dhap.pathcloud.setting.domain.model.Template;
import com.lifetech.dhap.pathcloud.setting.domain.model.TemplateSetting;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2016/12/7.
 */
@Service("templateApplication")
public class TemplateAppliationImpl implements TemplateApplication {

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private TemplateSettingRepository templateSettingRepository;

    @Override
    public List<TemplateDto> getTemplates(Integer parent, String position) throws BuzException {
        List<Template> templates = templateRepository.selectByParent(parent, position);
        return templateToDto(templates);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TemplateDto createTemplate(TemplateDto templateDto) throws BuzException {
        if (templateDto != null) {
            Template template = new Template();
            BeanUtils.copyProperties(templateDto, template);
            if (templateRepository.insert(template) == 0) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            Integer id = templateRepository.last();
            template = templateRepository.selectByPrimaryKey(id);
            BeanUtils.copyProperties(template, templateDto);
        }
        return templateDto;
    }

    @Override
    public TemplateDto getTemplateByName(String name, Integer parent, String position,Integer category,Integer level) throws BuzException {
        Template template = templateRepository.selectTemplateByName(name, parent, position, category,level);
        return templateToDto(template);
    }


    @Override
    public TemplateDto getTemplateById(Integer id) throws BuzException {
        Template template = templateRepository.selectByPrimaryKey(id);
        return templateToDto(template);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTemplate(Integer id) throws BuzException {
        templateRepository.deleteByPrimaryKey(id);
        templateRepository.deleteByParent(id);
        templateSettingRepository.deleteByTemplateId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void templateSetting(Integer id, Long userId, Integer position) {
        TemplateSetting template = templateSettingRepository.selectByPrimaryKey(id, userId, position);
        if (template == null) {
            template = new TemplateSetting();
            template.setOperatorId(userId);
            template.setTemplateId(id);
            template.setPosition(position);
            templateSettingRepository.insert(template);

            List<TemplateSetting> templateSettings = templateSettingRepository.selectByOperatorId(userId, position);
            if (templateSettings.size() > 10) {
                templateSettingRepository.deleteByPrimaryKey(templateSettings.get(10).getTemplateId(), templateSettings.get(10).getOperatorId());
            }
        } else {
            templateSettingRepository.deleteByPrimaryKey(template.getTemplateId(), template.getOperatorId());

            template = new TemplateSetting();
            template.setOperatorId(userId);
            template.setTemplateId(id);
            template.setPosition(position);
            templateSettingRepository.insert(template);
        }
    }

    @Override
    public List<TemplateDto> getOperatorUsedTemplate(Long operator, Integer position) {
        List<Template> templates = templateRepository.selectOperatorUsedTemplate(operator, position);
        return templateToDto(templates);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editTemplate(TemplateDto templateDto) {
        Template template = new Template();
        BeanUtils.copyProperties(templateDto, template);
        if (templateRepository.editTemplate(template) == 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void renameCategory(TemplateDto templateDto) throws BuzException {
        Template template = new Template();
        BeanUtils.copyProperties(templateDto, template);
        if (templateRepository.renameCategory(template) == 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

    }

    @Override
    public List<TemplateDto> getCategory(String position, Integer parentId) throws BuzException {
        if (position == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<Template> templateList = templateRepository.getCategoryByPositionAndParent(position, parentId);
        return templateToDto(templateList);
    }

    private TemplateDto templateToDto(Template template) {
        TemplateDto templateDto = null;
        if (template != null) {
            templateDto = new TemplateDto();
            BeanUtils.copyProperties(template, templateDto);
        }
        return templateDto;
    }

    private List<TemplateDto> templateToDto(List<Template> templates) {
        List<TemplateDto> templateDtoList = null;
        if (CollectionUtils.isNotEmpty(templates)) {
            templateDtoList = new ArrayList<>();
            for (Template template : templates) {
                templateDtoList.add(templateToDto(template));
            }
        }
        return templateDtoList;
    }
}
