package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/18 11:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1829 {

    
    /**
     * @Description:
     * 给你一个 有序 数组 nums ，它由 n 个非负整数组成，同时给你一个整数 maximumBit 。你需要执行以下查询 n 次：
     *
     * 找到一个非负整数 k < 2^maximumBit ，使得 nums[0] XOR nums[1] XOR ... XOR nums[nums.length-1] XOR k 的结果 最大化 。k 是第 i 个查询的答案。
     * 从当前数组 nums 删除 最后 一个元素。
     * 请你返回一个数组 answer ，其中 answer[i]是第 i 个查询的结果。
     *
     * tips:
     * nums.length == n
     * 1 <= n <= 10^5
     * 1 <= maximumBit <= 20
     * 0 <= nums[i] < 2^maximumBit
     * nums中的数字已经按 升序 排好序。
     * @param nums 
     * @param maximumBit
     * @return int[]
     * @author marks
     * @CreateDate: 2025/9/18 11:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] getMaximumXor(int[] nums, int maximumBit) {
        int[] result;
        result = method_01(nums, maximumBit);
        return result;
    }

    
    /**
     * @Description:
     * 输入：nums = [0,1,1,3], maximumBit = 2
     * 输出：[0,3,2,3]
     * 解释：查询的答案如下：
     * 第一个查询：nums = [0,1,1,3]，k = 0，因为 0 XOR 1 XOR 1 XOR 3 XOR 0 = 3 。
     * 第二个查询：nums = [0,1,1]，k = 3，因为 0 XOR 1 XOR 1 XOR 3 = 3 。
     * 第三个查询：nums = [0,1]，k = 2，因为 0 XOR 1 XOR 2 = 3 。
     * 第四个查询：nums = [0]，k = 3，因为 0 XOR 3 = 3 。
     * 1. int[] prevXor = new int[nums.length];  存储前缀异或结果
     * 2. 不能直接遍历2^maximumBit, 2^14 > 10^5, 如果maximumBit >= 14, 直接取14就行
     * 3. 10000 的二进制 2^13 = 8192, 最高是13位
     * 4. 需要分情况讨论: nums 最大值的位数是 currBit, currBit <= 13
     * a. currBit >= maximumBit, 不对, 可以把所有的0都变为1, 可以用或运算符, 将0变成1, 并且1保持不变, 即 prevXor |= (1 << maximumBit);
     * AC: 3ms/62.39MB
     * @param nums 
     * @param maximumBit 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/9/18 11:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums, int maximumBit) {
        int n = nums.length;
        int[] prevXor = new int[n];
        prevXor[0] = nums[0];
        for (int i = 1; i < n; i++) {
            prevXor[i] = prevXor[i - 1] ^ nums[i];
        }
        int[] query = new int[n];
        int index = n - 1;
        for (int i = 0; i < n; i++, index--) {
            int max = prevXor[i] | ((1 << maximumBit) - 1);
            query[index] = max ^ prevXor[i];
        }

        return query;
    }

}
