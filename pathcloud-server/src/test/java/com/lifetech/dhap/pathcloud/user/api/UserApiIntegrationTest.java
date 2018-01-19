package com.lifetech.dhap.pathcloud.user.api;

import com.lifetech.dhap.pathcloud.common.BaseJettyTest;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-11-14-10:49
 */
@Ignore
public class UserApiIntegrationTest extends BaseJettyTest {

    @Test
    public void testGetUsers() throws Exception {
        client.path("user").query("page", 1).query("size", 10);
        ResponseVO res = client.get(ResponseVO.class);
        assert (res.getCode() == 0);
    }
}
