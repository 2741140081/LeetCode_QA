package com.marks.leetcode.double_pointer.single_sequence;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/5 17:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_11 {
    /**
     * @Description: [
     * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
     *
     * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
     *
     * 返回容器可以储存的最大水量。
     *
     * 说明：你不能倾斜容器。
     *
     * ]
     * @param height
     * @return int
     * @author marks
     * @CreateDate: 2024/11/5 17:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxArea(int[] height) {
        int result;
        result = method_01(height);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * {1,8,6,2,5,4,8,3,7}
     * res = 49
     *
     * AC:4ms/56.71MB
     * ]
     * @param height
     * @return int
     * @author marks
     * @CreateDate: 2024/11/5 17:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] height) {
        int n = height.length;
        int left = 0;
        int right = n - 1;

        int ans = 0;
        while (left < right) {
            int temp = Math.min(height[left], height[right]) * (right - left);
            ans = Math.max(ans, temp);
            if (height[left] > height[right]) {
                right--;
            }else {
                left++;
            }
        }
        return ans;
    }
}
