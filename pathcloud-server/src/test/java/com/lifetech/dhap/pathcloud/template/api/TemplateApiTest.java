package com.lifetech.dhap.pathcloud.template.api;

import com.lifetech.dhap.pathcloud.common.BaseTest;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.setting.api.TemplateApi;
import com.lifetech.dhap.pathcloud.setting.api.vo.TemplateVO;
import com.lifetech.dhap.pathcloud.setting.application.TemplateApplication;
import com.lifetech.dhap.pathcloud.setting.application.dto.TemplateDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by HP on 2016/12/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-*.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional(rollbackFor = Exception.class)
@Ignore
public class TemplateApiTest extends BaseTest{

    @Autowired
    private TemplateApplication templateApplication;

    @Autowired
    private TemplateApi templateApi;

    @Before
    public void setup() throws Exception {
        SecurityContext context = new SecurityContextImpl();
        context.setAuthentication(this);
        SecurityContextHolder.setContext(context);
    }

    @After
    public void destroy() throws Exception {
    }
    
    @Test
    public void getTemplatesTest(){
        TemplateVO templateVO = new TemplateVO();
        templateVO.setParent(1);
        templateVO.setContent("世界");
        templateVO.setLevel(1);
        templateVO.setName("你秒模板四");
        templateVO.setPosition("1");

        ResponseVO template = templateApi.createTemplate(templateVO);
        TemplateDto data = (TemplateDto) template.getData();

        ResponseVO templates = templateApi.getTemplates(data.getParent(),"1",null);
        List<TemplateVO> data1 = (List<TemplateVO>) templates.getData();
        for (TemplateVO templateVO1 : data1){
            if (templateVO1.getId().equals(data.getId())){
                assert (templateVO1.getContent().equals("世界"));
                assert (templateVO1.getName().equals(templateVO.getName()));
                assert (templateVO1.getPosition().equals(templateVO.getPosition()));
                assert (templateVO1.getLevel().equals(templateVO.getLevel()));

            }

        }
    }

    @Test
    public void createTemplateTest(){
        TemplateVO templateVO = new TemplateVO();
        templateVO.setParent(1);
        templateVO.setContent("世界");
        templateVO.setLevel(1);
        templateVO.setName("你秒模板四");
        templateVO.setPosition("1");

        ResponseVO template = templateApi.createTemplate(templateVO);
        TemplateDto data = (TemplateDto) template.getData();
        assert (data.getName().equals(templateVO.getName()));
        assert (data.getContent().equals(templateVO.getContent()));
        assert (data.getPosition().equals(templateVO.getPosition()));
        assert (data.getLevel().equals(templateVO.getLevel()));

    }

    @Test
    public void renameTemplateTest(){
        TemplateVO templateVO = new TemplateVO();
        templateVO.setParent(1);
        templateVO.setContent("dadadasd");
        templateVO.setLevel(1);
        templateVO.setName("你秒模板四");
        templateVO.setPosition("1");

        ResponseVO template = templateApi.createTemplate(templateVO);
        TemplateDto data = (TemplateDto) template.getData();

        Integer id = data.getId();

        TemplateVO templateVO1 = new TemplateVO();
        templateVO1.setName("你秒模板五");
        templateVO1.setPosition(data.getPosition());
        templateApi.renameTemplate(id, templateVO1);

        ResponseVO templates = templateApi.getTemplates(1,"1",null);
        List<TemplateVO> data1 = (List<TemplateVO>) templates.getData();
        for (TemplateVO templateVO2 : data1){
            if (templateVO2.getId().equals(id)){
                assert (templateVO2.getName().equals(templateVO1.getName()));
            }
        }


    }

    @Test
    public void editTemplateTest(){
        TemplateVO templateVO = new TemplateVO();
        templateVO.setParent(1);
        templateVO.setContent("dadadasd");
        templateVO.setLevel(1);
        templateVO.setName("你秒模板四");
        templateVO.setPosition("1");

        ResponseVO template = templateApi.createTemplate(templateVO);
        TemplateDto data = (TemplateDto) template.getData();

        Integer id = data.getId();

        TemplateVO templateVO1 = new TemplateVO();
        templateVO1.setContent("模板内容");
        templateApi.editTemplate(id,templateVO1);

        ResponseVO templates = templateApi.getTemplates(1,"1",null);
        List<TemplateVO> data1 = (List<TemplateVO>) templates.getData();
        for (TemplateVO templateVO2 : data1){
            if (templateVO2.getId().equals(id)){
                assert (templateVO2.getContent().equals(templateVO1.getContent()));
                assert (templateVO2.getPosition().equals(templateVO.getPosition()));
            }

        }


    }



}
