package com.marks.leetcode.bfs_algorithm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
        result = method_02(n, presses);
        return result;
    }

    private int method_02(int n, int presses) {
        Set<Integer> seen = new HashSet<Integer>();
        for (int i = 0; i < 1 << 4; i++) {
            int[] pressArr = new int[4];
            for (int j = 0; j < 4; j++) {
                pressArr[j] = (i >> j) & 1;
            }
            int sum = Arrays.stream(pressArr).sum();
            if (sum % 2 == presses % 2 && sum <= presses) {
                int status = pressArr[0] ^ pressArr[2] ^ pressArr[3];
                if (n >= 2) {
                    status |= (pressArr[0] ^ pressArr[1]) << 1;
                }
                if (n >= 3) {
                    status |= (pressArr[0] ^ pressArr[2]) << 2;
                }
                if (n >= 4) {
                    status |= (pressArr[0] ^ pressArr[1] ^ pressArr[3]) << 3;
                }
                seen.add(status);
            }
        }
        return seen.size();
    }

    /**
     * @Description:
     * 1. 什么情况下才会有不同的状态, 需要整理出 灯泡与各个开关之间的联系
     * [1,1,1,1,1] => 执行开关4
     * [0,1,1,0,1] => 执行开关3
     * [0,0,1,1,1] => 执行开关4
     * [1,0,1,0,1] => 执行开关3
     * [1,1,1,1,1] => 执行k2
     * [0,1,0,1,0] => 执行k4
     * [0,0,0,0,0] => 执行k3
     * [0,1,1,0,1] => 执行k4
     * [1,1,1,1,1]
     * 当执行k2,k3,k4,k1时, 状态会还原为初始状态, 可以进行抵消操作
     * 1. 假设开关1执行了k1次, 开关2执行了k2次, 开关3执行了k3次, 开关4执行了k4次, k1 + k2 + k3 + k4 = presses
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
        for (int k1 = presses; k1 >= 0; k1--) {
            for (int k2 = presses - k1; k2 >= 0; k2--) {
                for (int k3 = presses - k1 - k2; k3 >= 0; k3--) {
                    for (int k4 = presses - k1 - k2 - k3; k4 >= 0; k4--) {

                    }
                }
            }
        }

        return 0;
    }

}
