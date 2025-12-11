package com.marks.leetcode.hotLC;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_287 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/11 16:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_287 {

    /**
     * @Description:
     * 给定一个包含 n + 1 个整数的数组 nums ，其数字都在 [1, n] 范围内（包括 1 和 n），可知至少存在一个重复的整数。
     * 假设 nums 只有 一个重复的整数 ，返回 这个重复的数 。
     * 你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间。
     * tips:
     * 1 <= n <= 10^5
     * nums.length == n + 1
     * 1 <= nums[i] <= n
     * nums 中 只有一个整数 出现 两次或多次 ，其余整数均只出现 一次
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/11 16:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findDuplicate(int[] nums) {
        int result;
        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 由于数组中假设重复数字是 target, 那么必定存在两个或者以上下标指向target, target就是环的入口点
     * 2. 假设 nums[i] = target, nums[j] = target.
     * [9,2,3,4,5,1,4,4,8]
     * 0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 <-> 7
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/11 16:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums) {
        int slow = nums[0];
        int fast = nums[0];
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        slow = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

    /**
     * @Description:
     * 1. 没有思路!
     * 2. AC: 39ms/77.7MB
     * 要求不能修改数组, 所以不能使用排序和哈希, 看看官解吧
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/11 16:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == nums[i + 1]) {
                return nums[i];
            }
        }
        return 0;
    }

}
