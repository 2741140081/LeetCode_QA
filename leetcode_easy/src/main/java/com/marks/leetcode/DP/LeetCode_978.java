package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_978 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/25 16:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_978 {

    /**
     * @Description:
     * 给定一个整数数组 arr ，返回 arr 的 最大湍流子数组的长度 。
     * 如果比较符号在子数组中的每个相邻元素对之间翻转，则该子数组是 湍流子数组 。
     * 更正式地来说，当 arr 的子数组 A[i], A[i+1], ..., A[j] 满足仅满足下列条件时，我们称其为湍流子数组：
     * 若 i <= k < j ：
     * 当 k 为奇数时， A[k] > A[k+1]，且
     * 当 k 为偶数时，A[k] < A[k+1]；
     * 或 若 i <= k < j ：
     * 当 k 为偶数时，A[k] > A[k+1] ，且
     * 当 k 为奇数时， A[k] < A[k+1]。
     * tips:
     * 1 <= arr.length <= 4 * 10^4
     * 0 <= arr[i] <= 10^9
     * @param: arr
     * @return int
     * @author marks
     * @CreateDate: 2026/02/25 16:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxTurbulenceSize(int[] arr) {
        int result;
        result = method_01(arr);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：arr = [9,4,2,10,7,8,8,1,9]
     * 输出：5
     * 解释：arr[1] > arr[2] < arr[3] > arr[4] < arr[5]
     * 1. 4 > 2 < 10 > 7 < 8, 需要注意, 求的是子数组而不是子序列
     * 2. 只需要相邻的符号相反即可
     * @param: arr
     * @return int
     * @author marks
     * @CreateDate: 2026/02/25 16:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr) {
        int ret = 1;
        int n = arr.length;
        int dp0 = 1, dp1 = 1;
        for (int i = 1; i < n; i++) {
            if (arr[i - 1] > arr[i]) {
                dp0 = dp1 + 1;
                dp1 = 1;
            } else if (arr[i - 1] < arr[i]) {
                dp1 = dp0 + 1;
                dp0 = 1;
            } else {
                dp0 = 1;
                dp1 = 1;
            }
            ret = Math.max(ret, dp0);
            ret = Math.max(ret, dp1);
        }
        return ret;
    }

}
