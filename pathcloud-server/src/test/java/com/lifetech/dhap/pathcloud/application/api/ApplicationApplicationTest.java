package com.lifetech.dhap.pathcloud.application.api;

import com.lifetech.dhap.pathcloud.application.application.ApplicationApplication;
import com.lifetech.dhap.pathcloud.application.application.condition.ApplicationCondition;
import com.lifetech.dhap.pathcloud.application.application.dto.ApplicationBriefDto;
import com.lifetech.dhap.pathcloud.application.application.dto.ApplicationDto;
import com.lifetech.dhap.pathcloud.application.application.dto.ApplicationSampleDto;
import com.lifetech.dhap.pathcloud.application.application.dto.SampleDto;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.ApplicationStatus;
import com.lifetech.dhap.pathcloud.common.BaseTest;
import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;
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
 * Created by LuoMo on 2016-11-23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext-*.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional(rollbackFor = Exception.class)
@Ignore
public class ApplicationApplicationTest extends BaseTest{

    @Autowired
    private ApplicationApplication applicationApplication;

    @Before
    public void setup() throws Exception {
        SecurityContext context = new SecurityContextImpl();
        context.setAuthentication(this);
        SecurityContextHolder.setContext(context);
    }

    @After
    public void destroy() throws Exception {
    }

    /**
     * 测试
     * 1.GetApplicationByCondition
     * 2.GetApplicationBriefByCondition
     * 3.GetApplicationTotalByCondition
     */
    @Test
    public void getApplicationByConditionTest(){
        ApplicationDto applicationDto = new ApplicationDto();
        Long time = System.currentTimeMillis();
        String patientName = "测试姓名-"+time;
        UserSimpleDto createBy = new UserSimpleDto();
        createBy.setId(1L);
        Integer status = ApplicationStatus.PrepareRegister.toCode();
        Integer sex = 0;
        String doctor = "送检医生";
        applicationDto.setPatientName(patientName);
        applicationDto.setCreateBy(1L);
        applicationDto.setStatus(status);
        applicationDto.setSex(sex);
        applicationDto.setDoctorTel("dasda s");
        applicationDto.setDoctor(doctor);
        List<SampleDto> sampleDtos = new ArrayList<>();
        SampleDto sampleDto = new SampleDto();
        String sampleName = "测试样本";
        Integer category = 1;
        sampleDto.setCreateBy(createBy);
        sampleDto.setName(sampleName);
        sampleDto.setCategory(category);
        sampleDtos.add(sampleDto);
        applicationDto.setSamples(sampleDtos);
        applicationApplication.createApplication(applicationDto);
        ApplicationCondition applicationCondition = new ApplicationCondition();
        applicationCondition.setCreateBy(1L);
        applicationCondition.setFilter(patientName);
        List<ApplicationDto> applicationDtos = applicationApplication.getApplicationByCondition(applicationCondition);
        Long total = applicationApplication.getApplicationTotalByCondition(applicationCondition);
        assert (total != null && total == 1);
        assert (applicationDtos != null && applicationDtos.size() == 1);
        applicationDto = applicationDtos.get(0);
        assert (applicationDto != null);
        assert (applicationDto.getStatus() == status);
        assert (applicationDto.getSex() == sex);
        assert (applicationDto.getPatientName().equals(patientName));
        assert (applicationDto.getStatusName().equals(ApplicationStatus.valueOf(status).toString()));
        assert (applicationDto.getDoctor().equals(doctor));
        assert (applicationDto.getCreateBy() == 1);
        assert (applicationDto.getUpdateBy() == 1);
        assert (applicationDto.getCreateTime() != null);
        assert (applicationDto.getUpdateTime() != null);
        String serialNumber = applicationDto.getSerialNumber();
        assert (serialNumber != null);
        Long id = applicationDto.getId();
        assert (serialNumber.startsWith("A") && serialNumber.endsWith(id.toString()));
        assert (applicationDto.getUpdateBy() == 1);
        sampleDtos = applicationDto.getSamples();
        assert (sampleDtos != null && sampleDtos.size() == 1);
        sampleDto = sampleDtos.get(0);
        assert (sampleDto.getName().equals(sampleName));
        assert (sampleDto.getCreateTime() != null);
        assert (sampleDto.getUpdateTime() != null);
        assert (sampleDto.getCategory() == category);
        String sampleNumber = sampleDto.getSerialNumber();
        assert (sampleNumber != null);
        Long sampleId = sampleDto.getId();
        assert (sampleNumber.startsWith("S") && sampleNumber.endsWith(sampleId.toString()));


        List<ApplicationBriefDto> applicationBriefDtos = applicationApplication.getApplicationBriefByCondition(applicationCondition);
        assert (applicationBriefDtos != null && applicationBriefDtos.size() == 1);
        ApplicationBriefDto applicationBriefDto = applicationBriefDtos.get(0);
        assert (applicationBriefDto != null);
        assert (applicationBriefDto.getStatus() == status);
        assert (applicationBriefDto.getSex() == sex);
        assert (applicationBriefDto.getPatientName().equals(patientName));
        assert (applicationBriefDto.getStatusName().equals(ApplicationStatus.valueOf(status).toString()));
        assert (applicationBriefDto.getCreateBy() == 1);
        assert (applicationBriefDto.getCreateTime() != null);
        String serialNumberB = applicationBriefDto.getSerialNumber();
        assert (serialNumberB != null);
        Long bId = applicationDto.getId();
        assert (serialNumberB.startsWith("A") && serialNumberB.endsWith(bId.toString()));
        sampleDtos = applicationBriefDto.getSamples();
        assert (sampleDtos != null && sampleDtos.size() == 1);
        sampleDto = sampleDtos.get(0);
        assert (sampleDto.getName().equals(sampleName));
        assert (sampleDto.getCreateTime() != null);
        assert (sampleDto.getUpdateTime() != null);
        assert (sampleDto.getCategory() == category);
        String sampleNumberB = sampleDto.getSerialNumber();
        assert (sampleNumberB != null);
        Long sampleIdB = sampleDto.getId();
        assert (sampleNumberB.startsWith("S") && sampleNumberB.endsWith(sampleIdB.toString()));
    }

    /**
     * 测试
     * 1.GetApplicationAndSamplesByCondition
     * 2.CountApplicationAndSamplesByCondition
     */
    @Test
    public void getApplicationAndSamplesByConditionTest(){
        ApplicationDto applicationDto = new ApplicationDto();
        String patientName = "测试姓名-"+System.currentTimeMillis();
        UserSimpleDto createBy = new UserSimpleDto();
        createBy.setId(1L);
        Integer status = ApplicationStatus.PrepareRegister.toCode();
        Integer sex = 0;
        String doctor = "送检医生";
        applicationDto.setPatientName(patientName);
        applicationDto.setCreateBy(1L);
        applicationDto.setStatus(status);
        applicationDto.setSex(sex);
        applicationDto.setDoctorTel("dasda s");
        applicationDto.setDoctor(doctor);
        List<SampleDto> sampleDtos = new ArrayList<>();
        SampleDto sampleDto = new SampleDto();
        String sampleName = "测试样本";
        Integer category = 1;
        sampleDto.setCreateBy(createBy);
        sampleDto.setName(sampleName);
        sampleDto.setCategory(category);
        sampleDtos.add(sampleDto);
        applicationDto.setSamples(sampleDtos);
        applicationApplication.createApplication(applicationDto);
        ApplicationCondition applicationCondition = new ApplicationCondition();
        applicationCondition.setCreateBy(1L);
        applicationCondition.setFilter(patientName);
        List<ApplicationSampleDto> applicationSampleDtos = applicationApplication.getApplicationAndSamplesByCondition(applicationCondition);
        Long total = applicationApplication.countApplicationAndSamplesByCondition(applicationCondition);
        assert (total != null && total == 1);
        assert (applicationSampleDtos != null && applicationSampleDtos.size() == 1);
        ApplicationSampleDto applicationSampleDto = applicationSampleDtos.get(0);
        assert (applicationSampleDto != null);
        Long sampleId = applicationSampleDto.getSampleId();
        assert (sampleId != null);
        assert (applicationSampleDto.getApplicationId() != null);
        String sampleNo = applicationSampleDto.getSampleNumber();
        assert (sampleNo != null && sampleNo.startsWith("S") && sampleNo.endsWith(sampleId.toString()));
        assert (applicationSampleDto.getName().equals(sampleName));
        assert (applicationSampleDto.getCategory().equals(category));
        assert (applicationSampleDto.getPatientName().equals(patientName));
        assert (applicationSampleDto.getDoctor().equals(doctor));
        assert (applicationSampleDto.getCreateTime() != null);
    }
}
