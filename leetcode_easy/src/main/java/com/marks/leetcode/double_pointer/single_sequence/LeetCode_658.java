package com.marks.leetcode.double_pointer.single_sequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/4 10:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_658 {
    /**
     * @Description: [
     * tips:
     * 1 <= k <= arr.length
     * 1 <= arr.length <= 10^4
     * arr 按 升序 排列
     * -10^4 <= arr[i], x <= 10^4
     * ]
     * @param arr
     * @param k
     * @param x
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2024/11/4 11:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> result;
        result = method_01(arr, k, x);
        return result;
    }

    /**
     * @Description: [
     * AC:4ms/44.62MB
     * ]
     * @param arr
     * @param k
     * @param x
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2024/11/4 11:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int[] arr, int k, int x) {
        int n = arr.length;
        ArrayList<Integer> list = new ArrayList<>();
        int left = 0;
        int right = n - 1;
        // right - left + 1 表示 arr[left ~ right]区间中存在的个数
        while (left < right && (right - left + 1) > k) {
            int temp_left = Math.abs(arr[left] - x);
            int temp_right = Math.abs(arr[right] - x);
            if (temp_left > temp_right ){
                left++;
            } else if (temp_left < temp_right) {
                right--;
            } else {
                // temp_left == temp_right
                right--;
            }
        }
        for (int i = left; i <= right; i++) {
            list.add(arr[i]);
        }
        return list;
    }
}
