package com.marks.leetcode.sliding_window.random_length;

import java.util.HashMap;
import java.util.HashSet;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/30 9:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2799 {
    /**
     * @Description: [
     * 给你一个由 正 整数组成的数组 nums 。
     *
     * 如果数组中的某个子数组满足下述条件，则称之为 完全子数组 ：
     *
     * 子数组中 不同 元素的数目等于整个数组不同元素的数目。
     * 返回数组中 完全子数组 的数目。
     *
     * 子数组 是数组中的一个连续非空序列。
     * tips:
     * 1 <= nums.length <= 1000
     * 1 <= nums[i] <= 2000
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/10/30 9:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countCompleteSubarrays(int[] nums) {
        int result = 0;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums = [1,3,1,2,2]
     * 输出：4
     * 感觉和之前题型类似, 关键在于如何判断窗口中不同元素的数量, 感觉可以用HashMap.size()
     * 开始作业！！！
     * AC:9ms/43.96MB
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/10/30 9:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        HashSet<Integer> set = new HashSet<>(); // 记录原数组中不同元素的数量
        HashMap<Integer, Integer> cntMap = new HashMap<>(); // 计算窗口中不同元素的数量
        for (int i = 0; i < n; i++) {
            set.add(nums[i]);
        }
        int count = set.size();
        int left = n - 1;
        int ans = 0;
        cntMap.put(nums[n - 1], 1);

        for (int right = n - 1; right >= 0; right--) {

            while (cntMap.size() < count && left > 0) {
                left--;
                cntMap.put(nums[left], cntMap.getOrDefault(nums[left], 0) + 1);
            }
            if (cntMap.size() == count) {
                ans += (left + 1);
            }

            // 将右边界移动
            if (cntMap.get(nums[right]) == 1) {
                cntMap.remove(nums[right]);
            }else {
                cntMap.put(nums[right], cntMap.get(nums[right]) - 1);
            }

        }
        return ans;
    }
}
