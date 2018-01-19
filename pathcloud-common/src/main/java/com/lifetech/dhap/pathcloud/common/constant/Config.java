package com.lifetech.dhap.pathcloud.common.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by gdw on 7/12/16.
 */
public class Config {

    private static final Logger logger = LoggerFactory.getLogger(Config.class);
    protected static final Properties p = new Properties();

    private Config() {

    }

    /**
     * 静态读入属性文件到Properties p变量中
     */
    protected static void init(String propertyFileName) {
        InputStream in = null;
        try {
            in = Config.class.getClassLoader().getResourceAsStream(propertyFileName);
            if (in != null) {
                p.load(in);
            }
        } catch (IOException e) {
            logger.error("load " + propertyFileName + " into Constants error!");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("close " + propertyFileName + " error!");
                }
            }
        }
    }

    /**
     * 封装了Properties类的getProperty函数,使p变量对子类透明.
     *
     * @param key          property key.
     * @param defaultValue 当使用property key在properties中取不到值时的默认值.
     */
    protected static String getProperty(String key, String defaultValue) {
        return p.getProperty(key, defaultValue);
    }

    static {
        init("config.properties");
    }

    public final static Integer rsyncMaxMinutes = Integer.parseInt(getProperty("rsync.max.minutes", "10"));

    public final static String nfsMntRw = getProperty("rsync.nfs.rw", "/nfs_mnt_rw/");

    public final static String nfsMntRo = getProperty("rsync.nfs.ro", "/nfs_mnt_ro/");

    public final static String serialNumberSeparator = getProperty("serial.number.separator", "-");

    public final static String token = getProperty("public.api.token", "5d50b2df9cc24dfdb2094bdd19f8f5a6");

    public final static Integer qualifiedScore = Integer.parseInt(getProperty("qualified.score", "3"));

    public final static Integer qualifiedDefaultScore = Integer.parseInt(getProperty("qualified.default.score", "5"));

    public final static Integer dyeType = Integer.parseInt(getProperty("dye.type", "0"));
    public final static String dyeMarker = (getProperty("dye.marker", null));

    public final static String FROZENLETTER = "F";
    public final static String IHCLETTER = "I";
    public final static String DYELETTER = "T";
    public final static String CONSULTLETTER = "H";
    public final static String APPLICATIONLETTER = "AP";
    public final static String SAMPLELETTER = "SA";
}
