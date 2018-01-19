package com.lifetech.dhap.pathcloud.setting.api.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lifetech.dhap.pathcloud.common.data.TemplatePosition;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.setting.api.TemplateApi;
import com.lifetech.dhap.pathcloud.setting.api.vo.DiagnoseTemplateContentVO;
import com.lifetech.dhap.pathcloud.setting.api.vo.TemplateVO;
import com.lifetech.dhap.pathcloud.setting.application.TemplateApplication;
import com.lifetech.dhap.pathcloud.setting.application.dto.TemplateDto;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.TemplateCategory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2016/12/7.
 */
@Component("templateApi")
public class TemplateApiImpl implements TemplateApi {

    @Autowired
    private TemplateApplication templateApplication;

    @Override
    public ResponseVO getTemplates(Integer parent, String position, Integer category) throws BuzException {
        if (parent == null && category == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<TemplateDto> templates = null;
        if (category == null || category.equals(TemplateCategory.Template.toCode())) {
            templates = templateApplication.getTemplates(parent, position);
        } else if (category.equals(TemplateCategory.Classify.toCode())) {
            templates = templateApplication.getCategory(position, parent);
        }
        TemplateVO templateVO;
        List<TemplateVO> templateVOs = new ArrayList<>();
        ;
        if (CollectionUtils.isNotEmpty(templates)) {
            for (TemplateDto templateDto : templates) {
                if (templateDto == null || StringUtils.isBlank(templateDto.getName())) {
                    throw new BuzException(BuzExceptionCode.ErrorParam);
                }
                templateVO = new TemplateVO();
                BeanUtils.copyProperties(templateDto, templateVO);
                templateVOs.add(templateVO);
            }
        }
        return new ResponseVO(templateVOs);
    }

    @Override
    public ResponseVO createTemplate(TemplateVO templateVO) throws BuzException {
        Integer category = templateVO.getCategory();
        if (category == null || category.equals(TemplateCategory.Template.toCode())) {
            category = TemplateCategory.Template.toCode();
        } else {
            category = TemplateCategory.Classify.toCode();
        }
        templateVO.setCategory(category);
        templateVO.setDisplayOrder(1);
        templateVO.setCreateBy(UserContext.getLoginUserID());
        String name = templateVO.getName();
        Integer parent = templateVO.getParent();
        Integer level = templateVO.getLevel();
        String position = templateVO.getPosition();
        if (position == null || level == null || StringUtils.isBlank(name)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if(parent != null){
            TemplateDto template = templateApplication.getTemplateById(parent);
            if(template == null){
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
        }
        if (templateApplication.getTemplateByName(name, parent, position, category,level) != null) {
            throw new BuzException(BuzExceptionCode.TemplateNameExists);
        }
        TemplateDto templateDto = new TemplateDto();
        BeanUtils.copyProperties(templateVO, templateDto);
        templateDto = templateApplication.createTemplate(templateDto);
        return new ResponseVO(templateDto);
    }

    @Override
    public ResponseVO renameTemplate(Integer id, TemplateVO templateVO) throws BuzException {
        String name = templateVO.getName();
        if (id == null || id <= 0 || StringUtils.isBlank(name)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        TemplateDto templateDto = templateApplication.getTemplateById(id);
        Integer parent = templateDto.getParent();
        String position = templateDto.getPosition();
        Integer category = templateDto.getCategory();
        Integer level = templateDto.getLevel();
        if (templateApplication.getTemplateByName(name, parent, position, category,level) != null) {
            throw new BuzException(BuzExceptionCode.TemplateNameExists);
        }
        templateDto.setName(name);
        templateDto.setUpdateBy(UserContext.getLoginUserID());
        templateApplication.renameCategory(templateDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO editTemplate(Integer id, TemplateVO templateVO) throws BuzException {
        String content = templateVO.getContent();
        List<String> marks = templateVO.getMarks();
        if (StringUtils.isBlank(content) && CollectionUtils.isEmpty(marks)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        TemplateDto templateDto = templateApplication.getTemplateById(id);
        if (CollectionUtils.isNotEmpty(marks)) {
            Gson gson = new Gson();
            templateDto.setContent(gson.toJson(marks));
        } else {
            templateDto.setContent(content);
        }
        templateDto.setUpdateBy(UserContext.getLoginUserID());
        templateApplication.editTemplate(templateDto);
        return new ResponseVO();
    }


    @Override
    public ResponseVO deleteTemplate(Integer id) throws BuzException {
        templateApplication.deleteTemplate(id);
        return new ResponseVO();
    }

    @Override
    public ResponseVO templateSetting(Integer id, Integer position) throws BuzException {
        if (position == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        templateApplication.templateSetting(id, UserContext.getLoginUserID(), position);
        return new ResponseVO();
    }

    @Override
    public ResponseVO used(Integer position) throws BuzException {
        List<TemplateDto> data = templateApplication.getOperatorUsedTemplate(UserContext.getLoginUserID(), position);
        List<TemplateVO> templateVOs = new ArrayList<>();
        TemplateVO templateVO;
        Gson gson;
        if (CollectionUtils.isNotEmpty(data)) {
            for (TemplateDto templateDto : data) {
                templateVO = new TemplateVO();
                BeanUtils.copyProperties(templateDto, templateVO);
                if (templateVO.getPosition().equals(TemplatePosition.DiagnosePosition.toCode())) {
                    String content = templateVO.getContent();
                    gson = new Gson();
                    List<DiagnoseTemplateContentVO> list = gson.fromJson(content, new TypeToken<List<DiagnoseTemplateContentVO>>() {
                    }.getType());
                    templateVO.setTemplateContentVO(list);
                }
                templateVOs.add(templateVO);
            }
        }
        return new ResponseVO(templateVOs);
    }
}
