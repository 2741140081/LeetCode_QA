package com.marks.leetcode.binary_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/26 15:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1642 {

    /**
     * @Description: [
     * 给你一个整数数组 heights ，表示建筑物的高度。另有一些砖块 bricks 和梯子 ladders 。
     *
     * 你从建筑物 0 开始旅程，不断向后面的建筑物移动，期间可能会用到砖块或梯子。
     *
     * 当从建筑物 i 移动到建筑物 i+1（下标 从 0 开始 ）时：
     *
     * 如果当前建筑物的高度 大于或等于 下一建筑物的高度，则不需要梯子或砖块
     * 如果当前建筑的高度 小于 下一个建筑的高度，您可以使用 一架梯子 或 (h[i+1] - h[i]) 个砖块
     * 如果以最佳方式使用给定的梯子和砖块，返回你可以到达的最远建筑物的下标（下标 从 0 开始 ）。
     *
     * tips:
     * 1 <= heights.length <= 10^5
     * 1 <= heights[i] <= 10^6
     * 0 <= bricks <= 10^9
     * 0 <= ladders <= heights.length
     * ]
     * @param heights 
     * @param bricks 
     * @param ladders
     * @return int
     * @author marks
     * @CreateDate: 2024/11/26 15:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        int result;
        result = method_01(heights, bricks, ladders);
        return result;
    }

    /**
     * @Description: [
     * 输入：heights = [4,12,2,7,3,18,20,3,19], bricks = 10, ladders = 2
     * 输出：7
     * 1. 梯子用在[0 ~ mid] 之间最大落差出pre[max]
     * 2. 方块可以分开使用
     *
     * AC:137ms/54.36MB
     * ]
     * @param heights
     * @param bricks
     * @param ladders
     * @return int
     * @author marks
     * @CreateDate: 2024/11/26 15:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] heights, int bricks, int ladders) {
        int n = heights.length;
        int[] pre = new int[n];
        pre[0] = 0;
        for (int i = 1; i < n; i++) {
            pre[i] = Math.max(0, heights[i] - heights[i - 1]);
        }
        int left = 0;
        int right = n - 1;
        int ans = 0;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (checkReach(pre, mid, bricks, ladders)) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

    private boolean checkReach(int[] pre, int mid, int bricks, int ladders) {
        int[] array = new int[mid + 1];
        System.arraycopy(pre, 0, array, 0, mid + 1);
        Arrays.sort(array);
        while (ladders > 0 && mid > 0) {
            array[mid] = 0;
            mid--;
            ladders--;
        }
        int sum = Arrays.stream(array).sum();
        if (sum <= bricks) {
            return true;
        } else {
            return false;
        }
    }
}
