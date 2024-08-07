package com.marks.leetcode.knapsack.multiple_knapsack;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>项目名称: 多重背包 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/7 14:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2902 {
    public final int MOD = (int) 1e9 + 7;

    /**
     * @Description: [
     * 给你一个下标从 0 开始的非负整数数组 nums 和两个整数 l 和 r 。
     * 请你返回 nums 中子多重集合的和在闭区间 [l, r] 之间的 子多重集合的数目 。
     * 由于答案可能很大，请你将答案对 109 + 7 取余后返回。
     * 子多重集合 指的是从数组中选出一些元素构成的 无序 集合，每个元素 x 出现的次数可以是 0, 1, ..., occ[x] 次，其中 occ[x] 是元素 x 在数组中的出现次数。
     * 注意：
     * 如果两个子多重集合中的元素排序后一模一样，那么它们两个是相同的 子多重集合 。
     * 空 集合的和是 0
     * tips:
     * 1 <= nums.length <= 2 * 104
     * 0 <= nums[i] <= 2 * 104
     * nums 的和不超过 2 * 104 。
     * 0 <= l <= r <= 2 * 104
     * ]
     * @param nums
     * @param l
     * @param r
     * @return int
     * @author marks
     * @CreateDate: 2024/8/7 14:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countSubMultisets(List<Integer> nums, int l, int r) {
        int result = 0;
        result = method_01(nums, l, r);
        return result;
    }

    /**
     * @Description: [E1:
     * 输入：nums = [2,1,4,2,7], l = 1, r = 5
     * 输出：7
     * 解释：和在闭区间 [1, 5] 之间的子多重集合为 {1} ，{2} ，{4} ，{2, 2} ，{1, 2} ，{1, 4} 和 {1, 2, 2} 。
     * 先不去管 l, 只关注与r
     * 1. 对nums[] 循环遍历 得到coins[] = {1, 2, 4, 7} 及其数量 counts[] = {1, 2, 1, 1}
     * 2. 那么这个问题就与LeetCode_2585.java 类似, 求dp[n][target]
     * 3. 如果target和是一个区间[l, r]
     * int res = 0;
     * for i = l; i <= r; i++
     * res += dp[n][i] % MOD
     * ]
     * @param nums
     * @param l
     * @param r
     * @return int
     * @author marks
     * @CreateDate: 2024/8/7 15:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(List<Integer> nums, int l, int r) {
        Map<Integer, Long> map = nums.stream().collect(
                Collectors.groupingBy(Integer::intValue, Collectors.counting()));

        // List<Integer> list = nums.stream().distinct().sorted().collect(Collectors.toList());
        // int[] array = list.stream().mapToInt(Integer::intValue).toArray();
        int[] array = nums.stream().distinct().sorted().collect(Collectors.toList()).
                stream().mapToInt(Integer::intValue).toArray();
        int n = map.size();
        int[][] dp = new int[2][r + 1];
        dp[0][0] = 1;
        int curr = 0;
        for (int i = 1; i <= n; i++) {
            curr = i % 2;
            int pre = 1 - curr;
            int temp = array[i - 1];
            for (int j = 0; j <= r; j++) {
                dp[curr][j] = 0; // 初始化
                // 考虑temp = 0的情况
                int num = 0;
                if (temp == 0) {
                    num = Math.toIntExact(map.get(temp));
                }else {
                    int tempCount = j / temp;
                    num = (int) Math.min(tempCount, map.get(temp));
                }
                for (int k = 0; k <= num; k++) {
                    if (j >= k * temp) {
                        dp[curr][j] = (dp[curr][j] + dp[pre][j - k * temp]) % MOD;
                    }else {
                        dp[curr][j] = dp[pre][j] % MOD;
                    }
                }
            }
        }
        long res = 0;
        for (int i = l; i <= r; i++) {
            res = (res + dp[curr][i]) % MOD;
        }

        return (int) res;
    }
}
