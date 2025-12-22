package com.marks.leetcode.bfs_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_672 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/22 17:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_672 {

    /**
     * @Description:
     * 房间中有 n 只已经打开的灯泡，编号从 1 到 n 。墙上挂着 4 个开关 。
     *
     * 这 4 个开关各自都具有不同的功能，其中：
     *
     * 开关 1 ：反转当前所有灯的状态（即开变为关，关变为开）
     * 开关 2 ：反转编号为偶数的灯的状态（即 0, 2, 4, ...）
     * 开关 3 ：反转编号为奇数的灯的状态（即 1, 3, ...）
     * 开关 4 ：反转编号为 j = 3k + 1 的灯的状态，其中 k = 0, 1, 2, ...（即 1, 4, 7, 10, ...）
     * 你必须 恰好 按压开关 presses 次。每次按压，你都需要从 4 个开关中选出一个来执行按压操作。
     *
     * 给你两个整数 n 和 presses ，执行完所有按压之后，返回 不同可能状态 的数量。
     * tips:
     * 1 <= n <= 1000
     * 0 <= presses <= 1000
     * @param: n
     * @param: presses
     * @return int
     * @author marks
     * @CreateDate: 2025/12/22 17:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int flipLights(int n, int presses) {
        int result;
        result = method_01(n, presses);
        return result;
    }

    /**
     * @Description:
     * 1. 什么情况下才会有不同的状态, 需要整理出 灯泡与各个开关之间的联系
     * 2. 0001 表示开关1, 0010 表示开关2, 0100 表示开关3, 1000 表示开关4
     * 3. 灯泡1: 1101, 灯泡2: 0011
     * need todo
     * @param: n
     * @param: presses
     * @return int
     * @author marks
     * @CreateDate: 2025/12/22 17:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int presses) {
        if (presses == 0) {
            return 1;
        }
        if (n == 1) {
            return 2;
        }
        int[] st = new int[n + 1];
        // 开关1
        Arrays.fill(st, 1);
        // 开关2
        for (int i = 2; i <= n; i += 2) {
            st[i] ^= 2;
        }
        // 开关3
        for (int i = 1; i <= n; i += 2) {
            st[i] ^= 4;
        }
        // 开关4
        for (int i = 1; i <= n; i += 3) {
            st[i] ^= 8;
        }
        int[] ans = new int[n + 1]; //

        return 0;
    }

}
