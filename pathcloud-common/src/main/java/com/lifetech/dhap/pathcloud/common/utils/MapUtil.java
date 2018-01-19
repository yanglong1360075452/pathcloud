package com.lifetech.dhap.pathcloud.common.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by LiuMei on 2017-08-31.
 */
public class MapUtil {

    /**
     * map按照key value添加元素
     * @param key
     * @param value
     * @return
     */
    public static Map mapGroup(Map baseMap,Long key,Long value){
        if (baseMap != null && baseMap.size() > 0) {
            Set<Map.Entry<Long, HashSet<Long>>> entries = baseMap.entrySet();
            boolean has = false;
            for (Map.Entry<Long, HashSet<Long>> entry : entries) {
                if (entry.getKey().equals(key)) {
                    has = true;
                    entry.getValue().add(value);
                }
            }
            if (!has) {
                HashSet<Long> values = new HashSet<>();
                values.add(value);
                baseMap.put(key, values);
            }
        } else {
            baseMap = new HashMap();
            HashSet<Long> values = new HashSet<>();
            values.add(value);
            baseMap.put(key, values);
        }
        return baseMap;
    }

}
