package com.marks.tools.start;

import java.io.File;
import java.io.IOException;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: BOMStart </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/19 11:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class BOMStart {

    public static void main(String[] args) {
        String pcPath = "C:\\pvcs\\working\\BOM\\Programs\\Middleware\\BOMR3\\BOM\\BOM_PC_DOMAIN\\startWebLogic.cmd";
        String eaiPath = "C:\\pvcs\\working\\BOM\\Programs\\Middleware\\BOMR3\\BOM\\BOM_EAI_DOMAIN\\startWebLogic.cmd";
        executeBatPC(pcPath);
        // EAI http:\\localhost:7001\console
        // PC http:\\localhost:7101\console
        boolean urlPcValid = false;
        while (!urlPcValid) {
            urlPcValid = URLValidator.isUrlValid("http://localhost:7101/console");
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("BOM PC Start Successfully");
        executeBatEAI(eaiPath);
        boolean urlEaiValid = false;
        while (!urlEaiValid) {
            urlEaiValid = URLValidator.isUrlValid("http://localhost:7001/console");
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("BOM EAI Start Successfully");
    }

    public static void executeBatEAI(String batPath) {
        File batFile = new File(batPath);
        boolean batFileExist = batFile.exists();
        System.out.println("bat文件是否存在: " + batFileExist);

        if (!batFileExist) {
            System.out.println("错误：bat文件不存在！");
            return;
        }

        StringBuilder output = new StringBuilder();
        try {
            // 执行bat脚本
            Runtime.getRuntime().exec("cmd.exe /C start " + batPath);
        } catch (IOException e) {
            System.out.println("IO异常: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void executeBatPC(String batPath) {
        File batFile = new File(batPath);
        boolean batFileExist = batFile.exists();
        System.out.println("bat文件是否存在: " + batFileExist);

        if (!batFileExist) {
            System.out.println("错误：bat文件不存在！");
            return;
        }

        StringBuilder output = new StringBuilder();
        try {
            // 执行bat脚本
            Runtime.getRuntime().exec("cmd.exe /C start " + batPath);
        } catch (IOException e) {
            System.out.println("IO异常: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
