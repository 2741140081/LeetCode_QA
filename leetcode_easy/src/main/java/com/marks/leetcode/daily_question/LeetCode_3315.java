package com.marks.leetcode.daily_question;

import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3315 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/21 15:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3315 {

    /**
     * @Description:
     * 给你一个长度为 n 的 质数 数组 nums 。你的任务是返回一个长度为 n 的数组 ans ，对于每个下标 i ，以下 条件 均成立：
     * ans[i] OR (ans[i] + 1) == nums[i]
     * 除此以外，你需要 最小化 结果数组里每一个 ans[i] 。
     * 如果没法找到符合 条件 的 ans[i] ，那么 ans[i] = -1 。
     * 质数 指的是一个大于 1 的自然数，且它只有 1 和自己两个因数。
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/21 15:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] minBitwiseArray(List<Integer> nums) {
        int[] result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：nums = [2,3,5,7]
     * 输出：[-1,1,4,3]
     * 1. 2 => (10)2 => (10) | (00) = 10
     * 3 => (11)2 => (10) | (01) = 2 | 1 => ans[i] = 1
     * 5 => (101)2 => (101) | (100) = 101 => ans[i] = 4
     * 7 => (111)2 => (100) | (011) = 111 => ans[i] = 3
     * 2. 根据意思规律可以得到 nums[i] == 2, 特殊处理
     * nums[i] => 转为而进行 (1111)2 或者 (xxxx0)2 最后一位是0
     * 3. 如果转为二进制全部是(111...1)2, 那么ans[i] = num[i] >> 1 右移一位
     * 4. 11 => (1011)2 => (1001) | (1010) = ans[i] = 9
     * 5. 找到从最后一位开始的最长的连续1, 假设是第x位, 将nums[i] 的第x位改成0, 使用异或运行 ans[i] = (nums[i] ^ (1 << x - 1))
     * AC: 1ms/45.97MB
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/21 15:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(List<Integer> nums) {
        int n = nums.size();
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int num = nums.get(i);
            if (num == 2) {
                ans[i] = -1;
            } else {
                // 找到 num 的最长的连续1的位数
                int t = ~num;
                int lowbit = t & (-t); // 这个运算用来获取一个数的最低位的1, -t 是 t 的补码（按位取反后加1）
                ans[i] = (num ^ (lowbit >> 1));

            }
        }
        return ans;
    }

}
