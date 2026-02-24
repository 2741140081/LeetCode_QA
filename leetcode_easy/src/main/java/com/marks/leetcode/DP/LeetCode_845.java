package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_845 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/11 16:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_845 {

    /**
     * @Description:
     * 把符合下列属性的数组 arr 称为 山脉数组 ：
     * arr.length >= 3
     * 存在下标 i（0 < i < arr.length - 1），满足
     * arr[0] < arr[1] < ... < arr[i - 1] < arr[i]
     * arr[i] > arr[i + 1] > ... > arr[arr.length - 1]
     * 给出一个整数数组 arr，返回最长山脉子数组的长度。如果不存在山脉子数组，返回 0 。
     * tips:
     * 1 <= arr.length <= 104
     * 0 <= arr[i] <= 104
     * @param: arr
     * @return int
     * @author marks
     * @CreateDate: 2026/02/11 16:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestMountain(int[] arr) {
        int result;
        result = method_01(arr);
        result = method_02(arr);
        return result;
    }

    /**
     * @Description: 双指针
     * AC: 2ms/46.63MB
     * @param: arr
     * @return int
     * @author marks
     * @CreateDate: 2026/02/11 16:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] arr) {
        int ans = 0;
        int n = arr.length;
        for (int i = 1; i < n - 1; i++) {
            if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {
                int left = i - 1;
                int right = i + 1;
                while (left > 0 && arr[left] > arr[left - 1]) {
                    left--;
                }
                while (right < n - 1 && arr[right] > arr[right + 1]) {
                    right++;
                }
                ans = Math.max(ans, right - left + 1);
            }
        }
        return ans;
    }

    /**
     * @Description:
     * 1. 子数组需要连续吗? 看起来是需要一个连续子数组
     * 2. 两次遍历, 构建int[] prev 和 suffix, 分别记录升序和降序的长度
     * AC: 3ms/46.3MB
     * @param: arr
     * @return int
     * @author marks
     * @CreateDate: 2026/02/11 16:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr) {
        int n = arr.length;
        int[] prev = new int[n];
        prev[0] = 1;
        for (int i = 1; i < n; i++) {
            if (arr[i] > arr[i - 1]) {
                prev[i] = prev[i - 1] + 1;
            } else {
                prev[i] = 1;
            }
        }
        int[] suffix = new int[n];
        suffix[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            if (arr[i] > arr[i + 1]) {
                suffix[i] = suffix[i + 1] + 1;
            } else {
                suffix[i] = 1;
            }
        }
        int ans = 0;
        for (int i = 1; i < n - 1; i++) {
            if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {
                ans = Math.max(ans, prev[i - 1] + suffix[i + 1] + 1);
            }
        }
        return ans;
    }

}
