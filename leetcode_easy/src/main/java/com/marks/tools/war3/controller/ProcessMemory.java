package com.marks.tools.war3.controller;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.win32.W32APIOptions;

/**
 * 进程内存操作类，用于读取和写入Windows进程内存
 * 对应C#中的WindowsApi.ProcessMemory类
 */
public class ProcessMemory implements AutoCloseable {
    // 定义Kernel32接口
    interface Kernel32 extends com.sun.jna.Library {
        Kernel32 INSTANCE = com.sun.jna.Native.load("kernel32", Kernel32.class, W32APIOptions.DEFAULT_OPTIONS);

        // 打开进程
        WinNT.HANDLE OpenProcess(int dwDesiredAccess, boolean bInheritHandle, int dwProcessId);

        // 读取进程内存
        boolean ReadProcessMemory(WinNT.HANDLE hProcess, Pointer lpBaseAddress,
                                  Pointer lpBuffer, int nSize, WinDef.ULONGByReference lpNumberOfBytesRead);

        // 写入进程内存
        boolean WriteProcessMemory(WinNT.HANDLE hProcess, Pointer lpBaseAddress,
                                   Pointer lpBuffer, int nSize, WinDef.ULONGByReference lpNumberOfBytesWritten);

        // 关闭句柄
        boolean CloseHandle(WinNT.HANDLE hObject);
    }

    // 进程访问权限常量
    private static final int PROCESS_VM_READ = 0x0010;
    private static final int PROCESS_VM_WRITE = 0x0020;
    private static final int PROCESS_VM_OPERATION = 0x0008;

    private WinNT.HANDLE processHandle;
    private int processId;

    /**
     * 构造函数，打开指定进程
     * @param processId 进程ID
     * @throws Exception 如果无法打开进程
     */
    public ProcessMemory(int processId) throws Exception {
        this.processId = processId;
        // 打开进程，获取读写权限
        this.processHandle = Kernel32.INSTANCE.OpenProcess(
                PROCESS_VM_READ | PROCESS_VM_WRITE | PROCESS_VM_OPERATION,
                false, processId);

        if (processHandle == null) {
            throw new Exception("无法打开进程，PID: " + processId);
        }
    }

    /**
     * 读取32位整数
     * @param address 内存地址
     * @return 读取的整数值
     * @throws Exception 读取失败时抛出异常
     */
    public int readInt32(long address) throws Exception {
        Memory buffer = new Memory(4); // 4 bytes for int32
        WinDef.ULONGByReference bytesRead = new WinDef.ULONGByReference();

        boolean result = Kernel32.INSTANCE.ReadProcessMemory(
                processHandle, new Pointer(address), buffer, 4, bytesRead);

        if (!result || bytesRead.getValue().intValue() != 4) {
            throw new Exception("读取内存失败，地址: 0x" + Long.toHexString(address));
        }

        return buffer.getInt(0);
    }

    /**
     * 读取浮点数
     * @param address 内存地址
     * @return 读取的浮点数值
     * @throws Exception 读取失败时抛出异常
     */
    public float readFloat(long address) throws Exception {
        Memory buffer = new Memory(4); // 4 bytes for float
        WinDef.ULONGByReference bytesRead = new WinDef.ULONGByReference();

        boolean result = Kernel32.INSTANCE.ReadProcessMemory(
                processHandle, new Pointer(address), buffer, 4, bytesRead);

        if (!result || bytesRead.getValue().intValue() != 4) {
            throw new Exception("读取内存失败，地址: 0x" + Long.toHexString(address));
        }

        return buffer.getFloat(0);
    }

    /**
     * 读取4字符字符串
     * @param address 内存地址
     * @return 读取的字符串
     * @throws Exception 读取失败时抛出异常
     */
    public String readChar4(long address) throws Exception {
        Memory buffer = new Memory(4); // 4 bytes for 4 characters
        WinDef.ULONGByReference bytesRead = new WinDef.ULONGByReference();

        boolean result = Kernel32.INSTANCE.ReadProcessMemory(
                processHandle, new Pointer(address), buffer, 4, bytesRead);

        if (!result || bytesRead.getValue().intValue() != 4) {
            throw new Exception("读取内存失败，地址: 0x" + Long.toHexString(address));
        }

        byte[] bytes = buffer.getByteArray(0, 4);
        return new String(bytes).trim(); // 移除可能的空字符
    }

    /**
     * 写入32位整数
     * @param address 内存地址
     * @param value 要写入的值
     * @throws Exception 写入失败时抛出异常
     */
    public void writeInt32(long address, int value) throws Exception {
        Memory buffer = new Memory(4);
        buffer.setInt(0, value);
        WinDef.ULONGByReference bytesWritten = new WinDef.ULONGByReference();

        boolean result = Kernel32.INSTANCE.WriteProcessMemory(
                processHandle, new Pointer(address), buffer, 4, bytesWritten);

        if (!result || bytesWritten.getValue().intValue() != 4) {
            throw new Exception("写入内存失败，地址: 0x" + Long.toHexString(address));
        }
    }

    /**
     * 写入浮点数
     * @param address 内存地址
     * @param value 要写入的值
     * @throws Exception 写入失败时抛出异常
     */
    public void writeFloat(long address, float value) throws Exception {
        Memory buffer = new Memory(4);
        buffer.setFloat(0, value);
        WinDef.ULONGByReference bytesWritten = new WinDef.ULONGByReference();

        boolean result = Kernel32.INSTANCE.WriteProcessMemory(
                processHandle, new Pointer(address), buffer, 4, bytesWritten);

        if (!result || bytesWritten.getValue().intValue() != 4) {
            throw new Exception("写入内存失败，地址: 0x" + Long.toHexString(address));
        }
    }

    /**
     * 写入4字符字符串
     * @param address 内存地址
     * @param value 要写入的字符串（最多4个字符）
     * @throws Exception 写入失败时抛出异常
     */
    public void writeChar4(long address, String value) throws Exception {
        // 确保字符串长度不超过4个字符
        if (value.length() > 4) {
            value = value.substring(0, 4);
        }
        
        // 转换为字节数组并确保长度为4
        byte[] bytes = value.getBytes();
        byte[] finalBytes = new byte[4];
        System.arraycopy(bytes, 0, finalBytes, 0, Math.min(bytes.length, 4));
        
        Memory buffer = new Memory(4);
        buffer.write(0, finalBytes, 0, 4);
        WinDef.ULONGByReference bytesWritten = new WinDef.ULONGByReference();

        boolean result = Kernel32.INSTANCE.WriteProcessMemory(
                processHandle, new Pointer(address), buffer, 4, bytesWritten);

        if (!result || bytesWritten.getValue().intValue() != 4) {
            throw new Exception("写入内存失败，地址: 0x" + Long.toHexString(address));
        }
    }

    /**
     * 关闭进程句柄，释放资源
     */
    @Override
    public void close() {
        if (processHandle != null) {
            Kernel32.INSTANCE.CloseHandle(processHandle);
        }
    }
}