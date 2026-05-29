package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3300 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/29 10:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3300 {

    /**
     * @Description:
     * 给你一个整数数组 nums 。
     * 请你将 nums 中每一个元素都替换为它的各个数位之 和 。
     * 请你返回替换所有元素以后 nums 中的 最小 元素。
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/05/29 10:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minElement(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 获取 nums[i] 的数位之和, 由于 1 <= nums[i] <= 10^4, 所以使用除法来得到各个数位值
     * AC: 1ms/43.91MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/05/29 10:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int ans = 100;
        for (int num : nums) {
            int sum = 0;
            int temp = num;
            while (temp > 0) {
                sum += temp % 10;
                temp /= 10;
            }
            ans = Math.min(ans, sum);
            // 剪枝
            if (ans == 1) {
                return 1;
            }
        }
        return ans;
    }

}
