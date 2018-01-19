package com.lifetech.dhap.pathcloud.grossing.api;

import com.lifetech.dhap.pathcloud.application.api.ApplicationApi;
import com.lifetech.dhap.pathcloud.application.api.PathologyApi;
import com.lifetech.dhap.pathcloud.application.api.vo.ApplicationVO;
import com.lifetech.dhap.pathcloud.application.api.vo.PathologyVO;
import com.lifetech.dhap.pathcloud.application.api.vo.SampleVO;
import com.lifetech.dhap.pathcloud.application.application.dto.PathologyDto;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.ApplicationStatus;
import com.lifetech.dhap.pathcloud.common.BaseTest;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.tracking.api.GrossingApi;
import com.lifetech.dhap.pathcloud.tracking.api.vo.BlockVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.GrossingSaveVO;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockDetailDto;
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
 * Created by HP on 2016/12/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-*.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional(rollbackFor = Exception.class)
@Ignore
public class GrossingApiTest extends BaseTest{

    @Autowired
    private GrossingApi grossingApi;

    @Autowired
    private ApplicationApi applicationApi;

    @Autowired
    private PathologyApi pathologyApi;

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
     * 取材记录保存测试
     *
     * @param
     * @return
     * @throws BuzException
     */
    @Test
    public void  addGrossingTest() throws IllegalAccessException {

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
        PathologyDto data2 = (PathologyDto) pathResponseVO.getData();


        GrossingSaveVO grossingSaveVO=new GrossingSaveVO();
        grossingSaveVO.setJujianNote("东西");
        grossingSaveVO.setOperatorId(1L);
        grossingSaveVO.setSecOperatorId(1L);

        BlockVO blockVO = new BlockVO();
        blockVO.setBiaoshi(1);
        blockVO.setSubId("A");
        blockVO.setBodyPart("输尿管切缘");
        blockVO.setCount(1);
        blockVO.setUnit(1);
        blockVO.setBasketNumber(1L);
        blockVO.setNote("test");
        blockVO.setPrint(1);

        List<BlockVO> blockVOs = new ArrayList<>();
        blockVOs.add(blockVO);
        grossingSaveVO.setBlocks(blockVOs);

         grossingApi.addGrossing(data2.getId(), grossingSaveVO);
        Integer page=1;
        Integer length=10;
        ResponseVO grossingForConfirm = grossingApi.getGrossingForConfirm(page, length, null, null, 1L, "1");
        Map<String,Object> data = (Map<String, Object>) grossingForConfirm.getData();
        List<BlockDetailDto> data1 = (List<BlockDetailDto>) data.get("data");
        for (BlockDetailDto dto:data1){
            if (dto.getPathologyId().equals(data2.getId())){
                assert (dto.getApplicationId().equals(applicationId));
                assert (dto.getBodyPart().equals(blockVO.getBodyPart()));
                assert (dto.getBiaoshi().equals(blockVO.getBiaoshi()));
                assert (dto.getPathologyNumber().equals(data2.getSerialNumber()));
                assert (dto.getDepartments().equals(applicationVO.getDepartments()));
            }
        }

    }


    /**
     * 取材确认测试
     *
     * @param
     * @return
     * @throws BuzException
     */
    @Test
    public void confirmGrossingTest() throws IllegalAccessException {

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
        PathologyDto data2 = (PathologyDto) pathResponseVO.getData();


        GrossingSaveVO grossingSaveVO=new GrossingSaveVO();
        grossingSaveVO.setJujianNote("东西");
        grossingSaveVO.setOperatorId(1L);
        grossingSaveVO.setSecOperatorId(1L);

        BlockVO blockVO = new BlockVO();
        blockVO.setBiaoshi(1);
        blockVO.setSubId("A");
        blockVO.setBodyPart("输尿管切缘");
        blockVO.setCount(1);
        blockVO.setUnit(1);
        blockVO.setBasketNumber(1L);
        blockVO.setNote("test");
        blockVO.setPrint(1);

        List<BlockVO> blockVOs = new ArrayList<>();
        blockVOs.add(blockVO);
        grossingSaveVO.setBlocks(blockVOs);

        grossingApi.addGrossing(data2.getId(), grossingSaveVO);
        Integer page=1;
        Integer length=10;
        ResponseVO grossingForConfirm = grossingApi.getGrossingForConfirm(page, length, null, null, 1L, "1");
        Map<String,Object> data = (Map<String, Object>) grossingForConfirm.getData();
        List<BlockDetailDto> data1 = (List<BlockDetailDto>) data.get("data");
        for (BlockDetailDto dto:data1){
            if (dto.getPathologyId().equals(data2.getId())){
                grossingApi.confirmGrossing(dto.getBasketNumber().toString());
            }
        }



        Integer page1=1;
        Integer length1=10;
        Integer order=null;
        String sort=null;
        Integer status1=null;
        String filter=null;
        Integer departments=null;
        Long operator=null;
        Long secOperator=null;
        Long timeStart=null;
        Long timeEnd=null;
        ResponseVO grossing = grossingApi.getGrossing(page1, length1, order, sort, status1,
                filter, departments, operator, secOperator, timeStart, timeEnd);

        Map<String,Object> data4 = (Map<String, Object>) grossing.getData();
        List<BlockDetailDto> data3 = (List) data4.get("data");
        for (BlockDetailDto blockDetailDto:data3){

       if (blockDetailDto.getPathologyId().equals(data2.getId())){
           assert (blockDetailDto.getStatusName().equals("待脱水"));
           assert (blockDetailDto.getBasketNumber() != null);
           assert (blockDetailDto.getName() != null);
           assert (blockDetailDto.getStatusName() != null);
           assert (blockDetailDto.getApplicationId() != null);
           assert (blockDetailDto.getStatus() != null);
            }

        }


    }

    /**
     * 脱水确认列表查询
     *
     * @param 
     * @param 
     * @return
     * @throws BuzException
     */
    @Test
    public void getGrossingForDehydrate() throws IllegalAccessException {
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
        PathologyDto data2 = (PathologyDto) pathResponseVO.getData();


        GrossingSaveVO grossingSaveVO=new GrossingSaveVO();
        grossingSaveVO.setJujianNote("东西");
        grossingSaveVO.setOperatorId(1L);
        grossingSaveVO.setSecOperatorId(1L);

        BlockVO blockVO = new BlockVO();
        blockVO.setBiaoshi(1);
        blockVO.setSubId("A");
        blockVO.setBodyPart("输尿管切缘");
        blockVO.setCount(1);
        blockVO.setUnit(1);
        blockVO.setBasketNumber(1L);
        blockVO.setNote("test");
        blockVO.setPrint(1);

        List<BlockVO> blockVOs = new ArrayList<>();
        blockVOs.add(blockVO);
        grossingSaveVO.setBlocks(blockVOs);

        grossingApi.addGrossing(data2.getId(), grossingSaveVO);
        Integer page=1;
        Integer length=10;
        ResponseVO grossingForConfirm = grossingApi.getGrossingForConfirm(page, length, null, null, 1L, "1");
        Map<String,Object> data = (Map<String, Object>) grossingForConfirm.getData();
        List<BlockDetailDto> data1 = (List<BlockDetailDto>) data.get("data");
        for (BlockDetailDto dto:data1){
            if (dto.getPathologyId().equals(data2.getId())){
                grossingApi.confirmGrossing(dto.getBasketNumber().toString());
            }
        }



        Integer page1=1;
        Integer length2=10;
        String basketNumbers="12";
        ResponseVO grossingForDehydrate = grossingApi.getGrossingForDehydrate(page1, length2, basketNumbers,1L);
        Map<String,Object> data4 = (Map<String, Object>) grossingForDehydrate.getData();
        List<BlockDetailDto> data3 = (List<BlockDetailDto>) data4.get("data");
        for (BlockDetailDto blockDetailDto : data3){
            assert (blockDetailDto.getBasketNumber() != null);
            assert (blockDetailDto.getBlockId() != null);
            assert (blockDetailDto.getName() != null);
            assert (blockDetailDto.getStatusName().equals("待脱水"));
            assert (blockDetailDto.getApplicationId() != null);
            assert (blockDetailDto.getBiaoshiName() != null);
        }
    }

}
