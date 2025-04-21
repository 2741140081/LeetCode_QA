package com.marks.leetcode.data_structure.difference;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/17 16:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1893 {
    public boolean isCovered(int[][] ranges, int left, int right) {
        boolean result;
        result = method_01(ranges, left, right);
        return result;
    }

    /**
     * @Description:
     * 差分
     * AC: 0ms/40.85MB
     * @param ranges
     * @param left
     * @param right
     * @return boolean
     * @author marks
     * @CreateDate: 2025/2/17 16:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[][] ranges, int left, int right) {
        int maxValue = 0;
        for (int[] range : ranges) {
            maxValue = Math.max(maxValue, range[1]);
        }
        int[] diff = new int[maxValue + 2];
        for (int[] range : ranges) {
            diff[range[0]]++;
            diff[range[1] + 1]--;
        }
        int count = 0;
        if (maxValue < right) {
            return false;
        }
        for (int i = 1; i <= maxValue; i++) {
            count += diff[i];
            if (count <= 0 && (i >= left && i <= right)) {
                return false;
            }
        }
        return true;
    }
}
