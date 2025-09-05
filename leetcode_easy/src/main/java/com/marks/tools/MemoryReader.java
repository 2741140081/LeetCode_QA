package com.marks.tools;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.W32APIOptions;

/**
 * Windows内存读取和修改工具类
 * 用于读取和修改Windows游戏中内存数据
 */
public class MemoryReader {
    
    /**
     * Kernel32 DLL接口定义
     */
    public interface Kernel32 extends Library {
        Kernel32 INSTANCE = Native.load("kernel32", Kernel32.class, W32APIOptions.DEFAULT_OPTIONS);
        
        WinNT.HANDLE OpenProcess(int dwDesiredAccess, boolean bInheritHandle, int dwProcessId);
        boolean ReadProcessMemory(WinNT.HANDLE hProcess, long lpBaseAddress, byte[] lpBuffer, int nSize, IntByReference lpNumberOfBytesRead);
        boolean WriteProcessMemory(WinNT.HANDLE hProcess, long lpBaseAddress, byte[] lpBuffer, int nSize, IntByReference lpNumberOfBytesWritten);
        boolean CloseHandle(WinNT.HANDLE hObject);
    }
    
    // 进程访问权限常量
    private static final int PROCESS_VM_READ = 0x0010;
    private static final int PROCESS_VM_WRITE = 0x0020;
    private static final int PROCESS_VM_OPERATION = 0x0008;
    private static final int PROCESS_ALL_ACCESS = 0x1F0FFF;
    
    private WinNT.HANDLE processHandle;
    private int processId;
    
    /**
     * 构造函数
     * @param processId 目标进程ID
     */
    public MemoryReader(int processId) {
        this.processId = processId;
        this.processHandle = null;
    }
    
    /**
     * 打开目标进程
     * @return 是否成功打开进程
     */
    public boolean openProcess() {
        processHandle = Kernel32.INSTANCE.OpenProcess(PROCESS_ALL_ACCESS, false, processId);
        return processHandle != null;
    }
    
    /**
     * 读取指定地址的整数值
     * @param address 内存地址
     * @return 读取到的整数值
     */
    public int readInt(long address) {
        byte[] buffer = new byte[4];
        IntByReference bytesRead = new IntByReference(0);
        
        boolean success = Kernel32.INSTANCE.ReadProcessMemory(
            processHandle, address, buffer, buffer.length, bytesRead);
            
        if (success && bytesRead.getValue() == 4) {
            // 将字节数组转换为整数 (Little Endian)
            return (buffer[0] & 0xFF) | 
                   ((buffer[1] & 0xFF) << 8) | 
                   ((buffer[2] & 0xFF) << 16) | 
                   ((buffer[3] & 0xFF) << 24);
        }
        
        return 0;
    }
    
    /**
     * 写入整数值到指定地址
     * @param address 内存地址
     * @param value 要写入的整数值
     * @return 是否写入成功
     */
    public boolean writeInt(long address, int value) {
        // 将整数转换为字节数组 (Little Endian)
        byte[] buffer = new byte[4];
        buffer[0] = (byte) (value & 0xFF);
        buffer[1] = (byte) ((value >> 8) & 0xFF);
        buffer[2] = (byte) ((value >> 16) & 0xFF);
        buffer[3] = (byte) ((value >> 24) & 0xFF);
        
        IntByReference bytesWritten = new IntByReference(0);
        
        return Kernel32.INSTANCE.WriteProcessMemory(
            processHandle, address, buffer, buffer.length, bytesWritten);
    }
    
    /**
     * 读取指定地址的字节数组
     * @param address 内存地址
     * @param size 读取大小
     * @return 读取到的字节数组
     */
    public byte[] readBytes(long address, int size) {
        byte[] buffer = new byte[size];
        IntByReference bytesRead = new IntByReference(0);
        
        boolean success = Kernel32.INSTANCE.ReadProcessMemory(
            processHandle, address, buffer, size, bytesRead);
            
        if (success && bytesRead.getValue() == size) {
            return buffer;
        }
        
        return new byte[0];
    }
    
    /**
     * 写入字节数组到指定地址
     * @param address 内存地址
     * @param data 要写入的字节数组
     * @return 是否写入成功
     */
    public boolean writeBytes(long address, byte[] data) {
        IntByReference bytesWritten = new IntByReference(0);
        
        return Kernel32.INSTANCE.WriteProcessMemory(
            processHandle, address, data, data.length, bytesWritten);
    }
    
    /**
     * 关闭进程句柄
     */
    public void close() {
        if (processHandle != null) {
            Kernel32.INSTANCE.CloseHandle(processHandle);
            processHandle = null;
        }
    }
    
    /**
     * 获取进程ID
     * @return 进程ID
     */
    public int getProcessId() {
        return processId;
    }
}