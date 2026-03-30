package com.marks.auto_script.config;

import java.io.File;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: AppConfig </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/30 15:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

public class AppConfig {

    private static final String USER_HOME = "D:";
    public static final String SCRIPT_DIR = USER_HOME + File.separator + "automated_scripts";
    public static final String CONFIG_FILE = SCRIPT_DIR + File.separator + "config.properties";

    public static final int DEFAULT_CLICK_INTERVAL = 50;
    public static final String DEFAULT_START_KEY = "F8";
    public static final String DEFAULT_PAUSE_KEY = "F9";

    static {
        File scriptDir = new File(SCRIPT_DIR);
        if (!scriptDir.exists()) {
            scriptDir.mkdirs();
        }
    }
}
