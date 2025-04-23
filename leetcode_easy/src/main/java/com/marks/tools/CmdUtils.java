package com.marks.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <p>项目名称: cmd 工具类 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/23 15:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class CmdUtils {

    /**
     * @Description:
     * 使用tasklist命令检查进程, 判断 processName.exe 是否存在
     * @param processName
     * @return boolean
     * @author marks
     * @CreateDate: 2025/4/23 15:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isProcessRunning(String processName) throws IOException {
        Process process = Runtime.getRuntime().exec("tasklist /FI \"IMAGENAME eq " + processName + ".exe\"");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains(processName)) {
                return true;
            }
        }
        return false;
    }
}
