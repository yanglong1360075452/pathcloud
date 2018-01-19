package com.lifetech.dhap.pathcloud.common.utils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created by HP on 2016/6/13.
 */
public class ListUtil {

    /**
     * 数组相同数字分组
     *
     * @param data
     * @return
     */
    public static List<List<Integer>> splitSame(List<Integer> data) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<Integer> group = null;
        for (int value : data) {
            if (group == null || group.get(group.size() - 1) != value) {
                group = new ArrayList<Integer>();
                result.add(group);
            }
            group.add(value);
        }
        return result;
    }


    /**
     * 数组连续数字分组
     *
     * @param data
     * @return
     */
    public static List<List<Integer>> splitCon(List<Integer> data) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<Integer> group = null;
        for (int value : data) {
            if (group == null || group.get(group.size() - 1) + 1 != value) {
                group = new ArrayList<Integer>();
                result.add(group);
            }
            group.add(value);
        }
        return result;
    }

    /**
     * 求两个Long List的差集
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public static List<Long> minus(List<Long> arr1, List<Long> arr2) {
        LinkedList<Long> list = new LinkedList<>();
        LinkedList<Long> history = new LinkedList<>();
        List<Long> longerArr = arr1;
        List<Long> shorterArr = arr2;
        //找出较长的数组来减较短的数组
        if (arr1.size() > arr2.size()) {
            longerArr = arr2;
            shorterArr = arr1;
        }
        for (Long l : longerArr) {
            if (!list.contains(l)) {
                list.add(l);
            }
        }
        for (Long l : shorterArr) {
            if (list.contains(l)) {
                history.add(l);
                list.remove(l);
            } else {
                if (!history.contains(l)) {
                    list.add(l);
                }
            }
        }
        return list;
    }

    /**
     * 从原数组减去传入数组项
     *
     * @param list
     * @param minusList
     * @return
     */
    public static List<Integer> minusParam(List<Integer> list, List<Integer> minusList) {
        if (list != null && list.size() > 0) {
            if (minusList != null && minusList.size() > 0) {
                for (Integer o : minusList) {
                    for (Integer oo : list) {
                        if (oo.equals(o)) {
                            list.remove(oo);
                            break;
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 字符串数组转数字
     *
     * @param strings
     * @return
     */
    public static List<Long> arrStringToLong(String[] strings) {
        if (!ArrayUtils.isEmpty(strings)) {
            List<String> ss = Arrays.asList(strings);
            List<Long> ids = new ArrayList<>();
            for (String s : ss) {
                if (StringUtils.isNotBlank(s)) {
                    ids.add(Long.parseLong(s));
                }
            }
            return ids;
        }
        return null;
    }

    /**
     * 检查字符串List是否有重复元素
     *
     * @param array
     * @return
     */
    public static boolean checkStringRepeat(List<String> array) {
        Set<String> set = new HashSet<>();
        for (String o : array) {
            set.add(o);
        }
        if (set.size() != array.size()) {
            return true;
        } else {
            return false;
        }
    }
}

