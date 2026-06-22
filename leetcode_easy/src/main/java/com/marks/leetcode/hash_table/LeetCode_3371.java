package com.marks.leetcode.hash_table;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3371 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/15 16:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3371 {

    /**
     * @Description:
     * 给你一个整数数组 nums。该数组包含 n 个元素，其中 恰好 有 n - 2 个元素是 特殊数字 。
     * 剩下的 两个 元素中，一个是所有 特殊数字 的 和 ，另一个是 异常值 。
     * 异常值 的定义是：既不是原始特殊数字之一，也不是表示元素和的那个数。
     * 注意，特殊数字、和 以及 异常值 的下标必须 不同 ，但可以共享 相同 的值。
     * 返回 nums 中可能的 最大异常值。
     * tips:
     * 3 <= nums.length <= 10^5
     * -1000 <= nums[i] <= 1000
     * 输入保证 nums 中至少存在 一个 可能的异常值。
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/06/15 16:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int getLargestOutlier(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 如果假设当前 nums[i] 是异常值, int sum = Sum(nums[i]); remain = sum - nums[i];
     * 2. if remain 是偶数, Sum(other[n - 2]) + sumN2 = remain; sumN2 = remain / 2; 并且 map 中包含该值
     * map.contains(sumN2); 成立, 返回 nums[i]
     * 3. 需要去除 map 中多余的 nums[i] 的影响, 当 nums[i] 数量为1, 但是sumN2 == nums[i] 时, 会发生错误, 即 当前 nums[i] 既是异常值, 也是特殊数字之和,
     * 并且由于数量只有1, 导致下标相同, 发生错误.
     * AC: 89ms/107.92MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/06/15 16:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int ans = Integer.MIN_VALUE;
        int sum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.merge(num, 1, Integer::sum);
            sum += num;
        }
        for (int num : nums) {
            int remain = sum - num;
            if (remain % 2 == 0) {
                int sumN2 = remain / 2;
                if (map.containsKey(sumN2)) {
                    if (num == sumN2 && map.get(sumN2) == 1) {
                        continue;
                    }
                    ans = Math.max(ans, num);
                }
            }
        }

        return ans;
    }

}
