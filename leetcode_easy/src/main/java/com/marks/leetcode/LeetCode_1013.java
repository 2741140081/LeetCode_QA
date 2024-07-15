package com.marks.leetcode;

import java.util.Arrays;

public class LeetCode_1013 {
    /**
     * 对于数组arr[0,2,1,-6,6,-7,9,1,2,0,1]
     * 是否存在下标i, j, 其中i + 1 < j
     * 使得arr[0] + ····· + arr[i] == arr[i+i] + ······ + arr[j-1] == arr[j] + ······ + arr[arr.length - 1] 成立
     * 如果成立 return true
     * else return false
     *
     * example1:
     * 输入：arr = [0,2,1,-6,6,-7,9,1,2,0,1]
     * 输出：true
     * 解释：0 + 2 + 1 = -6 + 6 - 7 + 9 + 1 = 2 + 0 + 1
     *
     * example2:
     * 输入：arr = [0,2,1,-6,6,7,9,-1,2,0,1]
     * 输出：false
     *
     * 提示：
     * 3 <= arr.length <= 5 * 104
     * -104 <= arr[i] <= 104
     *
     * @param arr
     * @return boolean
     */
    public boolean canThreePartsEqualSum(int[] arr) {

        boolean flag = false;
        flag = method_01(arr);
        return flag;
    }

    /**
     * 暴力破解: 遍历数据arr, 通过双重for循环, 分别确定i 和 j
     *
     */
    private boolean method_01(int[] arr) {

        int total = Arrays.stream(arr).sum();
        int sum_i = arr[0];
        for (int i = 1; i < arr.length - 1; i++) {
            if (sum_i * 3 == total) {
                int sum_j = 0;
                for (int j = i; j < arr.length; j++) {
                    if (sum_j == sum_i && (2 * sum_j == total - sum_i) && (j > i)) {
                        // total - sum_i = 2sum_j + sum_j
                        return true;
                    }
                    sum_j += arr[j];
                }
            }
            sum_i += arr[i];
        }

        return false;
    }
}
