package com.marks.leetcode.array;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_137 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/28 14:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_137 {

    /**
     * @Description:
     * 给你一个整数数组 nums ，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次 。请你找出并返回那个只出现了一次的元素。
     * 你必须设计并实现线性时间复杂度的算法且使用常数级空间来解决此问题。
     *
     * tips:
     * 1 <= nums.length <= 3 * 10^4
     * -2^31 <= nums[i] <= 2^31 - 1
     * nums 中，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/04/28 14:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    public int singleNumber(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 数组是无序的
     * 2. 按照正常思路, 使用map存储数据, 然后遍历map, 找到只出现一次的元素
     * 3. 但是现在需要常数级的空间复杂度，所以不能使用map
     * 4. 有一个思路是先排序, 然后遍历数组, 找到只出现一次的元素
     * 5. 假设当前位于 i 处, 并且 prev != nums[i] && next != nums[i], 说明 i 处是只出现一次的元素
     * AC: 6ms/45.19MB
     * 6. Arrays.sort(nums); 的时间复杂度是 nlog n, 空间复杂度是 log n
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/04/28 14:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        // 排序
        Arrays.sort(nums);
        // 先判断 0 的下标, 如果下标0与下标2
        if (nums[0] != nums[1]) {
            return nums[0];
        }
        if (nums[n - 1] != nums[n - 2]) {
            return nums[n - 1];
        }
        // 遍历数组
        for (int i = 1; i < n - 1; i++) {
            int prev = nums[i - 1];
            int next = nums[i + 1];
            if (prev != nums[i] && next != nums[i]) {
                return nums[i];
            }
        }

        return 0;
    }

}
