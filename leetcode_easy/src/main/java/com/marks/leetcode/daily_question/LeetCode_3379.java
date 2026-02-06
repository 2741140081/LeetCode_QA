package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3379 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/5 10:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3379 {

    /**
     * @Description:
     * 给你一个整数数组 nums，它表示一个循环数组。请你遵循以下规则创建一个大小 相同 的新数组 result ：
     * 对于每个下标 i（其中 0 <= i < nums.length），独立执行以下操作：
     * 如果 nums[i] > 0：从下标 i 开始，向 右 移动 nums[i] 步，在循环数组中落脚的下标对应的值赋给 result[i]。
     * 如果 nums[i] < 0：从下标 i 开始，向 左 移动 abs(nums[i]) 步，在循环数组中落脚的下标对应的值赋给 result[i]。
     * 如果 nums[i] == 0：将 nums[i] 的值赋给 result[i]。
     * 返回新数组 result。
     * 注意：由于 nums 是循环数组，向右移动超过最后一个元素时将回到开头，向左移动超过第一个元素时将回到末尾。
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2026/02/05 10:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] constructTransformedArray(int[] nums) {
        int[] result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 直接模拟就好了， 这个index 可以进行合并操作
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2026/02/05 10:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
//            int index = (n * 100 + nums[i] + i) % n;
            int index = ((i + nums[i]) % n + n) % n; // 这个索引更优雅
            result[i] = nums[index];
        }
        return result;
    }

}
