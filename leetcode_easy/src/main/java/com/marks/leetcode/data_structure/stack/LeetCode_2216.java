package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/17 14:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2216 {
    /**
     * @Description:
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/1/17 14:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minDeletion(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 给你一个下标从 0 开始的整数数组 nums ，如果满足下述条件，则认为数组 nums 是一个 美丽数组 ：
     *
     * nums.length 为偶数
     * 对所有满足 i % 2 == 0 的下标 i ，nums[i] != nums[i + 1] 均成立
     * 注意，空数组同样认为是美丽数组。
     *
     * 你可以从 nums 中删除任意数量的元素。当你删除一个元素时，被删除元素右侧的所有元素将会向左移动一个单位以填补空缺，而左侧的元素将会保持 不变 。
     *
     * 返回使 nums 变为美丽数组所需删除的 最少 元素数目。
     * AC:14ms/56.12MB
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/1/17 14:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            int size = stack.size();
            if (size % 2 == 0) {
                stack.push(nums[i]);
            } else {
                if (stack.peek() != nums[i]) {
                    stack.push(nums[i]);
                }
            }
        }
        int ans = stack.size() % 2 == 0 ? 0 : 1;
        return n - stack.size() + ans;
    }
}
