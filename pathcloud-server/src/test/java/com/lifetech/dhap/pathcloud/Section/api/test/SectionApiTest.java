package com.lifetech.dhap.pathcloud.Section.api.test;

import com.lifetech.dhap.pathcloud.application.api.ApplicationApi;
import com.lifetech.dhap.pathcloud.application.api.PathologyApi;
import com.lifetech.dhap.pathcloud.application.api.vo.ApplicationVO;
import com.lifetech.dhap.pathcloud.application.api.vo.PathologyVO;
import com.lifetech.dhap.pathcloud.application.api.vo.SampleVO;
import com.lifetech.dhap.pathcloud.application.application.dto.PathologyDto;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.ApplicationStatus;
import com.lifetech.dhap.pathcloud.common.BaseTest;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.dehydrate.api.DehydrateApi;
import com.lifetech.dhap.pathcloud.dehydrate.api.vo.DehydrateVO;
import com.lifetech.dhap.pathcloud.tracking.api.EmbedApi;
import com.lifetech.dhap.pathcloud.tracking.api.GrossingApi;
import com.lifetech.dhap.pathcloud.tracking.api.SectionApi;
import com.lifetech.dhap.pathcloud.tracking.api.vo.*;
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
 * Created by LiuMei on 2017-07-31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-*.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional(rollbackFor = Exception.class)
@Ignore
public class SectionApiTest extends BaseTest {

    @Autowired
    private EmbedApi embedApi;

    @Autowired
    private ApplicationApi applicationApi;

    @Autowired
    private PathologyApi pathologyApi;

    @Autowired
    private GrossingApi grossingApi;

    @Autowired
    private DehydrateApi dehydrateApi;

    @Autowired
    private SectionApi sectionApi;

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
    public void sectionTest() throws IllegalAccessException {

        ApplicationVO applicationVO = new ApplicationVO();
        String patientName = "测试姓名-" + System.currentTimeMillis();
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
        Long pathId = pathologyDto.getId();
        String pathNo = pathologyDto.getSerialNumber();
        GrossingSaveVO grossingSaveVO = new GrossingSaveVO();
        List<BlockVO> blockVOS = new ArrayList<>();
        BlockVO blockVO = new BlockVO();
        blockVO.setSubId("1");
        blockVO.setBasketNumber(1L);
        blockVO.setBiaoshi(1);
        blockVO.setCount(1);
        blockVO.setCreateBy(createBy);
        blockVO.setUnit(1);
        blockVO.setBodyPart("测试部位");
        blockVOS.add(blockVO);
        grossingSaveVO.setBlocks(blockVOS);
        grossingSaveVO.setOperatorId(createBy);
        grossingApi.addGrossing(pathId, grossingSaveVO);
        grossingApi.confirmGrossing("1");
        DehydrateVO dehydrateVO = new DehydrateVO();
        ArrayList<Integer> basketList = new ArrayList<>();
        basketList.add(1);
        dehydrateVO.setBaskets(basketList);
        List<Long> instrumentIds = new ArrayList<>();
        instrumentIds.add(1L);
        dehydrateVO.setInstrumentId(1L);
        dehydrateVO.setInstrumentIds(instrumentIds);
        dehydrateApi.startDehydrate(dehydrateVO);
        dehydrateApi.endDehydrate(dehydrateVO);
        ResponseVO blockResponse = embedApi.getBlockBySerialNumber(pathNo + 1, null);
        EmbedVO embedVO = (EmbedVO) blockResponse.getData();
        Long blockId = embedVO.getBlockId();
        NoteVO noteVO = new NoteVO();
        noteVO.setBlockId(blockId);
        embedApi.embedConfirm(blockId, null);
        ResponseVO sectionResponse = sectionApi.getBlockBySerialNumber(pathNo + 1, null,null);
        SectionVO sectionVO = (SectionVO) sectionResponse.getData();
        assert (sectionVO != null);
        Long blockId1 = sectionVO.getBlockId();
        assert (blockId.equals(blockId1));
        assert (sectionVO.getSubId().equals("1"));
        assert (sectionVO.getApplicationSerialNumber().equals(applicationVO.getSerialNumber()));
        assert (sectionVO.getPathologySerialNumber().equals(pathologyDto.getSerialNumber()));
        assert (sectionVO.getStatus().equals(PathologyStatus.PrepareSection.toCode()));
        noteVO = new NoteVO();
        noteVO.setBlockId(blockId);
        noteVO.setSpecialDye(0);
        List<NoteVO> noteVOS = new ArrayList<>();
        noteVOS.add(noteVO);
        sectionApi.sectionsConfirm(noteVOS);
        ResponseVO block2 = sectionApi.getBlockBySerialNumber(pathNo + 1, null,null);
        SectionVO  data = (SectionVO) block2.getData();
        assert(data.getStatus().equals(PathologyStatus.PrepareArchiving.toCode()));
    }
}
