package com.lifetech.dhap.pathcloud.Embed.api;

import com.lifetech.dhap.pathcloud.application.api.ApplicationApi;
import com.lifetech.dhap.pathcloud.application.api.PathologyApi;
import com.lifetech.dhap.pathcloud.application.api.vo.ApplicationVO;
import com.lifetech.dhap.pathcloud.application.api.vo.PathologyVO;
import com.lifetech.dhap.pathcloud.application.api.vo.SampleVO;
import com.lifetech.dhap.pathcloud.application.application.dto.PathologyDto;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.ApplicationStatus;
import com.lifetech.dhap.pathcloud.common.BaseTest;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.dehydrate.api.DehydrateApi;
import com.lifetech.dhap.pathcloud.dehydrate.api.vo.DehydrateVO;
import com.lifetech.dhap.pathcloud.tracking.api.EmbedApi;
import com.lifetech.dhap.pathcloud.tracking.api.GrossingApi;
import com.lifetech.dhap.pathcloud.tracking.api.vo.BlockVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.EmbedVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.GrossingSaveVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.NoteVO;
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

import static org.junit.Assert.assertTrue;

/**
 * Created by LiuMei on 2017-07-26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-*.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional(rollbackFor = Exception.class)
@Ignore
public class EmbedApiTest extends BaseTest{

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
     * 获取包埋信息/暂停包埋/取消包埋暂停/包埋确认
     * @throws IllegalAccessException
     */
    @Test
    public void embedTest() throws IllegalAccessException {
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
        assert (embedVO != null);
        Long blockId = embedVO.getBlockId();
        assert (embedVO.getSubId().equals("1"));
        assert (embedVO.getApplicationSerialNumber().equals(applicationVO.getSerialNumber()));
        assert (embedVO.getPathologySerialNumber().equals(pathologyDto.getSerialNumber()));
        assert (embedVO.getStatus().equals(PathologyStatus.PrepareEmbed.toCode()));
        assert (!embedVO.getEmbedPause());
        NoteVO noteVO = new NoteVO();
        noteVO.setBlockId(blockId);
        embedApi.pause(noteVO);
        try {
            embedApi.embedConfirm(blockId,null);
        } catch (Exception e) {
            assertTrue(e instanceof BuzException);
        }
        embedApi.cancelPause(noteVO);
        embedApi.embedConfirm(blockId,null);
        ResponseVO block2 = embedApi.getBlockBySerialNumber(pathNo + 1, null);
        EmbedVO  data = (EmbedVO) block2.getData();
        assert(data.getStatus().equals(PathologyStatus.PrepareSection.toCode()));
    }

}
