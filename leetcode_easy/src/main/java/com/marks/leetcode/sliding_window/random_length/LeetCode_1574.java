package com.marks.leetcode.sliding_window.random_length;

import java.util.ArrayList;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/28 15:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1574 {
    /**
     * @Description: [
     * 给你一个整数数组 arr ，请你删除一个子数组（可以为空），使得 arr 中剩下的元素是 非递减 的。
     *
     * 一个子数组指的是原数组中连续的一个子序列。
     *
     * 请你返回满足题目要求的最短子数组的长度。
     *
     * tips:
     * 1 <= arr.length <= 10^5
     * 0 <= arr[i] <= 10^9
     * ]
     * @param arr
     * @return int
     * @author marks
     * @CreateDate: 2024/10/28 15:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findLengthOfShortestSubarray(int[] arr) {
        int result = 0;
        result = method_01(arr);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：arr = [1,2,3,10,4,2,3,5]
     * 输出：3
     * 解释：我们需要删除的最短子数组是 [10,4,2] ，长度为 3 。剩余元素形成非递减数组 [1,2,3,3,5] 。
     * 另一个正确的解为删除子数组 [3,10,4] 。
     *
     * 方案1: 以E1为例
     * 明确题目要求是删除一个子数组后, 剩余的数组是一个非递减数组
     * 将left = 0, right = n - 1 = 7
     * left++, right--来不断地缩小窗口的大小, 以保证ans = Math.min(ans, right - left + 1)
     * 判断初始化的ans = arr[left] <= arr[right] 时, 此时[0, left] + [right, n - 1]可以拼接成一个大的非递减数组
     * 循环的条件设置为
     * while(left < right){
     *     arr[left] >= arr[left - 1]执行left++
     *     arr[right] <= arr[right + 1] 执行right--
     *     if(arr[left] <= arr[right]) {
     *         ans = Math.min(ans, right - left - 1)
     *     } else {
     *         int temp = Math.max(left + 1, n - right)
     *         ans = Math.min(ans, n - temp)
     *     }
     * }
     * 以上方案存在问题, 因为while循环中, right和left是同步进行移动, 就会导致在这之前存在arr[left - 2] <= arr[right]成立,
     * 但是arr[left] > arr[right]
     *
     * 因此查看官方题解: 首先遍历arr, 找到最小的right, 使得[right, n - 1]是非递减数组
     * 此时剩余的数组[0, right - 1], 最大的需要删除数组的长度 ans = right - 1 - 0 + 1 = right
     * 遍历arr, 需要左边界left, 如果arr[left] > arr[left + 1], 条件不满足, 那么直接跳出循环
     * 以案例E1: [1,2,3,10,4,2,3,5]
     * right = 5
     * left = 0, arr[0] = 1 <= arr[right] = 2, res = 5 - 0 - 1 = 4
     * left = 1, arr[1] = 2 <= 2, res = 5 - 1 - 1 = 3
     * left = 2, arr[2] = 3 > 2,
     * 需要将right右移增大, 因为arr[left]是不断增大的, 以满足arr[left] <= arr[right],
     * right++, right = 6, arr[6] = 3, res = 6 - 2 - 1 = 3
     * left = 3, arr[3] = 10, right = 8, res = 8 - 3 - 1 = 4
     * left = 4, 此时已经不满足条件, arr[3] > arr[4],
     * break; // 跳出循环体
     *
     * AC:2ms/63.32MB
     * ]
     * @param arr
     * @return int
     * @author marks
     * @CreateDate: 2024/10/28 15:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr) {
        int n = arr.length;
        int right = n - 1;
        // 找到右侧的非递减数组[right, n - 1], 那么剩余部分为[0, right - 1]
        while (right > 0 && arr[right - 1] <= arr[right]) {
            right--;
        }
        if (right == 0) {
            return 0;
        }
        int ans = right; // 在剩余的数组中找到更小的
        for (int i = 0; i < n; i++) {
            while (right < n && arr[i] > arr[right]) {
                // 找到arr[left] <= arr[right], 因为arr[left]存在某些值是大于arr[right]
                right++;
            }
            ans = Math.min(ans, right - i - 1);
            if (i + 1 < n && arr[i] > arr[i + 1]) {
                // 到了left的尽头, 再往下就是不符合条件的情况, 直接跳出循环
                break;
            }
        }
        return ans;
    }
}
