package com.marks.leetcode.double_pointer.single_sequence;

import java.util.Arrays;

/**
 * <p>项目名称:50 80/5  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/11 11:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1385 {
    /**
     * @Description: [
     * 给你两个整数数组 arr1 ， arr2 和一个整数 d ，请你返回两个数组之间的 距离值 。
     *
     * 「距离值」 定义为符合此距离要求的元素数目：对于元素 arr1[i] ，不存在任何元素 arr2[j] 满足 |arr1[i]-arr2[j]| <= d 。
     * ]
     * @param arr1 
     * @param arr2 
     * @param d 
     * @return int
     * @author marks
     * @CreateDate: 2024/11/11 15:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        int result;
        result = method_01(arr1, arr2, d);
        return result;
    }

    /**
     * @Description: [
     * 输入：arr1 = [4,5,8], arr2 = [10,9,1,8], d = 2
     * 输出：2
     * 解释：
     * 对于 arr1[0]=4 我们有：
     * |4-10|=6 > d=2
     * |4-9|=5 > d=2
     * |4-1|=3 > d=2
     * |4-8|=4 > d=2
     * 所以 arr1[0]=4 符合距离要求
     *
     * AC:7ms/43.18MB
     * ]
     * @param arr1
     * @param arr2
     * @param d
     * @return int
     * @author marks
     * @CreateDate: 2024/11/11 15:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr1, int[] arr2, int d) {
        int m = arr1.length;
        int n = arr2.length;
        int ans = 0;
        // 先排序
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        boolean flag = true;
        for (int i = 0; i < m; i++) {
            flag = true;
            for (int j = 0; j < n; j++) {
                if (Math.abs(arr1[i] - arr2[j]) <= d) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                ans++;
            }
        }
        return ans;
    }
}
