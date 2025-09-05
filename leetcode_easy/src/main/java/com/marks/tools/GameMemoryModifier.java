package com.marks.tools;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;

/**
 * 游戏内存修改器示例
 * 展示如何使用MemoryReader类读取和修改游戏内存数据
 */
public class GameMemoryModifier {
    
    /**
     * 根据进程名查找进程ID
     * @param processName 进程名（例如："game.exe"）
     * @return 进程ID，如果未找到则返回-1
     */
    public static int findProcessId(String processName) {
        WinNT.HANDLE snapshot = Kernel32.INSTANCE.CreateToolhelp32Snapshot(
            Tlhelp32.TH32CS_SNAPPROCESS, new WinDef.DWORD(0));
        
        Tlhelp32.PROCESSENTRY32.ByReference processEntry = 
            new Tlhelp32.PROCESSENTRY32.ByReference();
        
        try {
            if (Kernel32.INSTANCE.Process32First(snapshot, processEntry)) {
                do {
                    if (processName.equalsIgnoreCase(processEntry.szExeFile.toString())) {
                        return processEntry.th32ProcessID.intValue();
                    }
                } while (Kernel32.INSTANCE.Process32Next(snapshot, processEntry));
            }
        } finally {
            Kernel32.INSTANCE.CloseHandle(snapshot);
        }
        
        return -1;
    }
    
    /**
     * 修改游戏中的分数值示例
     * @param processName 游戏进程名
     * @param baseAddress 分数值在内存中的基地址
     * @param newScore 新的分数值
     */
    public static void modifyGameScore(String processName, long baseAddress, int newScore) {
        // 查找游戏进程ID
        int processId = findProcessId(processName);
        if (processId == -1) {
            System.out.println("未找到进程: " + processName);
            return;
        }
        
        System.out.println("找到进程: " + processName + " (PID: " + processId + ")");
        
        // 创建内存读取器实例
        MemoryReader memoryReader = new MemoryReader(processId);
        
        try {
            // 打开进程
            if (!memoryReader.openProcess()) {
                System.out.println("无法打开进程");
                return;
            }
            
            System.out.println("成功打开进程");
            
            // 读取当前分数
            int currentScore = memoryReader.readInt(baseAddress);
            System.out.println("当前分数: " + currentScore);
            
            // 修改分数
            if (memoryReader.writeInt(baseAddress, newScore)) {
                System.out.println("分数已修改为: " + newScore);
            } else {
                System.out.println("修改分数失败");
            }
            
        } catch (Exception e) {
            System.err.println("操作过程中发生错误: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 关闭进程句柄
            memoryReader.close();
        }
    }
    
    /**
     * 主方法 - 使用示例
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 示例：修改记事本中的某个内存地址的值
        // 注意：您需要根据实际游戏和需要修改的值来调整这些参数
        String processName = "notepad.exe";  // 实际使用时替换为游戏进程名
        long baseAddress = 0x12345678;       // 实际使用时替换为正确的内存地址
        int newScore = 999999;               // 实际使用时替换为想要设置的值
        
        System.out.println("游戏内存修改器示例");
        System.out.println("注意：此示例仅用于演示目的，实际使用时需要根据具体游戏调整参数");
        
        // 修改游戏分数
        modifyGameScore(processName, baseAddress, newScore);
    }
}