package com.marks.leetcode.DP;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/21 11:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_698 {

    
    /**
     * @Description:
     * 给定一个整数数组  nums 和一个正整数 k，找出是否有可能把这个数组分成 k 个非空子集，其总和都相等。
     *
     * tips:
     * 1 <= k <= len(nums) <= 16
     * 0 < nums[i] < 10000
     * 每个元素的频率在 [1,4] 范围内
     * @param nums 
     * @param k
     * @return boolean
     * @author marks
     * @CreateDate: 2025/10/21 11:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean canPartitionKSubsets(int[] nums, int k) {
        boolean result;
        result = method_01(nums, k);
        result = method_02(nums, k);
        result = method_03(nums, k);
        return result;
    }

    /**
     * @Description:
     * 查看官方题解, 状态压缩 + 记忆化搜索
     * @param: nums
     * @param: k
     * @return boolean
     * @author marks
     * @CreateDate: 2026/02/11 15:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] nums;
    private int target, n;
    private boolean[] dp;
    private boolean method_03(int[] nums, int k) {
        this.nums = nums;
        this.n = nums.length;
        int sum = Arrays.stream(nums).sum();
        if (sum % k != 0) {
            return false;
        }
        target = sum / k;
        Arrays.sort(this.nums);
        if (this.nums[n - 1] > target) {
            return false;
        }
        dp = new boolean[1 << n];
        Arrays.fill(dp, true);
        return dfs(0, (1 << n) - 1);
    }

    private boolean dfs(int p, int s) {
        if (s == 0) {
            // nums 中所有的数均分配完成
            return true;
        }
        if (!dp[s]) {
            // dp[s] 是否已经遍历过
            return dp[s];
        }
        dp[s] = false;
        for (int i = 0; i < n; i++) {
            if (nums[i] + p > target) {
                break;
            }
            if (((s >> i) & 1) != 0) {
                // 判断 s 的第 i 位是否为1
                if (dfs((p + nums[i]) & target, s ^ (1 << i))) {
                    // 将 s 的 第 i 位置为0, 并且判断 p + nums[i] == target
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @Description:
     * 输入： nums = [4, 3, 2, 3, 5, 2, 1], k = 4
     * 输出： True
     * 1. 我记得之前好像有过类似的将数组分割成为相等的两份,
     * 2. n <= 16, 这个数据范围大概里是进行状态压缩,
     * 3. int[][] dp = new int[k][1 << n]
     * 4. 如果通过回溯来进行解决, 这个的事件复杂度是多少? n^k = 16^16 = 2^64(必定超时, 不可取)
     * 5. 没有思路了, 看看tips 先
     * 超时, 156/164 通过
     * @param: nums
     * @param: k
     * @return boolean
     * @author marks
     * @CreateDate: 2026/02/11 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean ans;
    private boolean method_02(int[] nums, int k) {
        int n = nums.length;
        int sum = Arrays.stream(nums).sum();
        int target = sum / k;
        int max = Arrays.stream(nums).max().getAsInt();
        if (sum % k != 0 || max > target) {
            return false;
        }
        // 对数组进行降序排序
        nums = Arrays.stream(nums).boxed().sorted(Collections.reverseOrder()).mapToInt(Integer::intValue).toArray();
        // 试一试回溯
        int[] dp = new int[k]; // 存储k 个子集的 sum 值
        ans = false;
        backtrack(nums, 0, 0, target, dp);

        return ans;
    }

    private void backtrack(int[] nums, int index, int count, int target, int[] dp) {
        if (ans) {
            return;
        }
        if (index == nums.length) {
            // check all dp[i] is equal to target
            // check count == k
            if (count == dp.length) {
                ans = true;
            }
            return;
        }
        for (int i = 0; i < dp.length; i++) {
            if (dp[i] + nums[index] <= target) {
                dp[i] += nums[index];

                backtrack(nums, index + 1, count + (dp[i] == target ? 1 : 0), target, dp);
                dp[i] -= nums[index];
            }
        }
    }


    /**
     * @Description:
     * 1. 先判断sum % k == 0
     * 2. 不对, 只是说需要把数组分成k个子集, 没有说明是需要连续的子集
     * 3. 对于 nums[i], 可以插入到 [0 ~ k - 1] 的任意一个集合中
     * 4. 目标子集的集合sum值是确定的, 即 sum % k == 0, 在合法情况下 int target = sum / k;
     *
     * @param nums 
     * @param k 
     * @return boolean
     * @author marks
     * @CreateDate: 2025/10/21 11:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums, int k) {
        int sum = Arrays.stream(nums).sum();
        if (sum % k != 0) {
            // 不合法情况
            return false;
        }
        Arrays.sort(nums); // 排序
        int n = nums.length;
        int target = sum / k;
        while (k > 0) {
            // 找到 k 组 子集, 使其子集和 = target

            k--;
        }

        return false;
    }

}
