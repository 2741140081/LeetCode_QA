package com.marks.leetcode.bitwise_operation;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/19 14:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2317 {

    /**
     * <p>方法描述:
     * 给你一个下标从 0 开始的整数数组 nums 。
     * 一次操作中，选择 任意 非负整数 x 和一个下标 i ，更新 nums[i] 为 nums[i] AND (nums[i] XOR x) 。
     *
     * 注意，AND 是逐位与运算，XOR 是逐位异或运算。
     *
     * 请你执行 任意次 更新操作，并返回 nums 中所有元素 最大 逐位异或和。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] <= 10^8
     * </p>
     *
     * @param nums [参数描述]
     * @return [返回值类型]
     * @author marks
     * @date 2025/9/19 14:27
     * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumXOR(int[] nums) {
        int result;
        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    /**
     * @Description:
     * AC: 1ms/53.84MB
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/9/19 14:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums) {
        int ans = 0;
        for (int num : nums) {
            ans |= num;
        }
        return ans;
    }

    /**
     * <p>方法描述:
     * E1:nums = [3,2,4,6]
     * 输出: 7
     * 1. nums[i] AND (nums[i] XOR x), 只能将 nums[i] 的值减少,
     * 2. 最大数的位次是 27, 但是只需要找 int max = Arrays.stream(nums).max().getAsInt();
     * AC: 43ms/53.77MB
     * </p>
     *
     * @param nums [参数描述]
     * @return [返回值类型]
     * @author marks
     * @date 2025/9/19 14:27
     * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int max = Arrays.stream(nums).max().getAsInt();
        int n = Integer.toBinaryString(max).length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < nums.length; j++) {
                if ((nums[j] & 1) == 1) {
                    count++;
                }
                nums[j] = nums[j] >> 1;
            }
            if (count > 0) {
                sb.append('1');
            } else {
                sb.append('0');
            }
        }
        sb.reverse();
        return Integer.parseInt(sb.toString(), 2);
    }

}
