package com.marks.leetcode.DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/14 15:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3599 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和一个整数 k。
     * 你的任务是将 nums 分成 k 个非空的 子数组 。对每个子数组，计算其所有元素的按位 XOR 值。
     *
     * 返回这 k 个子数组中 最大 XOR 的 最小值 。
     *
     * 子数组 是数组中连续的 非空 元素序列。
     * @param nums 
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/10/14 15:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minXor(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }

    
    /**
     * @Description:
     * 1. 需要构建一个前缀数组, 存储 nums 的前缀Xor, int[] prefixXor = new int[nums.length];
     * 2. int[] dp = new int[nums.length]; dp[i] 表示 nums[0...i] 中子数组的 最大 XOR 值
     * 3. 需要分割成k个子数组, 就需要 k - 1 个不同的分割点
     * todo
     * @param nums 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/14 15:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        int[] prefixXor = new int[n];
        prefixXor[0] = nums[0];
        for (int i = 1; i < n; i++) {
            prefixXor[i] = prefixXor[i - 1] ^ nums[i];
        }
        return 0;
    }


}
