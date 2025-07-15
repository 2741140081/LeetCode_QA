package com.marks.jvm.capter_4;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/9 16:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class JConsoleCase {

    /**
     * @Description: 内存占位符对象, 一个OOMObject 对象占 64 KB
     * @return
     * @author marks
     * @CreateDate: 2025/7/9 16:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    static class OOMObject {
        public byte[] placeholder = new byte[64 * 1024];
    }

    public static void fillHeap(int num) throws InterruptedException {
        List<OOMObject> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            // 稍作延时, 使得监控曲线更明显
            Thread.sleep(50);
            list.add(new OOMObject());
        }
        System.gc();
    }

    public static void main(String[] args) throws InterruptedException {
        fillHeap(1000);
    }
}
