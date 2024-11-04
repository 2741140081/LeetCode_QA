package com.marks.leetcode.double_pointer.single_sequence;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/4 14:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1471 {
    public int[] getStrongest(int[] arr, int k) {
        int[] result;
        result = method_01(arr, k);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：arr = [6,7,11,7,6,8], k = 5
     * 输出：[11,8,6,6,7]
     * AC:36ms/60.45MB
     * ]
     * @param arr
     * @param k
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/4 14:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] arr, int k) {
        int n = arr.length;
        Arrays.sort(arr); // 先排序
        int medina = arr[(n - 1) / 2];
        int left = 0;
        int right = n - 1;
        int[] res = new int[k];
        int pos = 0; //pos 为res 的记录下标

        // 判断特殊情况
        if (k == n) {
            return arr;
        }

        while (left < right && pos < k) {
            int temp_left = Math.abs(arr[left] - medina);
            int temp_right = Math.abs(arr[right] - medina);
            if (temp_left > temp_right) {
                res[pos] = arr[left];
                pos++;
                left++;
            } else if (temp_left < temp_right) {
                res[pos] = arr[right];
                pos++;
                right--;
            }else {
                // temp_left == temp_right
                res[pos] = arr[right];
                pos++;
                right--;
            }
        }
        return res;
    }
}
