package com.marks.leetcode.daily_question;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1291 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/13 10:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1291 {

    /**
     * @Description:
     * 我们定义「顺次数」为：每一位上的数字都比前一位上的数字大 1 的整数。
     * 请你返回由 [low, high] 范围内所有顺次数组成的 有序 列表（从小到大排序）。
     *
     * tips:
     * 10 <= low <= high <= 10^9
     * @param: low
     * @param: high
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/07/13 10:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> result;
        result = method_01(low, high);
        return result;
    }

    /**
     * @Description:
     * 1. 先确定low 是多少位数字长度, 以及 high 是多少位长度, 然后进行对长度进行从小到大的for 循环遍历
     * 2. 假设当前长度是 int len; 需要先创建 int[] arr = new int[arr]; 长度的数组, 并且给arr[] 进行初始值赋值, 例如 len = 3,
     * 那么 arr = {1, 2, 3}; 最大循环次数 9 - 3 = 6 次, 即使用 9 - len 为arr 数组的循环次数. 循环体中给数组中的值增加1, 并且判断是否在 low 和 high 范围内,
     * 3. 如果并且low 的最小值是 12, high 的最大值是 123456789, 最大长度是 9 位.
     * AC: 0ms/41.43MB
     * @param: low
     * @param: high
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/07/13 11:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int low, int high) {
        low = Math.max(low, 12);
        high = Math.min(high, 123456789);
        int left = getLength(low);
        int right = getLength(high);
        List<Integer> ans = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            int[] arr = new int[i];
            // 初始化
            for (int j = 0; j < i; j++) {
                arr[j] = j + 1;
            }
            // 将数组转成数组, 并且判断是否在low 和 high
            arrToInteger(arr, ans, low, high);
            // 剩余的循环次数
            int temp = 9 - arr[i - 1];
            for (int j = 0; j < temp; j++) {
                for (int k = 0; k < i; k++) {
                    arr[k]++;
                }
                arrToInteger(arr, ans, low, high);
            }
        }

        return ans;
    }

    private void arrToInteger(int[] arr, List<Integer> ans, int low, int high) {
        int num = 0;
        for (int j : arr) {
            num = num * 10 + j;
        }
        if (num >= low && num <= high) {
            ans.add(num);
        }
    }

    private int getLength(int num) {
        int ans = 0;
        while (num != 0) {
            num /= 10;
            ans++;
        }
        return ans;
    }

}
