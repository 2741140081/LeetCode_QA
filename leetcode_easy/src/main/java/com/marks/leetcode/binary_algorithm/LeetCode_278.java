package com.marks.leetcode.binary_algorithm;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_278 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/27 10:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_278 {

    public int firstBadVersion(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * AC: 13ms/41.14MB
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/27 10:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        int left = 1, right = n;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (isBadVersion(mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private boolean isBadVersion(int mid) {
        return false;
    }

}
