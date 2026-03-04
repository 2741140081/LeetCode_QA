package com.marks.leetcode.DP;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1387 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/3 16:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1387 {

    /**
     * @Description:
     * 我们将整数 x 的 权重 定义为按照下述规则将 x 变成 1 所需要的步数：
     * 如果 x 是偶数，那么 x = x / 2
     * 如果 x 是奇数，那么 x = 3 * x + 1
     * 比方说，x=3 的权重为 7 。因为 3 需要 7 步变成 1 （3 --> 10 --> 5 --> 16 --> 8 --> 4 --> 2 --> 1）。
     *
     * 给你三个整数 lo， hi 和 k 。你的任务是将区间 [lo, hi] 之间的整数按照它们的权重 升序排序 ，如果大于等于 2 个整数有 相同 的权重，那么按照数字自身的数值 升序排序 。
     *
     * 请你返回区间 [lo, hi] 之间的整数按权重排序后的第 k 个数。
     *
     * 注意，题目保证对于任意整数 x （lo <= x <= hi） ，它变成 1 所需要的步数是一个 32 位有符号整数。
     * tips:
     * 1 <= lo <= hi <= 1000
     * 1 <= k <= hi - lo + 1
     * @param: lo
     * @param: hi
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/03/03 16:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    public int getKth(int lo, int hi, int k) {
        int result;
        result = method_01(lo, hi, k);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：lo = 7, hi = 11, k = 4
     * 输出：7
     * 1. 如何计算 x 的权值? 使用动态规划? 每个x 的权重都是确定的吧
     * 2. 计算权值的过程中是否可以用记忆化?
     * 3. 由于范围难以确定, 所以使用 map 存储比较方便, 初始存储 map.put(1, 0)
     * AC: 52ms/46.01MB
     * @param: lo
     * @param: hi
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/03/03 16:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int lo, int hi, int k) {
        int n = hi - lo + 1;
        int[][] arr = new int[n][2]; // 创建一个二维数组，用于存储每个数字的权重和数字本身
        // 构建 Map
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 0);
        for (int i = 0; i < n; i++) {
            arr[i][0] = getWeight(lo + i, map);
            arr[i][1] = lo + i;
        }
        // 对arr 进行排序
        Arrays.sort(arr, (a, b) -> {
            if (a[0] != b[0]) {
                return a[0] - b[0];
            } else {
                return a[1] - b[1];
            }
        });
        return arr[k - 1][1];
    }

    private int getWeight(int num, Map<Integer, Integer> map) {
        if (map.containsKey(num)) {
            return map.get(num);
        }
        int weight;
        // 判断奇偶性
        if (num % 2 == 0) {
            // 偶数
            weight = 1 + getWeight(num / 2, map);
        } else {
            // 奇数
            weight = 1 + getWeight(num * 3 + 1, map);
        }
        map.put(num, weight);
        return weight;
    }

}
