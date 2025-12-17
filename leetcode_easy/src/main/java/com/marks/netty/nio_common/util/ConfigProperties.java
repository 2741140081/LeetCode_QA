package com.marks.netty.nio_common.util;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ConfigProperties </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/5 16:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class ConfigProperties {
    private String propertyFileName;
    private Properties properties = new Properties();

    public ConfigProperties() {
    }

    public ConfigProperties(String propertyFileName) {
        this.propertyFileName = propertyFileName;
    }

    protected void loadFromFile() {
        InputStream in = null;
        InputStreamReader reader = null;
        try {
            ClassLoader loader = ConfigProperties.class.getClassLoader();
            in = loader.getResourceAsStream(propertyFileName);
            if (null != in) {
                reader = new InputStreamReader(in, "UTF-8");
            } else {
                String filePath = IOUtil.getResourcePath(propertyFileName);
                in = new FileInputStream(filePath);
                //解决读非UTF-8编码的配置文件时，出现的中文乱码问题
                reader = new InputStreamReader(in, "utf-8");
            }
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按key获取值
     *
     * @param key
     * @return
     */
    public String readProperty(String key) {
        String value = "";

        value = properties.getProperty(key);

        return value;
    }


    public String getValue(String key) {

        return readProperty(key);

    }

    public int getIntValue(String key) {

        return Integer.parseInt((readProperty(key)));

    }

    public static ConfigProperties loadFromFile(Class aClass)
            throws IllegalAccessException {

        ConfigProperties propertiesUtil = null;


        return propertiesUtil;
    }

    public static void loadAnnotations(Class aClass) {

        ConfigProperties configProperties = null;
        try {
            configProperties = loadFromFile(aClass);


            if (null == configProperties) return;

            Field[] fields = aClass.getDeclaredFields();

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
