package com.lifetech.dhap.pathcloud.common.utils;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Administrator on 2016/5/4 0004.
 */
public class DateUtil {
    static Logger log = LoggerFactory.getLogger(DateUtil.class);

    public static Date dateFormat(String data){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(data);
            return date;
        } catch (ParseException e) {
            log.error("Error format " + data);
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
    }


    public static void main(String[] args){
        SimpleDateFormat foo = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println("foo:" + foo.format(new Date()));

        Calendar gc = GregorianCalendar.getInstance();
        System.out.println("gc.getTime():" + gc.getTime());
        System.out.println("gc.getTimeInMillis():" + new Date(gc.getTimeInMillis()));

        // 当前系统默认时区的时间：
        Calendar calendar = new GregorianCalendar();
        System.out.print("时区：" + calendar.getTimeZone().getID() + "  ");
        System.out.println("时间：" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));

        // 美国洛杉矶时区
        TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
        // 时区转换
        calendar.setTimeZone(tz);
        System.out.print("时区：" + calendar.getTimeZone().getID() + "  ");
        System.out.println("时间：" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));

        // 1、取得本地时间：
        Calendar cal = Calendar.getInstance();

        // 2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);

        // 3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);

        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));

        // 之后调用cal.get(int x)或cal.getTimeInMillis()方法所取得的时间即是UTC标准时间。
        System.out.println("UTC:" + new Date(cal.getTimeInMillis()));

        Calendar calendar1 = Calendar.getInstance();
        TimeZone tztz = TimeZone.getTimeZone("GMT");
        calendar1.setTimeZone(tztz);
        System.out.println(calendar.getTime());
        System.out.println(calendar.getTimeInMillis());

        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        // df.setTimeZone(TimeZone.getTimeZone("UTC"));
        // System.out.println(df.parse("2014-08-23T09:20:05Z").toString());

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date t = new Date();
        System.out.println(df1.format(t));
//        System.out.println(df1.format(df1.parse("2014-08-27T18:02:59.676Z")) + "***********");
        df1.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println(df1.format(t));
        System.out.println("-----------");
//        System.out.println(df1.format(df1.parse("2014-08-27T18:02:59.676Z")) + "***********");
        System.out.println("2014-08-27T18:02:59.676Z");
    }
}
