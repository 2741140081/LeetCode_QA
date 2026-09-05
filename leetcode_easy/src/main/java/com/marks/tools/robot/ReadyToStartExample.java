package com.marks.tools.robot;

/**
 * @Description: ReadyToStart使用示例
 * 展示如何使用ReadyToStart类的各种功能
 * @author marks
 * @CreateDate: 2026/2/28 22:20
 */
public class ReadyToStartExample {
    
    public static void main(String[] args) {
        System.out.println("=== ReadyToStart 使用示例 ===");
        
        // 创建实例
        ReadyToStart readyToStart = new ReadyToStart();
        
        try {
            // 1. 注册全局钩子（包括关闭钩子）
            System.out.println("1. 注册全局钩子...");
            readyToStart.registerGlobalHooks();
            
            // 2. 程序已准备好，等待用户使用快捷键
            System.out.println("2. 程序已就绪，等待快捷键操作...");
            System.out.println("请按 F8 启动自动化任务，按 F9 暂停任务");
            
            System.out.println("自动化任务已启动！");
            System.out.println("程序将每隔3秒执行：按下'1'键 -> 等待1秒 -> 点击鼠标左键");
            System.out.println("快捷键说明：");
            System.out.println("  F8 - 启动自动化任务");
            System.out.println("  F9 - 暂停自动化任务");
            System.out.println("  Ctrl+C - 退出程序");
            
            // 3. 演示程序运行一段时间
            Thread.sleep(15000); // 运行15秒
            
            System.out.println("15秒后，停止自动化任务...");
            
            // 4. 停止自动化任务
            readyToStart.stopAutomation();
            System.out.println("自动化任务已停止");
            
            // 5. 检查运行状态
            System.out.println("当前运行状态: " + (readyToStart.isRunning() ? "运行中" : "已停止"));
            
        } catch (InterruptedException e) {
            System.out.println("程序被中断");
            Thread.currentThread().interrupt();
        } finally {
            // 6. 清理资源
            System.out.println("清理资源...");
            readyToStart.stopAutomation();
            readyToStart.removeShutdownHook();
            System.out.println("程序结束");
        }
    }
}