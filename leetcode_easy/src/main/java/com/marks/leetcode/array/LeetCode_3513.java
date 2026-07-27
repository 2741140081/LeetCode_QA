package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3513 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/23 10:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3513 {

    /**
     * @Description:
     * 给你一个长度为 n 的整数数组 nums，其中 nums 是范围 [1, n] 内所有数的 排列 。
     * XOR 三元组 定义为三个元素的异或值 nums[i] XOR nums[j] XOR nums[k]，其中 i <= j <= k。
     * 返回所有可能三元组 (i, j, k) 中 不同 的 XOR 值的数量。
     * 排列 是一个集合中所有元素的重新排列。
     *
     * tips:
     * 1 <= n == nums.length <= 10^5
     * 1 <= nums[i] <= n
     * nums 是从 1 到 n 的整数的一个排列。
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/07/23 10:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int uniqueXorTriplets(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 先排序, XOR 的定义是, 对于二进制, 相同为0, 不同为1, 所以可能存在异或值大于 n 的数
     * 2. 最大的数 n, 它包含多少位数
     * AC: 0ms/122.7MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/07/23 10:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        if (n < 3) {
            return n;
        }
        // 计算 n 的位数
        int cnt = Integer.SIZE - Integer.numberOfLeadingZeros(n);
        // 右移
        return 1 << cnt; // 返回 2^cnt
    }

}
