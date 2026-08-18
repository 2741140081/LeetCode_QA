package com.marks.leetcode.daily_question;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3471 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/18 11:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3471 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和一个整数 k 。
     * 如果整数 x 恰好仅出现在 nums 中的一个大小为 k 的子数组中，则认为 x 是 nums 中的几近缺失（almost missing）整数。
     * 返回 nums 中 最大的几近缺失 整数，如果不存在这样的整数，返回 -1 。
     * 子数组 是数组中的一个连续元素序列。
     * tips:
     * 1 <= nums.length <= 50
     * 0 <= nums[i] <= 50
     * 1 <= k <= nums.length
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/08/18 11:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int largestInteger(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1. 贡献法, 记录每个数字的 数量和最远影响下标, 初始值为 0, -1
     * 2. 处理 nums[i], 如果 int[] temp = map.get(nums[i]); if temp[1] == -1,
     * int left = Math.max(i - k + 1, 0); int right = Math.min(n - 1, i + k - 1);
     * 此时的贡献度: Math.min(i - left + 1, right - i + 1), 并且更新 map.put(nums[i], i)
     * 3. if temp[1] != -1, int prev = temp[1], 会对 left 产生影响, left = Math.max(i - k + 1, 0, prev + 1),
     * 三者取最大值,
     * 4. 总结一下, 首先需要 map 存储数量和上一次 nums[i] 出现的坐标, {cnt, prev}
     * 5. 理解存在问题, 只需要出现一次的情况, 即只能选择首尾或者 k == n, 可以选择全部
     * AC: 1ms/44.02MB
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/08/18 11:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        int[] cnt = new int[51];
        // 遍历数组找到仅出现一次
        for (int num : nums) {
            cnt[num]++;
        }
        int ans = -1;
        if (k == n || k == 1) {
            // 从后向前遍历, 返回 cnt[i] == 1
            for (int i = cnt.length - 1; i >= 0; i--) {
                if (k == n && cnt[i] != 0) {
                    return i;
                } else if (k == 1 && cnt[i] == 1) {
                    return i;
                }
            }
        } else {
            // 只有第0个元素或者末尾元素
            if (cnt[nums[0]] == 1) {
                ans = nums[0];
            }
            if (cnt[nums[n - 1]] == 1) {
                ans = Math.max(ans, nums[n - 1]);
            }
        }

        return ans;
    }

}
