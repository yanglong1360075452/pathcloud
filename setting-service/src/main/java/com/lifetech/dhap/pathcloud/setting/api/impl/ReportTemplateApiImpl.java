package com.lifetech.dhap.pathcloud.setting.api.impl;

import com.google.gson.Gson;
import com.lifetech.dhap.pathcloud.common.data.TemplatePosition;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.setting.api.ReportTemplateApi;
import com.lifetech.dhap.pathcloud.setting.api.vo.ReportTemplateVO;
import com.lifetech.dhap.pathcloud.setting.api.vo.TemplateVO;
import com.lifetech.dhap.pathcloud.setting.application.TemplateApplication;
import com.lifetech.dhap.pathcloud.setting.application.dto.TemplateDto;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.TemplateCategory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2017/1/12.
 */
@Component("reportTemplateApi")
public class ReportTemplateApiImpl implements ReportTemplateApi {

    @Autowired
    private TemplateApplication templateApplication;

    @Override
    public ResponseVO createTemplate(TemplateVO templateVO) throws BuzException {
        
        templateVO.setPosition(TemplatePosition.ReportPosition.toCode().toString());
        templateVO.setCategory(1);
        templateVO.setDisplayOrder(1);
        templateVO.setCreateBy(UserContext.getLoginUserID());
        templateVO.setName(TemplatePosition.ReportPosition.name());

        Integer level = templateVO.getLevel();
        String position = templateVO.getPosition();
        if(level == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        ReportTemplateVO reportTemplateVO = templateVO.getReportTemplateVO();
        if (reportTemplateVO == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);

        }
        Gson gson = new Gson();
        String s = gson.toJson(reportTemplateVO);
        templateVO.setContent(s);

        String name = templateVO.getName();
        Integer parent = templateVO.getParent();
        if (parent == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (StringUtils.isBlank(name)){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (templateApplication.getTemplateByName(name,parent,position,TemplateCategory.Template.toCode(),level) != null){
            throw new BuzException(BuzExceptionCode.TemplateNameExists);
        }
        TemplateDto templateDto = new TemplateDto();
        BeanUtils.copyProperties(templateVO,templateDto);
        TemplateDto template = templateApplication.createTemplate(templateDto);

        return new ResponseVO(template);
    }

    @Override
    public ResponseVO getTemplates(Integer parent, String position) throws BuzException {
        if (parent==null || parent != 0){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (position==null || !position.equals(TemplatePosition.ReportPosition.toCode().toString())){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<TemplateDto> templates = templateApplication.getTemplates(parent,position);
        TemplateVO templateVO;
        List<TemplateVO> templateVOs = new ArrayList<>();
        for (TemplateDto templateDto : templates){
            templateVO = new TemplateVO();
            BeanUtils.copyProperties(templateDto,templateVO);
            String content = templateVO.getContent();
            Gson gson = new Gson();
            ReportTemplateVO reportTemplateVO = gson.fromJson(content,ReportTemplateVO.class);

            templateVO.setReportTemplateVO(reportTemplateVO);
            templateVOs.add(templateVO);
        }
        return new ResponseVO(templateVOs);
    }

    @Override
    public ResponseVO renameTemplate(Integer id, TemplateVO templateVO) throws BuzException {
        String name = templateVO.getName();
        if (id ==null || id<=0 || StringUtils.isBlank(name)){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        TemplateDto templateDto = templateApplication.getTemplateById(id);
        Integer parent = templateDto.getParent();
        String position = templateDto.getPosition();
        Integer level = templateDto.getLevel();
        if(templateApplication.getTemplateByName(name,parent,position, TemplateCategory.Template.toCode(),level) != null){
            throw new BuzException(BuzExceptionCode.TemplateNameExists);
        }
        templateDto.setName(name);
        templateDto.setUpdateBy(UserContext.getLoginUserID());
        templateApplication.renameCategory(templateDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO deleteTemplate(Integer id) throws BuzException {
        if (id==null || id <=0){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        templateApplication.deleteTemplate(id);
        return new ResponseVO();

    }

    @Override
    public ResponseVO editTemplate(Integer id, TemplateVO templateVO) throws BuzException {
        ReportTemplateVO reportTemplateVO = templateVO.getReportTemplateVO();
        Gson gson = new Gson();
        String s = gson.toJson(reportTemplateVO);
        templateVO.setContent(s);
        String content = templateVO.getContent();
        if (StringUtils.isBlank(content)){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        TemplateDto templateDto = templateApplication.getTemplateById(id);
        templateDto.setContent(content);
        templateDto.setUpdateBy(UserContext.getLoginUserID());
        templateApplication.editTemplate(templateDto);
        return new ResponseVO();
    }

}
