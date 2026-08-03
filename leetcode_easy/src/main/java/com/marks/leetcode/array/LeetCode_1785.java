package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1785 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/3 10:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1785 {

    /**
     * @Description:
     * 给你一个整数数组 nums ，和两个整数 limit 与 goal 。
     * 数组 nums 有一条重要属性：abs(nums[i]) <= limit 。
     * 返回使数组元素总和等于 goal 所需要向数组中添加的 最少元素数量 ，
     * 添加元素 不应改变 数组中 abs(nums[i]) <= limit 这一属性。
     * 注意，如果 x >= 0 ，那么 abs(x) 等于 x ；否则，等于 -x 。
     * @param: nums
     * @param: limit
     * @param: goal
     * @return int
     * @author marks
     * @CreateDate: 2026/08/03 10:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minElements(int[] nums, int limit, int goal) {
        int result;
        result = method_01(nums, limit, goal);
        return result;
    }

    /**
     * @Description:
     * 1. 计算数组和, long sum = Arrays.sum(nums), int target = sum - goal
     * AC: 1ms/86.42MB
     * @param: nums
     * @param: limit
     * @param: goal
     * @return int
     * @author marks
     * @CreateDate: 2026/08/03 10:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int limit, int goal) {
        long sum = 0;
        for (int num : nums) {
            sum += num;
        }
        long target = Math.abs(sum - goal) + (limit - 1);

        return (int) (target / limit);
    }

}
