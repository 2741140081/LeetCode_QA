package com.marks.leetcode.hotLC;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_169 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/11 10:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_169 {

    /**
     * @Description:
     * 给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
     * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/11 10:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int majorityElement(int[] nums) {
        int result;
        result = method_01(nums);
        result = method_02(nums);
        result = method_03(nums);
        return result;
    }

    /**
     * @Description:
     * Boyer-Moore 投票算法是一种高效寻找数组中“多数元素”的算法，其核心思想是通过‌成对抵消‌机制实现：
     *
     * ‌多数元素‌：在数组中出现次数超过一半的元素。
     * ‌抵消机制‌：将数组视为选票，每个数字代表一个候选人。当两个不同的元素相遇时，视为一张支持票和一张反对票抵消。
     * 由于多数元素的票数大于其他所有元素票数的总和，它无法被完全抵消，最终剩下的候选人一定是多数元素。
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/11 11:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_03(int[] nums) {
        int count = 0;
        Integer candidate = null;
        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }
        return candidate;
    }

    /**
     * @Description:
     * 题目给定条件, 众数占超过一半, 所以中位数必定是众数
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/11 11:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    /**
     * @Description:
     * 排序 + 计算
     * AC: 6ms/54.7MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/11 10:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        // 排序
        Arrays.sort(nums);
        int count = 1;
        int maxCount = 1;
        int ans = nums[0];
        for (int i = 1; i < n; i++) {
            if (nums[i] == nums[i - 1]) {
                count++;
            } else {
                if (count > maxCount) {
                    maxCount = count;
                    ans = nums[i - 1];
                }
                count = 1;
            }
        }
        // 判断最后一组数据
        if (count > maxCount) {
            maxCount = count;
            ans = nums[n - 1];
        }

        return ans;
    }

}
