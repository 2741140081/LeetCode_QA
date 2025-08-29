package com.marks.leetcode.backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/25 14:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3376 {


    /**
     * @Description:
     * Bob 被困在了一个地窖里，他需要破解 n 个锁才能逃出地窖，每一个锁都需要一定的 能量 才能打开。
     * 每一个锁需要的能量存放在一个数组 strength 里，其中 strength[i] 表示打开第 i 个锁需要的能量。
     *
     * Bob 有一把剑，它具备以下的特征：
     *
     * 一开始剑的能量为 0 。
     * 剑的能量增加因子 X 一开始的值为 1 。
     * 每分钟，剑的能量都会增加当前的 X 值。
     * 打开第 i 把锁，剑的能量需要到达 至少 strength[i] 。
     * 打开一把锁以后，剑的能量会变回 0 ，X 的值会增加一个给定的值 K 。
     * 你的任务是打开所有 n 把锁并逃出地窖，请你求出需要的 最少 分钟数。
     *
     * 请你返回 Bob 打开所有 n 把锁需要的 最少 时间。
     *
     * tips:
     * n == strength.length
     * 1 <= n <= 8
     * 1 <= K <= 10
     * 1 <= strength[i] <= 10^6
     * @param strength
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/8/25 14:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findMinimumTime(List<Integer> strength, int k) {
        int result;
        result = method_01(strength, k);
        return result;
    }


    /**
     * @Description:
     * E1:
     * 输入：strength = [3,4,1], K = 1
     * 输出：4
     * 1. 对于锁而言, 不需要按照 s[i] 的顺序来处理, 完全可以进行排序后再去处理, 即先处理 strength[i] 最小的锁
     * 2. 我们要找到最小所需天数, 初始化设置一个最大值, index = 0,
     * 3. 存在问题, 针对上面的案例E1, 如果是按照顺序打开所有的锁,那么需要5分钟, 但是最短仅需要4分钟,
     * 4. 需要通过回溯判断当前是否是最佳结果, 遍历整个s[i], 当前选择s[i] 进行打开
     * AC: 1483ms(5.05%)/44.05MB(8.08%)
     * @param strength
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/8/25 14:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int ans; // 所需的最小时间
    private int k;
    private int method_01(List<Integer> strength, int k) {
        ans = Integer.MAX_VALUE;
        this.k = k;
        int x = 1;
        backtrack(strength, x, 0);
        return ans;
    }

    private void backtrack(List<Integer> strength, int x, int day) {
        if (strength.isEmpty()) {
            ans = Math.min(ans, day);
            return;
        }
        for (int i = 0; i < strength.size(); i++) {
            // 拷贝当前strength 列表
            List<Integer> copy = new ArrayList<>(strength);

            int daysNeeded = (strength.get(i) + x - 1) / x; // 向上取整, 获取需要的天数

            copy.remove(i); // 删除当前 strength[i], 表示当前锁已经打开
            backtrack(copy, x + k, day + daysNeeded);
        }
    }

}