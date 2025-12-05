package com.marks.nio_common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: Dateutil </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/5 17:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class Dateutil {
    /**
     * 取得今天的日期
     *
     * @return
     */
    public static String getToday()
    {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(new Date().getTime());
    }

    /**
     * 取得昨天的日期
     *
     * @return
     */
    public static String getYestoday()
    {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date date = calendar.getTime();
        return sdf.format(date.getTime());
    }

    public static String getNow()
    {
        //HH表示用24小时制，如18；hh表示用12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(new Date().getTime());

    }

    public static String getTime()
    {
        //HH表示用24小时制，如18；hh表示用12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        return sdf.format(new Date().getTime());

    }
}
