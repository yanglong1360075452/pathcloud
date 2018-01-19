package com.lifetech.dhap.pathcloud.common.response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gdw on 4/18/16.
 *
 * 分页数据基类
 */
public class PageDataVO extends ResponseVO {

    public PageDataVO(Integer page, Integer length, Long total, Object data){
        super();

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("page", page);
        dataMap.put("length", length);
        dataMap.put("total", total);
        dataMap.put("data", data);

        this.setData(dataMap);
    }

    public PageDataVO(Integer page, Long total, Object data){
        super();

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("page", page);
        dataMap.put("length", 10);
        dataMap.put("total", total);
        dataMap.put("data", data);

        this.setData(dataMap);
    }

}
