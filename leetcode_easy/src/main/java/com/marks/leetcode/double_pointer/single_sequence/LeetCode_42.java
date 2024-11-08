package com.marks.leetcode.double_pointer.single_sequence;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/8 10:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_42 {
    /**
     * @Description: [
     * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
     *
     * n == height.length
     * 1 <= n <= 2 * 10^4
     * 0 <= height[i] <= 10^5
     * ]
     * @param height
     * @return int
     * @author marks
     * @CreateDate: 2024/11/8 10:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int trap(int[] height) {
        int result = 0;
        result = method_01(height);
        return result;
    }
    
    /**
     * @Description: [
     *
     * E1：
     * 输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
     * 输出：6
     * 解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）
     *
     * 双指针
     * AC:0ms/45.57MB
     * ]
     * @param height 
     * @return int
     * @author marks
     * @CreateDate: 2024/11/8 10:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] height) {
        int n = height.length;
        int left = 0;
        int right = n - 1;
        int leftMax = 0;
        int rightMax = 0;
        int ans = 0;

        while (left < right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            if (height[left] < height[right]) {
                ans += (leftMax - height[left]);
                left++;
            } else {
                ans += (rightMax - height[right]);
                right--;
            }
        }

        return ans;
    }
}
