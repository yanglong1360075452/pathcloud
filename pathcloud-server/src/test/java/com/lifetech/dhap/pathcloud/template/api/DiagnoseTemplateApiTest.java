package com.lifetech.dhap.pathcloud.template.api;

import com.lifetech.dhap.pathcloud.common.BaseTest;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.setting.api.DiagnoseTemplateApi;
import com.lifetech.dhap.pathcloud.setting.api.vo.DiagnoseTemplateContentVO;
import com.lifetech.dhap.pathcloud.setting.api.vo.ProjectContentVO;
import com.lifetech.dhap.pathcloud.setting.api.vo.TemplateVO;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2017/1/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-*.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional(rollbackFor = Exception.class)
@Ignore
public class DiagnoseTemplateApiTest extends BaseTest{

    @Autowired
    private DiagnoseTemplateApi diagnoseTemplateApi;

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
    public void createTemplateTest(){
        TemplateVO templateVO = new TemplateVO();
        templateVO.setName("泌尿模板一");
        templateVO.setParent(1);
        templateVO.setLevel(1);
        List<DiagnoseTemplateContentVO> list = new ArrayList<>();
        DiagnoseTemplateContentVO dtc = new DiagnoseTemplateContentVO();
        dtc.setProjectContentCheck(true);
        dtc.setOther("qwe");
        dtc.setProjectName("送检科室");
        dtc.setProjectNameCheck(true);
        List<ProjectContentVO> list1 = new ArrayList<>();
        ProjectContentVO projectContentVO = new ProjectContentVO();
        projectContentVO.setName("肾");
        projectContentVO.setCheck(true);
        list1.add(projectContentVO);
        ProjectContentVO projectContentV1 = new ProjectContentVO();
        projectContentV1.setName("胃");
        projectContentV1.setCheck(true);
        list1.add(projectContentV1);
        dtc.setProjectContentVO(list1);
        list.add(dtc);
        templateVO.setTemplateContentVO(list);

        ResponseVO template = diagnoseTemplateApi.createTemplate(templateVO);
        TemplateDto data = (TemplateDto) template.getData();
        assert (data.getName().equals("泌尿模板一"));
        assert (data.getCategory() == 1);
        assert (data.getParent() == 1);
        assert (data.getPosition().equals("2"));
        assert (data.getContent() != null);

    }

    @Test
    public void getTemplatesTest(){

        TemplateVO templateVO = new TemplateVO();
        templateVO.setName("泌尿模板一");
        templateVO.setParent(1);
        templateVO.setLevel(1);
        List<DiagnoseTemplateContentVO> list = new ArrayList<>();
        DiagnoseTemplateContentVO dtc = new DiagnoseTemplateContentVO();
        dtc.setProjectContentCheck(true);
        dtc.setOther("qwe");
        dtc.setProjectName("送检科室");
        dtc.setProjectNameCheck(true);
        List<ProjectContentVO> list1 = new ArrayList<>();
        ProjectContentVO projectContentVO = new ProjectContentVO();
        projectContentVO.setName("肾");
        projectContentVO.setCheck(true);
        list1.add(projectContentVO);
        ProjectContentVO projectContentV1 = new ProjectContentVO();
        projectContentV1.setName("胃");
        projectContentV1.setCheck(true);
        list1.add(projectContentV1);
        dtc.setProjectContentVO(list1);
        list.add(dtc);
        templateVO.setTemplateContentVO(list);

        ResponseVO template = diagnoseTemplateApi.createTemplate(templateVO);
        TemplateDto data = (TemplateDto) template.getData();


        ResponseVO templates = diagnoseTemplateApi.getTemplates(data.getParent(), data.getPosition());
        List<TemplateVO> data1 = (List<TemplateVO>) templates.getData();

        for (TemplateVO template1:data1){
            if (template1.getName().equals("泌尿模板一")){
                assert (template1.getContent() != null);
                assert (template1.getParent()==1);
                assert (template1.getCategory()==1);
                assert (template1.getPosition().equals("2"));
                assert (template1.getTemplateContentVO().size() >= 0);
                List<DiagnoseTemplateContentVO> dtc1= template1.getTemplateContentVO();
                for (DiagnoseTemplateContentVO diagnoseTemplateContentVO:dtc1) {
                    if (diagnoseTemplateContentVO.getProjectName().equals("送检科室")) {
                        assert (diagnoseTemplateContentVO.getProjectNameCheck());
                        assert (diagnoseTemplateContentVO.getProjectName().equals("送检科室"));
                        assert (diagnoseTemplateContentVO.getOther().equals("qwe"));
                        assert (diagnoseTemplateContentVO.getProjectContentVO().size() >= 0);

                    List<ProjectContentVO> projectContentVO1 = diagnoseTemplateContentVO.getProjectContentVO();
                    for (ProjectContentVO projectContentVO2 : projectContentVO1) {

                        if (projectContentVO2.getName().equals("肾")){
                            assert (projectContentVO2.getName().equals("肾"));
                            assert (projectContentVO2.getCheck());
                        }
                    }
                }
                }
            }
        }

    }
    
    
    @Test
    public void renameTemplateTest(){

        TemplateVO templateVO = new TemplateVO();
        templateVO.setName("泌尿模板一");
        templateVO.setParent(1);
        templateVO.setLevel(1);
        List<DiagnoseTemplateContentVO> list = new ArrayList<>();
        DiagnoseTemplateContentVO dtc = new DiagnoseTemplateContentVO();
        dtc.setProjectContentCheck(true);
        dtc.setOther("qwe");
        dtc.setProjectName("送检科室");
        dtc.setProjectNameCheck(true);
        List<ProjectContentVO> list1 = new ArrayList<>();
        ProjectContentVO projectContentVO = new ProjectContentVO();
        projectContentVO.setName("肾");
        projectContentVO.setCheck(true);
        list1.add(projectContentVO);
        ProjectContentVO projectContentV1 = new ProjectContentVO();
        projectContentV1.setName("胃");
        projectContentV1.setCheck(true);
        list1.add(projectContentV1);
        dtc.setProjectContentVO(list1);
        list.add(dtc);
        templateVO.setTemplateContentVO(list);

        ResponseVO template = diagnoseTemplateApi.createTemplate(templateVO);
        TemplateDto data = (TemplateDto) template.getData();
        TemplateVO templateVO1 = new TemplateVO();
        templateVO1.setName("泌尿模板二");
        ResponseVO responseVO = diagnoseTemplateApi.renameTemplate(data.getId(), templateVO1);
        ResponseVO templates = diagnoseTemplateApi.getTemplates(data.getParent(), data.getPosition());
        List<TemplateVO> data1 = (List<TemplateVO>) templates.getData();

        for (TemplateVO template1:data1){
            if (template1.getName().equals("泌尿模板二")){
                assert (template1.getName().equals("泌尿模板二"));
                assert (template1.getPosition().equals("2"));
                assert (template1.getContent() != null);
                assert (template1.getCategory() == 1);
                assert (template1.getTemplateContentVO().size() >= 0);
            }
        }

    }

}
