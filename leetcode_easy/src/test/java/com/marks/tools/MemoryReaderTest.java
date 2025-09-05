package com.marks.tools;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * MemoryReader类的单元测试
 */
public class MemoryReaderTest {
    
    @Test
    public void testMemoryReaderInitialization() {
        // 测试构造函数
        MemoryReader memoryReader = new MemoryReader(1234);
        assertEquals(1234, memoryReader.getProcessId(), "进程ID应该正确设置");
    }
    
    @Test
    public void testFindProcessId() {
        // 测试查找进程ID功能（使用一个常见的系统进程）
        int processId = GameMemoryModifier.findProcessId("explorer.exe");
        // explorer.exe通常会运行，所以应该能找到进程ID
        assertTrue(processId > 0, "应该能找到explorer.exe进程");
    }
    
    @Test
    public void testFindNonExistentProcess() {
        // 测试查找不存在的进程
        int processId = GameMemoryModifier.findProcessId("NonExistentProcess12345.exe");
        assertEquals(-1, processId, "不应该找到不存在的进程");
    }
}