package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/17 15:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1486 {

    /**
     * @Description:
     * 给你两个整数，n 和 start 。
     *
     * 数组 nums 定义为：nums[i] = start + 2*i（下标从 0 开始）且 n == nums.length 。
     *
     * 请返回 nums 中所有元素按位异或（XOR）后得到的结果。
     * @param n 
     * @param start
     * @return int
     * @author marks
     * @CreateDate: 2025/9/17 15:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int xorOperation(int n, int start) {
        int result;
        result = method_01(n, start);
        return result;
    }

    
    /**
     * @Description:
     * E1:
     * 输入：n = 5, start = 0
     * 输出：8
     * 解释：数组 nums 为 [0, 2, 4, 6, 8]，其中 (0 ^ 2 ^ 4 ^ 6 ^ 8) = 8 。
     * start = xxx, nums[] = {0000, 0010, 0100, 0110, 1000}
     * 1. 假设 start = 1, nums[] = {0001, 0011, 0101, 0111, 1001} = 1
     * 2. 假设 start = 2, nums[] = {0010, 0100, 0110, 1000, 1010} = 2
     * (start + 2 * 0) ^ (start + 2 * 1) ^ (start + 2 * 2) ^ (start + 2 * 3) ^ (start + 2 * 4) =
     * @param n 
     * @param start 
     * @return int
     * @author marks
     * @CreateDate: 2025/9/17 15:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int start) {
        int result = 0;
        for (int i = 0; i < n; i++) {
            result ^= start + 2 * i;
        }
        return result;
    }

}
