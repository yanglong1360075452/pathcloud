package com.lifetech.dhap.pathcloud.common.utils;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMei on 2016-12-12.
 */
public class StringUtil {

    private static final Logger log = LoggerFactory.getLogger(StringUtil.class);

    public static List<Integer> stringSplitToList(String string){
        if(string == null || "".equals(string.trim())){
            return null;
        }
        List<Integer> result = new ArrayList<>();
        try {
            if (string.indexOf(",") != -1) {
                String[] item = string.split(",");
                for (String no : item) {
                    result.add(Integer.parseInt(no));
                }
            }else {
                result.add(Integer.parseInt(string));
            }
        } catch (NumberFormatException e) {
            log.error("Can't parse " + string, e);
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        return result;
    }

    /**
     * sql模糊查询 _ % 字符转义
     * @param keyword
     * @return
     */
    public static String escapeExprSpecialWord(String keyword) {
        if (StringUtils.isNotBlank(keyword)) {
            String[] fbsArr = { "_", "%"};
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }
}
