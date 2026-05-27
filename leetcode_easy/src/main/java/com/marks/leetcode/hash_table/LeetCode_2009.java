package com.marks.leetcode.hash_table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2009 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/27 10:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2009 {

    /**
     * @Description:
     * 给你一个整数数组 nums 。每一次操作中，你可以将 nums 中 任意 一个元素替换成 任意 整数。
     * 如果 nums 满足以下条件，那么它是 连续的 ：
     * nums 中所有元素都是 互不相同 的。
     * nums 中 最大 元素与 最小 元素的差等于 nums.length - 1 。
     * 比方说，nums = [4, 2, 5, 3] 是 连续的 ，但是 nums = [1, 2, 3, 5, 6] 不是连续的 。
     * 请你返回使 nums 连续 的 最少 操作次数。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/05/27 10:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minOperations(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 查看官方题解, 使用 去重 + 排序 + 滑动窗口
     * 2. 首先对 nums[] 数据进行去重和排序操作, 得到排序后的结果 list
     * 3. 以 left = list.get(i) 作为滑动窗口的左端点, 由于 max - min = n - 1, 所以可以通过left 得到右端点 right = left + n - 1;
     * 4. 遍历 list, 判断 list 中有多少个元素在 [left, right] 范围内, 假设有[i, j] 个元素, count = j - i + 1;
     * 其它元素需要修改, 剩余元素为 ans = Math.min(ans, n - (j - i + 1));
     * 5. 依次遍历 list 中的 i, 获取最小的ans 结果.
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/05/27 10:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        List<Integer> list = new ArrayList<>(set);
        list.sort(Integer::compareTo);
        int ans = n;
        int j = 0;
        for (int i = 0; i < list.size(); i++) {
            int left = list.get(i);
            int right = left + n - 1;
            while (j < list.size() && list.get(j) <= right) {
                ans = Math.min(ans, n - (j - i + 1));
                j++;
            }
        }

        return ans;
    }

}
