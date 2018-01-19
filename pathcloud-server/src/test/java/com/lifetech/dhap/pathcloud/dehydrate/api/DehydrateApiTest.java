package com.lifetech.dhap.pathcloud.dehydrate.api;

import com.lifetech.dhap.pathcloud.common.BaseTest;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.dehydrate.api.vo.DehydratorVO;
import com.lifetech.dhap.pathcloud.dehydrate.application.DehydratorApplication;
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
import java.util.Map;

/**
 * Created by HP on 2016/12/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-*.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional(rollbackFor = Exception.class)
@Ignore
public class DehydrateApiTest extends BaseTest{

    @Autowired
    private DehydratorSettingApi dehydratorSettingApi;

    @Autowired
    private DehydratorApplication dehydratorApplication;

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
     * 添加脱水机
     *
     * @param
     * @return
     * @throws BuzException
     */
    @Test
    public void addDehydratorTest(){
        DehydratorVO dehydratorVO = new DehydratorVO();
        dehydratorVO.setName("123");
        dehydratorVO.setSn("xxx");
        dehydratorVO.setCapacity(222);
        dehydratorVO.setDescription("yyy");
        dehydratorVO.setDisabled(false);

        ResponseVO responseVO = dehydratorSettingApi.addDehydrator(dehydratorVO);
        ResponseVO lastDehydrator = dehydratorSettingApi.getLastDehydrator();
        Map data = (Map) lastDehydrator.getData();
        assert (data.get("name").equals("123"));
        assert (data.get("sn").equals("xxx"));
        assert (data.get("capacity").equals(222));
        assert (data.get("description").equals("yyy"));
        assert (data.get("disabled").equals(false));


    }


    /**
     * 移除脱水机
     *
     * @param  
     * @return
     * @throws BuzException
     */
    @Test
    public void removeDehydratorTest(){
        DehydratorVO dehydratorVO = new DehydratorVO();
        dehydratorVO.setName("123");
        dehydratorVO.setSn("xxx");
        dehydratorVO.setCapacity(222);
        dehydratorVO.setDescription("yyy");
        dehydratorVO.setDisabled(false);

        dehydratorSettingApi.addDehydrator(dehydratorVO);

        ResponseVO lastDehydrator = dehydratorSettingApi.getLastDehydrator();
        Map data = (Map) lastDehydrator.getData();
        assert (data.get("name").equals("123"));
        assert (data.get("sn").equals("xxx"));
        assert (data.get("capacity").equals(222));
        assert (data.get("description").equals("yyy"));
        assert (data.get("disabled").equals(false));
        Long instrumentId = (Long) data.get("instrumentId");
         dehydratorSettingApi.removeDehydrator(instrumentId);
        assert (data.get("name") != "123");
        assert (data.get("sn") != "xxx");
        assert (data.get("capacity") != "222");
        assert (data.get("description") != "yyy");
        assert (data.get("disabled") != "false");



    }

    /**
     * 获取脱水机状态
     *
     * @param
     * @return
     * @throws BuzException
     */
    @Test
    public void getDehydratorStatusTest(){

        DehydratorVO dehydratorVO = new DehydratorVO();
        dehydratorVO.setName("123");
        dehydratorVO.setSn("xxx");
        dehydratorVO.setCapacity(222);
        dehydratorVO.setDescription("yyy");
        dehydratorVO.setDisabled(false);

        dehydratorSettingApi.addDehydrator(dehydratorVO);

        ResponseVO dehydratorStatus = dehydratorSettingApi.getDehydratorStatus();
        List<Map<String,Object>> data = (List<Map<String, Object>>) dehydratorStatus.getData();
        for (int i=0;i<data.size();i++){
            Map<String, Object> map = data.get(i);
            assert (map.get("name") != null);
            assert (map.get("instrumentId") != null);
            assert (map.get("sn") != null);
            assert (map.get("capacity") != null);
            assert (map.get("inUse") != null);
            assert (map.get("disabled") != null);
            if (i==data.size()-1){
                Map<String, Object> map1 = data.get(i);
                assert (map1.get("name").equals("123"));
                assert (map1.get("sn").equals("xxx"));
                assert (map1.get("capacity").equals(222));
                assert (map1.get("description").equals("yyy"));
                assert (map1.get("disabled").equals(false));
                assert (map1.get("status").equals(0));
            }
        }

    }


    /**
     * 清除警告信息
     *
     * @param
     * @return
     * @throws BuzException
     */
    @Test
    public void clearWarningMsgTest(){
        DehydratorVO dehydratorVO = new DehydratorVO();
        dehydratorVO.setName("123");
        dehydratorVO.setSn("xxx");
        dehydratorVO.setCapacity(222);
        dehydratorVO.setDescription("yyy");
        dehydratorVO.setDisabled(false);

        dehydratorSettingApi.addDehydrator(dehydratorVO);

        ResponseVO lastDehydrator = dehydratorSettingApi.getLastDehydrator();
        Map data = (Map) lastDehydrator.getData();
        assert (data.get("name").equals("123"));
        assert (data.get("sn").equals("xxx"));
        assert (data.get("capacity").equals(222));
        assert (data.get("description").equals("yyy"));
        assert (data.get("disabled").equals(false));

        Long instrumentId1 = (Long) data.get("instrumentId");
        dehydratorSettingApi.clearWarningMsg(instrumentId1);
        ResponseVO lastDehydrator1 = dehydratorSettingApi.getLastDehydrator();
        Map data2 = (Map) lastDehydrator1.getData();
        assert (data.get("name") != "123");
        assert (data.get("sn") != "xxx");
        assert (data.get("capacity") != "222");
        assert (data.get("description") != "yyy");
        assert (data.get("disabled") != "false");



    }


    @Test
    public void getDehydratorsTest(){

        DehydratorVO dehydratorVO = new DehydratorVO();
        dehydratorVO.setName("123");
        dehydratorVO.setSn("xxx");
        dehydratorVO.setCapacity(222);
        dehydratorVO.setDescription("yyy");
        dehydratorVO.setDisabled(false);

        dehydratorSettingApi.addDehydrator(dehydratorVO);
        PageDataVO dehydrators = (PageDataVO) dehydratorSettingApi.getDehydrators(null,null, 1, 10,null);

        Map<String,Object> data = (Map<String, Object>) dehydrators.getData();
        Long total = (Long) data.get("total");

        DehydratorVO dehydratorVO1 = new DehydratorVO();
        dehydratorVO1.setName("222");
        dehydratorVO1.setSn("xxx1");
        dehydratorVO1.setCapacity(333);
        dehydratorVO1.setDescription("yyy1");
        dehydratorVO1.setDisabled(false);

        dehydratorSettingApi.addDehydrator(dehydratorVO1);
        PageDataVO dehydrators1 = (PageDataVO) dehydratorSettingApi.getDehydrators(null,null, 1, 10,null);
        Map<String,Object> data1 = (Map<String, Object>) dehydrators1.getData();

        Long total2 = (Long) data1.get("total");

        assert (total2-total==1);



    }



}
