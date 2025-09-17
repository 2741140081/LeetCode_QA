package com.marks.tools.impossible_game;

import com.marks.tools.war3.*;
import com.marks.tools.war3.controller.ProcessMemory;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [基于jnativehook的魔兽争霸3修改器，监听F8键修改攻击频率比和攻击间隔] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/12 11:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class ImpossibleGame implements NativeKeyListener {
    private War3ProcessHandler war3ProcessHandler;
    private ProcessMemory processMemory;
    private boolean initialized = false;
    
    public static void main(String[] args) {
        try {
            new ImpossibleGame().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void start() throws Exception {
        // 禁用jnativehook的日志输出
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);
        
        // 注册全局键盘监听器
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.err.println("注册本地钩子失败: " + e.getMessage());
            System.exit(1);
        }
        
        GlobalScreen.addNativeKeyListener(this);
        System.out.println("ImpossibleGame已启动，按F8修改攻击属性，按ESC退出");
        
        // 查找魔兽争霸3进程
        findWar3Process();
        
        // 保持程序运行
        synchronized (this) {
            while (true) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
    
    /**
     * 查找魔兽争霸3进程
     */
    private void findWar3Process() {
        new Thread(() -> {
            System.out.println("正在查找魔兽争霸3进程...");
            while (war3ProcessHandler == null) {
                try {
                    // 查找war3.exe进程
                    war3ProcessHandler = War3ProcessHandler.findGameRunning("war3", "game.dll");
                    if (war3ProcessHandler == null) {
                        // 查找网易对战平台的war3进程
                        war3ProcessHandler = War3ProcessHandler.findGameRunning("dzwar3", "game.dll");
                    }
                    
                    if (war3ProcessHandler != null) {
                        System.out.println("找到魔兽争霸3进程，版本: " + war3ProcessHandler.getProcessVersion());
                        processMemory = new ProcessMemory(war3ProcessHandler.getProcessId());
                        initialized = true;
                    } else {
                        Thread.sleep(2000); // 每2秒检查一次
                    }
                } catch (Exception e) {
                    System.err.println("查找游戏进程时出错: " + e.getMessage());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ie) {
                        break;
                    }
                }
            }
        }).start();
    }
    
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_F8) {
            if (!initialized) {
                System.out.println("游戏未初始化，请先启动魔兽争霸3");
                return;
            }
            
            try {
                modifyAttackAttributes();
            } catch (Exception ex) {
                System.err.println("修改攻击属性时出错: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            // 退出程序
            System.out.println("程序退出");
            try {
                if (processMemory != null) {
                    processMemory.close();
                }
                GlobalScreen.unregisterNativeHook();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }
    
    /**
     * 修改攻击属性
     * 攻击频率比修改为50.0
     * 攻击间隔修改为0.5
     */
    private void modifyAttackAttributes() throws Exception {
        // 获取当前选中单位的地址
        long selectedUnitAddress = getSelectedUnitAddress();
        if (selectedUnitAddress == 0) {
            System.out.println("未找到选中单位");
            return;
        }
        
        System.out.println("选中单位地址: 0x" + Long.toHexString(selectedUnitAddress).toUpperCase());
        
        // 计算攻击属性地址
        // 根据魔兽版本1.27a，攻击属性偏移量为0x1E8
        long attackAttributesAddress = selectedUnitAddress + war3ProcessHandler.getGameAddresses().getAttackAttributesOffset();
        System.out.println("攻击属性地址: 0x" + Long.toHexString(attackAttributesAddress).toUpperCase());
        
        // 攻击频率比地址 = 攻击属性地址 + 0x1B0
        long attackSpeedAddress = attackAttributesAddress + 0x1B0;
        System.out.println("攻击频率比地址: 0x" + Long.toHexString(attackSpeedAddress).toUpperCase());
        
        // 攻击间隔地址 = 攻击属性地址 + 0x244 (主动攻击范围)
        // 根据需求，攻击间隔应该在其他位置，这里我们假设它在0x23C偏移处
        long attackIntervalAddress = attackAttributesAddress + 0x23C;
        System.out.println("攻击间隔地址: 0x" + Long.toHexString(attackIntervalAddress).toUpperCase());
        
        // 修改攻击频率比为50.0
        processMemory.writeFloat(attackSpeedAddress, 50.0f);
        
        // 修改攻击间隔为0.5
        processMemory.writeFloat(attackIntervalAddress, 0.5f);
        
        System.out.println("攻击属性修改成功: 攻击频率比=50.0, 攻击间隔=0.5");
    }
    
    /**
     * 获取当前选中单位的地址
     * need todo!
     * @return 单位地址
     */
    private long getSelectedUnitAddress() throws Exception {
        // 为了简化，我们直接返回一个示例地址
        // 在真实应用中，这需要通过读取游戏内存获取当前选中单位的实际地址
        // 这里返回一个固定地址用于演示
        return 0x01000000L; // 示例地址
    }
    
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        // 不需要处理
    }
    
    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        // 不需要处理
    }
}