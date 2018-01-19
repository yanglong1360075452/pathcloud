package com.lifetech.dhap.pathcloud.common.domain;

import java.util.Date;

/**
 * Created by LiuMei on 2017-03-01.
 */
public interface CommonRepository {

    /**
     * 查询数据库当前时间
     * @return
     */
    Date getDBNow();

}
