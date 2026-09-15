package com.marks.leetcode.array_medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3752 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/15 16:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3752 {

    /**
     * @Description:
     * 给你一个正整数 n 和一个整数 target。
     * 请返回一个大小为 n 的 字典序最小 的整数数组，并满足：
     * 其元素 和 等于 target。
     * 其元素的 绝对值 组成一个大小为 n 的 排列。
     * 如果不存在这样的数组，则返回一个空数组。
     * 如果数组 a 和 b 在第一个不同的位置上，数组 a 的元素小于 b 的对应元素，
     * 则认为数组 a 字典序小于 数组 b。
     * 大小为 n 的 排列 是对整数 1, 2, ..., n 的重新排列。
     * tips:
     * 1 <= n <= 10^5
     * -10^10 <= target <= 10^10
     * @param: n
     * @param: target
     * @return int[]
     * @author marks
     * @CreateDate: 2026/09/15 16:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] lexSmallestNegatedPerm(int n, long target) {
        int[] result;
        result = method_01(n, target);
        return result;
    }

    /**
     * @Description:
     * 1. 从 ans[i] 选出最短的 x 个数, 使其元素和等于 sub / 2
     * 2. 即 target = maxSum - sub - sub => sub = (maxSum - target) / 2, 所以 maxSum - target 必须是偶数
     * 3. 处理 sub != 0 的情况, sub > n
     * 4, -4 {-4,-3,1,2}
     * 5. 假设 ans[i] < 0, 需要反转 x 个数, 使得 sum = target < 0, 不应该这样写, 还是应该保持 target < 0,
     * 那么 sub = (maxSum - target) / 2, 此时相当于 (maxSum + |target|) / 2
     * AC: 24ms/157.91MB
     * @param: n
     * @param: target
     * @return int[]
     * @author marks
     * @CreateDate: 2026/09/15 16:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int n, long target) {
        // 计算 n 序列的最大和
        long maxSum = (long) n * (n + 1) / 2;
        if (target > maxSum || target < -maxSum) {
            return new int[0];
        }
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = i + 1;
        }
        long sub = maxSum - target;
        if (sub % 2 != 0) {
            return new int[0];
        } else if (sub == 0) {
            // 升序排序返回, 即返回最小字典序
            Arrays.sort(ans);
            return ans;
        }
        // 处理 sub != 0
        long res = sub / 2;
        List<Integer> list = new ArrayList<>(); // 记录需要修改符号的元素
        for (int i = n; i > 0; i--) {
            if (res >= i) {
                res -= i;
                list.add(i);
            }
        }
        for (int i : list) {
            ans[i - 1] = -ans[i - 1];
        }
        Arrays.sort(ans);
        return ans;
    }

}
