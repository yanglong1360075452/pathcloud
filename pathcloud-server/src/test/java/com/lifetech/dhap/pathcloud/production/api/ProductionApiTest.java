package com.lifetech.dhap.pathcloud.production.api;

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
import com.lifetech.dhap.pathcloud.tracking.api.*;
import com.lifetech.dhap.pathcloud.tracking.api.vo.*;
import com.lifetech.dhap.pathcloud.tracking.application.dto.PrepareDistributeDto;
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
 * Created by HP on 2016/12/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext-*.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional(rollbackFor = Exception.class)
@Ignore
public class ProductionApiTest extends BaseTest{


    @Autowired
    private DyeApi dyeApi;

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

    @Autowired
    private ProductionApi productionApi;

    @Autowired
    private SlideDistributeApi slideDistributeApi;

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
    public void productionConfirmTest() throws IllegalAccessException {
        ApplicationVO applicationVO = new ApplicationVO();
        String patientName = "测试姓名-"+System.currentTimeMillis();
        Long createBy = 1L;
        Integer status = ApplicationStatus.PrepareRegister.toCode();
        Integer sex = 0;
        String doctor = "送检医生";
        applicationVO.setPatientName(patientName);
        applicationVO.setStatus(status);
        applicationVO.setDoctorTel("dasda s");
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
        grossingApi.addGrossing(pathId,grossingSaveVO);
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
        assert (embedVO != null);
        assert (embedVO.getSubId().equals("1"));
        assert (embedVO.getApplicationSerialNumber().equals(applicationVO.getSerialNumber()));
        assert (embedVO.getPathologySerialNumber().equals(pathologyDto.getSerialNumber()));
        assert (embedVO.getStatus().equals(PathologyStatus.PrepareEmbed.toCode()));
        assert (!embedVO.getEmbedPause());
        NoteVO noteVO = new NoteVO();
        noteVO.setBlockId(blockId);
        embedApi.embedConfirm(blockId,null);

        ResponseVO block2 = embedApi.getBlockBySerialNumber(pathNo + 1, null);
        EmbedVO  data = (EmbedVO) block2.getData();

        NoteVO noteVO1 = new NoteVO();
        noteVO1.setBlockId(data.getBlockId());
        noteVO1.setSpecialDye(0);
        List<NoteVO> noteVOs = new ArrayList<>();
        noteVOs.add(noteVO1);
        sectionApi.sectionsConfirm(noteVOs);
        ResponseVO blockBySerialNumber = dyeApi.getSlideBySerialNumber(embedVO.getPathologySerialNumber()+"-"+"1"+"-"+"1");

        List<DyeInfoVO> dyeInfoVOS = (List<DyeInfoVO>) blockBySerialNumber.getData();
        List<Long> lists = new ArrayList<>();
        for (DyeInfoVO dyeInfoVO:dyeInfoVOS){
            lists.add(dyeInfoVO.getId());
        }
        DyeConfirmVO dyeConfirmVO = new DyeConfirmVO();
        dyeConfirmVO.setSlideIds(lists);
        dyeApi.dyeConfirm(dyeConfirmVO);

        ResponseVO production = productionApi.getSlidesInfoBySerialNumber(embedVO.getPathologySerialNumber()+"-"+"1"+"-"+"1");
        assert (production.getCode() == 0);
        List<ProductionVO> productionVOS  = (List<ProductionVO>) production.getData();
        List<Long> slids = new ArrayList<>();
        for (ProductionVO productionVO:productionVOS){
            assert (productionVO.getStatus().equals(PathologyStatus.PrepareCompletionConfirm.toCode()));
            assert (productionVO.getPathNo().equals(embedVO.getPathologySerialNumber()));
            slids.add(productionVO.getId());
        }

        ProductionSaveVO productionSaveVO = new ProductionSaveVO();
        productionSaveVO.setPathId(embedVO.getPathologyId());
        productionSaveVO.setSlideIds(slids);
        List<ProductionSaveVO> lists2 = new ArrayList<>();
        lists2.add(productionSaveVO);

        productionApi.productionConfirm(lists2);

        Integer page=1;
        Integer length=Integer.MAX_VALUE;

        ResponseVO prepareDistribute = slideDistributeApi.getPrepareDistribute(page, length, null, null, null, null);
        Map<String,Object> map= (Map<String, Object>) prepareDistribute.getData();
        List<PrepareDistributeDto> data1 = (List<PrepareDistributeDto>) map.get("data");
        for (PrepareDistributeDto dto:data1){
            if (dto.getPathId().equals(productionSaveVO.getPathId())){
                assert (dto.getStatus().equals(PathologyStatus.PrepareCompletionDistribute.toCode()));
                assert (dto.getSerialNumber().equals(pathologyDto.getSerialNumber()));
            }
        }


    }



}
