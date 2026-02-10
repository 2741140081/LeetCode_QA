package com.marks.leetcode.daily_question;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3719 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/10 10:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3719 {

    /**
     * @Description:
     * 给你一个整数数组 nums。
     * 如果子数组中 不同偶数 的数量等于 不同奇数 的数量，则称该 子数组 是 平衡的 。
     * 返回 最长 平衡子数组的长度。
     * 子数组 是数组中连续且 非空 的一段元素序列。
     * tips:
     * 1 <= nums.length <= 1500
     * 1 <= nums[i] <= 10^5
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/02/10 10:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestBalanced(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 好像没有思路, 先暴力吧, 将 i 作为平衡子数组的起始节点, 然后使用map 分别统计偶数和奇数出现的次数, 然后比较
     * AC: 393ms/46.53MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/02/10 10:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            Map<Integer, Integer> even = new HashMap<>();
            Map<Integer, Integer> odd = new HashMap<>();
            for (int j = i; j < nums.length; j++) {
                if (nums[j] % 2 == 0) {
                    even.merge(nums[j], 1, Integer::sum);
                } else {
                    odd.merge(nums[j], 1, Integer::sum);
                }
                if (even.size() == odd.size()) {
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }
        return ans;
    }

}
