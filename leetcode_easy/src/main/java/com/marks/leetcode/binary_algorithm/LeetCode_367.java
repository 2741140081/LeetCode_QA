package com.marks.leetcode.binary_algorithm;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_367 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/1 11:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_367 {

    public boolean isPerfectSquare(int num) {
        boolean result;
        result = method1(num);
        return result;
    }

    /**
     * @Description:
     * 需要用long 进行存储, 使用int 会发生溢出
     * AC: 0ms/41.41MB
     * @param: num
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/01 11:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method1(int num) {
        if (num == 1) {
            return true;
        } else {
            // num > 1
            long left = 1;
            long right = num / 2;
            while (left <= right) {
                long mid = left + (right - left) / 2;
                if (mid * mid == num) {
                    return true;
                } else if (mid * mid < num) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return false;
    }

}
