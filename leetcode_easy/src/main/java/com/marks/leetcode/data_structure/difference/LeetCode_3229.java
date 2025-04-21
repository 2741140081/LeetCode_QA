package com.marks.leetcode.data_structure.difference;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/19 14:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3229 {

    /**
     * @Description:
     * 给你两个长度相同的正整数数组 nums 和 target。
     *
     * 在一次操作中，你可以选择 nums 的任何子数组，并将该子数组内的每个元素的值增加或减少 1。
     *
     * 返回使 nums 数组变为 target 数组所需的 最少 操作次数。
     * tips:
     * 1 <= nums.length == target.length <= 10^5
     * 1 <= nums[i], target[i] <= 10^8
     * @param nums
     * @param target
     * @return long
     * @author marks
     * @CreateDate: 2025/2/19 14:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long minimumOperations(int[] nums, int[] target) {
        long result;
        result = method_01(nums, target);
        return result;
    }

    /**
     * @Description:
     * E1:
     * nums = [1,3,2], target = [2,1,4]
     * 5
     * 1 + 3 + 1 = 5
     *
     * E2:
     * nums = [1,3,2], target = [3,1,4]
     * 2 + 4 = 6
     * pre = [2, -2, 2]
     *
     * 已经找到 pre[], 但是关键点是, 需要找到 不定长 k
     * @param nums
     * @param target
     * @return long
     * @author marks
     * @CreateDate: 2025/2/19 14:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums, int[] target) {
        long s = target[0] - nums[0];
        long ans = Math.abs(s);
        for (int i = 1; i < nums.length; i++) {
            int k = (target[i] - target[i - 1]) - (nums[i] - nums[i - 1]);
            if (k > 0) {
                ans += s >= 0 ? k : Math.max(k + s, 0);
            } else {
                ans -= s <= 0 ? k : Math.min(k + s, 0);
            }
            s += k;
        }
        return ans;
    }
}
