package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/18 14:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2997 {

    
    /**
     * @Description:
     * 给你一个下标从 0 开始的整数数组 nums 和一个正整数 k 。
     * 你可以对数组执行以下操作 任意次 ：
     * 选择数组里的 任意 一个元素，并将它的 二进制 表示 翻转 一个数位，翻转数位表示将 0 变成 1 或者将 1 变成 0 。
     * 你的目标是让数组里 所有 元素的按位异或和得到 k ，请你返回达成这一目标的 最少 操作次数。
     *
     * 注意，你也可以将一个数的前导 0 翻转。比方说，数字 (101)2 翻转第四个数位，得到 (1101)2 。
     * @param nums 
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/9/18 14:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minOperations(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }

    
    /**
     * @Description:
     * E1:
     * 输入：nums = [2,1,3,4], k = 1
     * 1. int prevXor = nums[0] ^ nums[1] ^ ... ^ nums[nums.length - 1];
     * 2. int target = prevXor ^ k;
     * 3. 不太对? 这个是不会考虑0的情况, 只考虑了1的情况
     * 4. 看下案例, prevXor = 100, k = 001
     * AC: 1ms/57.73MB
     * @param nums 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2025/9/18 14:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int prevXor = 0;
        for (int i = 0; i < nums.length; i++) {
            prevXor ^= nums[i];
        }
        int ans = 0;
        while (prevXor != 0 || k != 0) {
            if ((prevXor & 1) != (k & 1)) {
                // 最低位不同, 需要转换
                ans++;
            }
            prevXor >>= 1;
            k >>= 1;
        }
        return ans;
    }

}
