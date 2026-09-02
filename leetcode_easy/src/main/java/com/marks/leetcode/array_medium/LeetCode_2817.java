package com.marks.leetcode.array_medium;

import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2817 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/1 11:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2817 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的整数数组 nums 和一个整数 x 。
     * 请你找到数组中下标距离至少为 x 的两个元素的 差值绝对值 的 最小值 。
     * 换言之，请你找到两个下标 i 和 j ，满足 abs(i - j) >= x 且 abs(nums[i] - nums[j]) 的值最小。
     * 请你返回一个整数，表示下标距离至少为 x 的两个元素之间的差值绝对值的 最小值 。
     * @param: nums
     * @param: x
     * @return int
     * @author marks
     * @CreateDate: 2026/09/01 11:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minAbsoluteDifference(List<Integer> nums, int x) {
        int result;
        result = method_01(nums, x);
        return result;
    }

    /**
     * @Description:
     * 1. 还是同样的滑动窗口 + 有序集合
     * AC: 172ms/103.45MB
     * @param: nums
     * @param: x
     * @return int
     * @author marks
     * @CreateDate: 2026/09/01 11:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(List<Integer> nums, int x) {
        int n = nums.size();
        TreeSet<Integer> treeSet = new TreeSet<>();
        int left = 0;
        int ans = Integer.MAX_VALUE;
        for (int right = x; right < n; right++) {
            // 添加 left 点的元素到 treeMap 中
            treeSet.add(nums.get(left));
            // 0 是最小的, 直接通过 TreeSet 判断 nums.get(right) 是否存在TreeSet
            if (treeSet.contains(nums.get(right))) {
                return 0;
            }

            // 查询 treeSet 分别得到小于等于 nums[right] 和大于等于 nums[right] 的元素
            Integer lower = treeSet.lower(nums.get(right));
            Integer higher = treeSet.higher(nums.get(right));
            if (lower != null) {
                ans = Math.min(ans, nums.get(right) - lower);
            }
            if (higher != null) {
                ans = Math.min(ans, higher - nums.get(right));
            }
            left++;
        }
        return ans;
    }

}
