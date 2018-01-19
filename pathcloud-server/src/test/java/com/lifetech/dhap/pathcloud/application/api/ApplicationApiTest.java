package com.lifetech.dhap.pathcloud.application.api;

import com.lifetech.dhap.pathcloud.application.api.vo.ApplicationVO;
import com.lifetech.dhap.pathcloud.application.api.vo.RejectVO;
import com.lifetech.dhap.pathcloud.application.api.vo.SampleVO;
import com.lifetech.dhap.pathcloud.application.application.dto.ApplicationBriefDto;
import com.lifetech.dhap.pathcloud.application.application.dto.SampleDto;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.ApplicationStatus;
import com.lifetech.dhap.pathcloud.common.BaseTest;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
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


/**
 * Created by LuoMo on 2016-11-23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-*.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional(rollbackFor = Exception.class)
@Ignore
public class ApplicationApiTest extends BaseTest{

    @Autowired
    private ApplicationApi applicationApi;

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
     * 创建申请
     *
     * @param
     * @return
     * @throws BuzException
     */
    @Test
    public void createApplicationTest() throws IllegalAccessException {
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
        assert (returnData.getSex().equals(applicationVO.getSex()));
        assert (returnData.getDoctor().equals(applicationVO.getDoctor()));
        assert (returnData.getDoctorTel().equals(applicationVO.getDoctorTel()));
        assert (returnData.getPatientName().equals(applicationVO.getPatientName()));




    }


    /**
     * 申请查看
     *
     * @param
     * @param
     * @param
     * @param
     * @param
     * @returnpage
     * @param
     * @throws BuzException
     */
    @Test
    public void getApplicationsTest() throws IllegalAccessException {
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


        Integer page = 1;
        Integer length = 1;
        ResponseVO responseVO1 = applicationApi.getApplications(page,length,patientName,null,null,null);

        assert (responseVO1.getCode() == 0);
        Map<String,Object> data = (Map<String,Object>)responseVO1.getData();
        assert (data != null);
        assert (data.get("page").equals(page));
        assert (data.get("length").equals(length));
        assert (data.get("total").toString().equals("1"));
        List<ApplicationBriefDto> datas = (List<ApplicationBriefDto>) data.get("data");
        assert (datas != null && datas.size() == 1);
        ApplicationBriefDto applicationBriefDto = datas.get(0);

        assert (applicationBriefDto != null);
        assert (applicationBriefDto.getStatus() == status);
        assert (applicationBriefDto.getSex() == sex);
        assert (applicationBriefDto.getPatientName().equals(patientName));
        assert (applicationBriefDto.getStatusName().equals(ApplicationStatus.valueOf(status).toString()));
        assert (applicationBriefDto.getCreateBy() == createBy);
        assert (applicationBriefDto.getCreateTime() != null);
        String serialNumber = applicationBriefDto.getSerialNumber();
        assert (serialNumber != null);
        Long id = applicationBriefDto.getId();
        assert (serialNumber.startsWith("A") && serialNumber.endsWith(id.toString()));
        List<SampleDto> sampleDtos = applicationBriefDto.getSamples();
        assert (sampleDtos != null && sampleDtos.size() == 1);
        SampleDto sampleDto = sampleDtos.get(0);
        assert (sampleDto.getName().equals(sampleName));
        assert (sampleDto.getCreateTime() != null);
        assert (sampleDto.getUpdateTime() != null);
        assert (sampleDto.getCategory() == category);
        String sampleNumber = sampleDto.getSerialNumber();
        assert (sampleNumber != null);
        Long sampleId = sampleDto.getId();
        assert (sampleNumber.startsWith("S") && sampleNumber.endsWith(sampleId.toString()));

    }

    /**
     * 撤销申请
     *
     * @param
     * @return
     * @throws BuzException
     */
    @Test
    public void cancelApplicationTest() throws IllegalAccessException {
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


        applicationApi.cancelApplication(returnData.getId());
        ResponseVO application = applicationApi.getApplicationBySerialNumber(returnData.getSerialNumber());
        ApplicationVO applicationVO2 = (ApplicationVO) application.getData();
      assert (applicationVO2.getStatus().equals(4));
      assert (applicationVO2.getPatientName().equals(applicationVO.getPatientName()));
      assert (applicationVO2.getDoctorTel().equals(applicationVO.getDoctorTel()));
      assert (applicationVO2.getDoctor().equals(applicationVO.getDoctor()));
      assert (applicationVO2.getSex().equals(applicationVO.getSex()));


    }
    @Test
    public void rejectApplicationTest() throws IllegalAccessException {
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
        ApplicationVO applicationVO1 = (ApplicationVO) responseVO.getData();

        String rejectReason="nihao";
        String reasonType="样本数量不全";
        RejectVO rejectVO = new RejectVO();
        rejectVO.setReasonType(reasonType);
        rejectVO.setRejectReason(rejectReason);

        applicationApi.rejectApplication(applicationVO1.getId(),rejectVO);
        ResponseVO application = applicationApi.getApplicationBySerialNumber(applicationVO1.getSerialNumber());
        ApplicationVO applicationVO2 = (ApplicationVO) application.getData();

        assert (applicationVO2.getStatus().equals(ApplicationStatus.Reject.toCode()));
        assert (applicationVO2.getRejectReason().equals(rejectVO.getRejectReason()));
        assert (applicationVO2.getReasonType().equals(rejectVO.getReasonType()));


    }

}
