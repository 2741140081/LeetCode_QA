package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3514 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/24 10:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3514 {

    /**
     * @Description:
     * 给你一个整数数组 nums 。
     * XOR 三元组 定义为三个元素的异或值 nums[i] XOR nums[j] XOR nums[k]，其中 i <= j <= k。
     * 返回所有可能三元组 (i, j, k) 中 不同 的 XOR 值的数量。
     *
     * tips:
     * 1 <= nums.length <= 1500
     * 1 <= nums[i] <= 1500
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/07/24 10:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int uniqueXorTriplets(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. xor 的范围不会超过 2^11 = 2048
     * 2. 可以先计算 nums[i] XOR nums[j] 的结果, 存入 boolean[] arr 数组中
     * AC: 418ms/46.33MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/07/24 10:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int max = 2048;
        boolean[] arr = new boolean[max];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                arr[nums[i] ^ nums[j]] = true;
            }
        }
        boolean[] ans = new boolean[max];
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < max; j++) {
                if (arr[j]) {
                    ans[nums[i] ^ j] = true;
                }
            }
        }
        int res = 0;
        for (int i = 0; i < max; i++) {
            if (ans[i])
                res++;
        }

        return res;
    }

}
