package com.marks.leetcode.monotonic_stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/28 10:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_503 {
    /**
     * @Description: [
     * 给定一个循环数组 nums （ nums[nums.length - 1] 的下一个元素是 nums[0] ），返回 nums 中每个元素的 下一个更大元素 。
     *
     * 数字 x 的 下一个更大的元素 是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。
     * 如果不存在，则输出 -1 。
     *
     * tips:
     * 1 <= nums.length <= 10^4
     * -10^9 <= nums[i] <= 10^9
     * ]
     * @param nums 
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/28 10:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] nextGreaterElements(int[] nums) {
        int[] result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description: [
     * 输入: nums = [1,2,1]
     * 输出: [2,-1,2]
     * 解释: 第一个 1 的下一个更大的数是 2；
     * 数字 2 找不到下一个更大的数；
     * 第二个 1 的下一个最大的数需要循环搜索，结果也是 2。
     *
     * AC:6ms/45.13MB
     * ]
     * @param nums
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/28 10:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        Arrays.fill(ans,  -1);
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < 2 * n; i++) {
            int num = nums[i % n];
            while (!stack.isEmpty() && nums[stack.peek() % n] < num) {
                Integer preIndex = stack.poll();
                ans[preIndex % n] = num;
            }
            stack.push(i);
        }
        return ans;
    }
}
