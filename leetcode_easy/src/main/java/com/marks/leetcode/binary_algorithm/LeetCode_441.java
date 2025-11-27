package com.marks.leetcode.binary_algorithm;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_441 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/27 11:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_441 {

    /**
     * @Description: [方法描述]
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/27 11:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int arrangeCoins(int n) {
        int result;
        result = method_01(n);
        result = method_02(n);
        return result;
    }

    private int method_02(int n) {
        int left = 1, right = n;
        while (left < right) {
            int mid = left + (right - left + 1) / 2;
            if ((long) mid * (mid + 1) <= (long) 2 * n) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    /**
     * @Description:
     * AC: 8ms/41.89MB
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/27 11:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        int i = 1;
        int sum = 0;
        while (n - sum >= i) {
            sum += i;
            i++;
        }
        return i - 1;
    }

}
