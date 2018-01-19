package com.lifetech.dhap.pathcloud.pathology.api;

import com.lifetech.dhap.pathcloud.application.api.ApplicationApi;
import com.lifetech.dhap.pathcloud.application.api.PathologyApi;
import com.lifetech.dhap.pathcloud.application.api.vo.ApplicationVO;
import com.lifetech.dhap.pathcloud.application.api.vo.PathologyVO;
import com.lifetech.dhap.pathcloud.application.api.vo.SampleVO;
import com.lifetech.dhap.pathcloud.application.application.ApplicationApplication;
import com.lifetech.dhap.pathcloud.application.application.dto.ApplicationSampleDto;
import com.lifetech.dhap.pathcloud.application.application.dto.ApplicationDto;
import com.lifetech.dhap.pathcloud.application.application.dto.PathologyDto;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.ApplicationStatus;
import com.lifetech.dhap.pathcloud.common.BaseTest;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.security.UserContext;
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
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created by HP on 2016/11/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext-*.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional(rollbackFor = Exception.class)
@Ignore
public class PathologyApiTest extends BaseTest {

    @Autowired
    private PathologyApi pathologyApi;

    @Autowired
    private ApplicationApi applicationApi;

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

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void createPathologyTest() throws IllegalAccessException {
        ApplicationVO applicationVO = new ApplicationVO();
        String patientName = "测试姓名-"+System.currentTimeMillis();
        Long createBy = 1L;
        Integer status = ApplicationStatus.PrepareRegister.toCode();
        Integer sex = 0;
        String doctor = "送检医生";
        applicationVO.setPatientName(patientName);
        applicationVO.setStatus(status);
        applicationVO.setDoctorTel("13999999999");
        applicationVO.setSex(sex);
        applicationVO.setDoctor(doctor);
        List<SampleVO> sampleVOs = new ArrayList<>();
        SampleVO sampleVO = new SampleVO();
        String sampleName = "测试样本";
        Integer category = 1;
        sampleVO.setName(sampleName);
        sampleVO.setCategory(category);
        sampleVOs.add(sampleVO);
        applicationVO.setSamples(sampleVOs);
        applicationVO.setCreateBy(createBy);
        applicationVO.setDepartments(1);
        ResponseVO responseVO = applicationApi.createApplication(applicationVO);
        ApplicationVO returnData = (ApplicationVO) responseVO.getData();
        Long applicationId = returnData.getId();
        PathologyVO pathologyVO = new PathologyVO();
        pathologyVO.setApplicationId(applicationId);
        pathologyVO.setInspectCategory(1);
        pathologyVO.setSamples(sampleVOs);
        ResponseVO pathResponseVO = pathologyApi.createPathology(pathologyVO);
        PathologyDto pathologyDto = (PathologyDto) pathResponseVO.getData();

        assert (pathologyDto.getApplicationId().equals(applicationId));
        assert (pathologyDto.getPatientName().equals(applicationVO.getPatientName()));
        assert (pathologyDto.getDepartments().equals(applicationVO.getDepartments()));

    }




}
