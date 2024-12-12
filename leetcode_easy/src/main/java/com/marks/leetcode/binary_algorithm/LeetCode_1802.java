package com.marks.leetcode.binary_algorithm;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/26 10:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1802 {
    /**
     * @Description: [
     * 给你三个正整数 n、index 和 maxSum 。你需要构造一个同时满足下述所有条件的数组 nums（下标 从 0 开始 计数）：
     *
     * nums.length == n
     * nums[i] 是 正整数 ，其中 0 <= i < n
     * abs(nums[i] - nums[i+1]) <= 1 ，其中 0 <= i < n-1
     * nums 中所有元素之和不超过 maxSum
     * nums[index] 的值被 最大化
     * 返回你所构造的数组中的 nums[index] 。
     *
     * 注意：abs(x) 等于 x 的前提是 x >= 0 ；否则，abs(x) 等于 -x 。
     * tips:
     * 1 <= n <= maxSum <= 10^9
     * 0 <= index < n
     * ]
     * @param n 
     * @param index 
     * @param maxSum
     * @return int
     * @author marks
     * @CreateDate: 2024/11/26 10:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxValue(int n, int index, int maxSum) {
        int result;
        result = method_01(n, index, maxSum);
        return result;
    }

    /**
     * @Description: [
     * 输入：n = 4, index = 2,  maxSum = 6
     * 输出：2
     * 解释：数组 [1,1,2,1] 和 [1,2,2,1] 满足所有条件。不存在其他在指定下标处具有更大值的有效数组。
     * 假设index点的最大值为max
     * max, max - 1, max - 2, max - 3, max - 4
     * left = 1, right = 6, mid =
     * [1,1,1,1,2,3,4,5]
     * 感觉就是纯粹的数学问题, 分类讨论
     * AC:0ms/40.15MB
     * ]
     * @param n 
     * @param index 
     * @param maxSum 
     * @return int
     * @author marks
     * @CreateDate: 2024/11/26 10:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int index, int maxSum) {
        int left = 1; // nums[1] >= 1, 因为nums[1]是正整数
        int right = maxSum;
        int ans = 1;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (checkMaxValue(mid, n, index, maxSum)) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

    /**
     * @Description: [
     * 输入：n = 4, index = 2,  maxSum = 6, mid = 3
     * [1,2,3,2]
     * 前半部分(mid - index + mid) * index / 2
     * 后半部分(n - 1 - index) * (mid + (mid - (n - 1 - index))) / 2
     * ]
     * @param mid
     * @param n
     * @param index
     * @param maxSum
     * @return boolean
     * @author marks
     * @CreateDate: 2024/11/26 10:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean checkMaxValue(long mid, int n, int index, int maxSum) {
        long sumLeft = 0;
        if (index > 0) {
            long leftIndex = mid - 1; // 当nums[i] == 1时, 需要的长度
            if (leftIndex >= index) {
                // 此时nums[0] >= 1, 可以使用高斯公式计算
                sumLeft += ((mid - index) + (mid - 1)) * index / 2;
            } else {
                // 由2部分组成[1,1,...1] 和[1,2,3,4...,mid - 1]
                sumLeft += (index - leftIndex) + mid * (mid - 1) / 2;
            }
        }
        long sumRight = 0;
        if (index < n - 1) {
            long rightIndex = mid - 1;
            if (rightIndex >= (n - index - 1)) {
                // 此时nums[n - 1] >= 1, 可以使用高斯公式计算
                sumRight += ((mid - 1) + (mid - (n - 1 - index))) * (n - 1 - index) / 2;
            } else {
                sumRight += ((mid - 1) + 1) * (mid - 1) / 2 + (n - index - mid);
            }
        }
        if (sumLeft + sumRight <= maxSum - mid) {
            return true;
        } else {
            return false;
        }
    }
}
