package com.marks.tools.war3;

import com.marks.tools.war3.entity.GameAddresses;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.*;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinNT.HANDLE;

import java.util.HashMap;
import java.util.Map;

/**
 * 魔兽争霸3进程处理类，用于处理游戏进程相关信息
 * 实现类似C#版本GameContext的功能
 */
public class War3ProcessHandler {
    private int processId;
    private String processVersion;
    private long moduleAddress;
    private GameAddresses gameAddresses;

    // 版本到地址偏移量的映射
    private static final Map<String, long[]> ADDRESS_OFFSETS = new HashMap<>();
    private static final Map<String, long[]> ATTRIBUTE_OFFSETS = new HashMap<>();

    static {
        // 初始化地址偏移量映射
        ADDRESS_OFFSETS.put("1.20.4.6074", new long[]{0x87C744, 0x8722BC, 0x55BDF0});
        ADDRESS_OFFSETS.put("1.21.0.6263", new long[]{0x87D7BC, 0x873334, 0x55FE80});
        ADDRESS_OFFSETS.put("1.21.1.6300", new long[]{0x87D7BC, 0x873334, 0x55fEA0});
        ADDRESS_OFFSETS.put("1.22.0.6328", new long[]{0xAA4178, 0xAA2FFC, 0x201190});
        ADDRESS_OFFSETS.put("1.23.0.6352", new long[]{0xABCFC8, 0xABBE4C, 0x2026D0});
        ADDRESS_OFFSETS.put("1.24.0.6372", new long[]{0xACE5E0, 0xACD44C, 0x202780});
        ADDRESS_OFFSETS.put("1.24.1.6374", new long[]{0xACE5E0, 0xACD44C, 0x202780});
        ADDRESS_OFFSETS.put("1.24.2.6378", new long[]{0xACE5E0, 0xACD44C, 0x202780});
        ADDRESS_OFFSETS.put("1.24.3.6384", new long[]{0xACE5E0, 0xACD44C, 0x2027E0});
        ADDRESS_OFFSETS.put("1.24.4.6387", new long[]{0xACE5E0, 0xACD44C, 0x2027E0});
        ADDRESS_OFFSETS.put("1.25.1.6397", new long[]{0xAB7788, 0xAB65F4, 0x201AA0});
        ADDRESS_OFFSETS.put("1.26.0.6401", new long[]{0xAB7788, 0xAB65F4, 0x201CD0});
        ADDRESS_OFFSETS.put("1.27.0.52240", new long[]{0xBE40A8, 0xBE4238, 0x5DF420});
        ADDRESS_OFFSETS.put("1.28.0.7205", new long[]{0xD72F58, 0xD730F0, 0x604470});
        ADDRESS_OFFSETS.put("1.28.5.7680", new long[]{0xD30448, 0xD305E0, 0x630C70});

        // 初始化属性偏移量映射
        ATTRIBUTE_OFFSETS.put("1.20.4.6074", new long[]{0x1E4, 0x1EC, 0x1F4, 0x1D8});
        ATTRIBUTE_OFFSETS.put("1.21.0.6263", new long[]{0x1E4, 0x1EC, 0x1F4, 0x1D8});
        ATTRIBUTE_OFFSETS.put("1.21.1.6300", new long[]{0x1E4, 0x1EC, 0x1F4, 0x1D8});
        ATTRIBUTE_OFFSETS.put("1.22.0.6328", new long[]{0x1E4, 0x1EC, 0x1F4, 0x1D8});
        ATTRIBUTE_OFFSETS.put("1.23.0.6352", new long[]{0x1E4, 0x1EC, 0x1F4, 0x1D8});
        ATTRIBUTE_OFFSETS.put("1.24.0.6372", new long[]{0x1E4, 0x1EC, 0x1F4, 0x1D8});
        ATTRIBUTE_OFFSETS.put("1.24.1.6374", new long[]{0x1E4, 0x1EC, 0x1F4, 0x1D8});
        ATTRIBUTE_OFFSETS.put("1.24.2.6378", new long[]{0x1E4, 0x1EC, 0x1F4, 0x1D8});
        ATTRIBUTE_OFFSETS.put("1.24.3.6384", new long[]{0x1E8, 0x1F0, 0x1F8, 0x1DC});
        ATTRIBUTE_OFFSETS.put("1.24.4.6387", new long[]{0x1E8, 0x1F0, 0x1F8, 0x1DC});
        ATTRIBUTE_OFFSETS.put("1.25.1.6397", new long[]{0x1E8, 0x1F0, 0x1F8, 0x1DC});
        ATTRIBUTE_OFFSETS.put("1.26.0.6401", new long[]{0x1E8, 0x1F0, 0x1F8, 0x1DC});
        ATTRIBUTE_OFFSETS.put("1.27.0.52240", new long[]{0x1E8, 0x1F0, 0x1F8, 0x1DC});
        ATTRIBUTE_OFFSETS.put("1.28.0.7205", new long[]{0x1E8, 0x1F0, 0x1F8, 0x1DC});
        ATTRIBUTE_OFFSETS.put("1.28.5.7680", new long[]{0x1E8, 0x1F0, 0x1F8, 0x1DC});
    }

    /**
     * 查找正在运行的游戏进程
     *
     * @param processName 进程名称
     * @param moduleName  模块名称
     * @return War3ProcessHandler 游戏进程处理器实例，如果未找到则返回null
     */
    public static War3ProcessHandler findGameRunning(String processName, String moduleName) {
        // 获取所有匹配名称的进程
        HANDLE snapshot = Kernel32.INSTANCE.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPPROCESS, new DWORD(0));
        Tlhelp32.PROCESSENTRY32.ByReference entry = new Tlhelp32.PROCESSENTRY32.ByReference();
        entry.dwSize = new DWORD(entry.size());

        if (!Kernel32.INSTANCE.Process32First(snapshot, entry)) {
            Kernel32.INSTANCE.CloseHandle(snapshot);
            return null;
        }

        do {
            char[] nameChars = entry.szExeFile;
            String name = new String(nameChars).trim();
            // 处理C#字符串比较逻辑，忽略大小写并去除空字符
            int nullIndex = name.indexOf('\0');
            if (nullIndex != -1) {
                name = name.substring(0, nullIndex);
            }
            
            if (name.equalsIgnoreCase(processName + ".exe")) {
                Kernel32.INSTANCE.CloseHandle(snapshot);
                return new War3ProcessHandler(entry.th32ProcessID.intValue(), moduleName);
            }
        } while (Kernel32.INSTANCE.Process32Next(snapshot, entry));

        Kernel32.INSTANCE.CloseHandle(snapshot);
        return null;
    }

    /**
     * 构造函数，初始化游戏进程信息
     *
     * @param pid         进程ID
     * @param moduleName  模块名称
     */
    public War3ProcessHandler(int pid, String moduleName) {
        this.processId = pid;
        this.gameAddresses = new GameAddresses();

        // 获取模块信息
        getModuleInfo(moduleName);

        // 根据版本确定地址
        getGameAddress();

        // 根据版本确定偏移量
        getGameOffset();
    }

    /**
     * 获取模块信息
     *
     * @param moduleName 模块名称
     */
    public void getModuleInfo(String moduleName) {
        // 使用JNA获取模块信息
        Kernel32 kernel32 = Kernel32.INSTANCE;
        WinNT.HANDLE snapshot = kernel32.CreateToolhelp32Snapshot(
                Tlhelp32.TH32CS_SNAPMODULE, new WinDef.DWORD(this.processId));

        if (snapshot == null || snapshot == WinNT.INVALID_HANDLE_VALUE) {
            throw new RuntimeException("Failed to create snapshot");
        }

        try {
            Tlhelp32.MODULEENTRY32W moduleEntry = new Tlhelp32.MODULEENTRY32W();
            moduleEntry.dwSize = new WinDef.DWORD(moduleEntry.size());

            if (kernel32.Module32FirstW(snapshot, moduleEntry)) {
                do {
                    String currentModuleName = Native.toString(moduleEntry.szModule);
                    if (currentModuleName.equalsIgnoreCase(moduleName)) {
                        // 获取文件版本信息
                        String modulePath = Native.toString(moduleEntry.szExePath);
                        String version = getFileVersion(modulePath);

                        // 保存信息
                        this.processVersion = version.replace(", ", ".");
                        this.moduleAddress = Pointer.nativeValue(moduleEntry.modBaseAddr);
                        return;
                    }
                } while (kernel32.Module32NextW(snapshot, moduleEntry));
            }
            throw new RuntimeException("Module not found: " + moduleName);
        } finally {
            kernel32.CloseHandle(snapshot);
        }
    }

    private String getFileVersion(String filePath) {
        // 在实际应用中，这里应该解析PE文件的版本信息
        // 直接hard code, 反正也是在本地运行代码, 直接用自己的版本
        return "1.27.0.52240";
    }

    /**
     * 根据版本确定游戏地址
     */
    private void getGameAddress() {
        long[] offsets = ADDRESS_OFFSETS.get(processVersion);
        if (offsets == null) {
            throw new UnknownGameVersionException(processId, processVersion);
        }

        gameAddresses.setThisGameAddress(moduleAddress + offsets[0]);
        gameAddresses.setUnitListAddress(moduleAddress + offsets[1]);
        gameAddresses.setMoveSpeedAddress(moduleAddress + offsets[2]);
    }

    /**
     * 根据版本确定游戏偏移量
     */
    private void getGameOffset() {
        long[] offsets = ATTRIBUTE_OFFSETS.get(processVersion);
        if (offsets == null) {
            throw new UnknownGameVersionException(processId, processVersion);
        }

        gameAddresses.setAttackAttributesOffset(offsets[0]);
        gameAddresses.setHeroAttributesOffset(offsets[1]);
        gameAddresses.setItemsListOffset(offsets[2]);
        gameAddresses.setMoveSpeedOffset(offsets[3]);
    }

    // Getters
    public int getProcessId() {
        return processId;
    }

    public String getProcessVersion() {
        return processVersion;
    }

    public long getThisGameAddress() {
        return gameAddresses.getThisGameAddress();
    }

    public long getUnitListAddress() {
        return gameAddresses.getUnitListAddress();
    }

    public long getMoveSpeedAddress() {
        return gameAddresses.getMoveSpeedAddress();
    }

    public long getAttackAttributesOffset() {
        return gameAddresses.getAttackAttributesOffset();
    }

    public long getHeroAttributesOffset() {
        return gameAddresses.getHeroAttributesOffset();
    }

    public long getItemsListOffset() {
        return gameAddresses.getItemsListOffset();
    }

    public long getMoveSpeedOffset() {
        return gameAddresses.getMoveSpeedOffset();
    }
    
    public GameAddresses getGameAddresses() {
        return gameAddresses;
    }
}