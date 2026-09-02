package com.marks.leetcode.array_medium;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: RouterTest </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/31 16:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class RouterTest {

    @Test
    void addPacket() {
        int memoryLimit = 3;
        Router router = new Router(memoryLimit);
        // [1,4,90],[2,5,90],[1,4,90],[3,5,95],[4,5,105]
        router.addPacket(1, 4, 90);
        router.addPacket(2, 5, 90);
        router.addPacket(1, 4, 90);
        router.addPacket(3, 5, 95);
        router.addPacket(4, 5, 105);
        int[] packet = router.forwardPacket();
        System.out.println(Arrays.toString(packet));
        // [5,2,110]
        router.addPacket(5,2,110);
        // [5,100,110]
        int count = router.getCount(5, 100, 110);
        System.out.println(count);
    }
}