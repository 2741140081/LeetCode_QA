package com.marks.leetcode.DP;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_119 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/3 10:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_119 {

    /**
     * @Description:
     * 给定一个非负索引 rowIndex，返回「杨辉三角」的第 rowIndex 行。
     *
     * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
     *
     * 你可以优化你的算法到 O(rowIndex) 空间复杂度吗？
     * @param: rowIndex
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/11/03 10:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> getRow(int rowIndex) {
        List<Integer> result;
        result = method_01(rowIndex);
        return result;
    }

    /**
     * @Description:
     * AC: 3ms/41.98MB
     * @param: rowIndex
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/11/03 10:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int rowIndex) {
        int[][] dp = new int[2][rowIndex];
        dp[0][0] = 1;
        int prev = 0;
        int curr = 1;

        for (int i = 1; i < rowIndex; i++) {
            int maxSize = i + 1;
            for (int j = 0; j < maxSize; j++) {
                if (j == 0 || j == maxSize - 1) {
                    dp[curr][j] = 1;
                } else {
                    dp[curr][j] = dp[prev][j - 1] + dp[prev][j];
                }
            }
            prev = curr;
            curr = 1 - curr;

        }
        return Arrays.stream(dp[prev]).boxed().collect(Collectors.toList());
    }

}
