package com.marks.tools;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.marks.tools.Constants.*;

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
    private Runtime rt = Runtime.getRuntime();

    private String kill_exe_command = "taskkill /F /IM ";

    /**
     * @Description:
     * 使用tasklist命令检查进程, 判断 processName.exe 是否存在
     * @param processName
     * @return boolean
     * @author marks
     * @CreateDate: 2025/4/23 15:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isProcessRunning(String processName) {
        processName = processName.toLowerCase();
        if (!checkProgramIsLegal(processName)) {
            return false;
        }
        String command = "tasklist /FI \"IMAGENAME eq " + processName + "\"";
        try {
            Process process = rt.exec(command);

            // 显示指定 GBK 编码, 防止乱码
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(processName)) {
                    String[] parts = line.trim().split("\\s+");
                    String name = parts[0];  // 程序名
                    String pid = parts[1];         // PID
                    System.out.printf(processName +" is running, cmd output is Process: %s, Pid: %s%n", name, pid);
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:
     * kill program by program name, such as kill notepad++.exe
     * @param programName
     * @return boolean
     * @author marks
     * @CreateDate: 2025/5/16 17:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean killTargetProgramByCMD(String programName) {
        // 判断 programName is legal, such as XXX.exe
        programName = programName.toLowerCase();
        if (!checkProgramIsLegal(programName)) {
            return false;
        }
        String command = kill_exe_command + programName;

        try {
            rt.exec(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (isProcessRunning(programName)) {
            // 如果仍在运行, 返回false, 说明关闭程序失败
            return false;
        } else {
            // 关闭成功
            return true;
        }
    }

    /**
     * @Description:
     * 已经将programName 添加到系统环境变量Path
     * 通过 cmd /c start xxx.exe 来启动xxx.exe
     * 备注: start 命令是cmd 的内部命令, 需要通过 cmd /c 来进行显示调用
     * @param programName
     * @return void
     * @author marks
     * @CreateDate: 2025/5/16 17:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void launchProgram(String programName) {
        if (!checkProgramIsLegal(programName)) {
            System.err.println(programName + " 启动失败, program name 不是可执行程序");
            return;
        }
        // start 是cmd 内部命令, 需要通过cmd /c 来显示触发
        String command = "cmd /c start " + programName;
        try {
            rt.exec(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private boolean checkProgramIsLegal(String programName) {
        if (!programName.endsWith(EXE_PROGRAM_NAME)) {
            System.err.println("program name is not legal, programName: " + programName);
            return false;
        }
        return true;
    }
}
