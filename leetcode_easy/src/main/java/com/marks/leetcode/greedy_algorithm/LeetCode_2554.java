package com.marks.leetcode.greedy_algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/26 10:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2554 {
    /**
     * @Description: [功能描述]
     * @param banned
     * @param n
     * @param maxSum
     * @return int
     * @author marks
     * @CreateDate: 2025/3/26 10:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxCount(int[] banned, int n, int maxSum) {
        int result;
        result = method_01(banned, n, maxSum);
        return result;
    }

    /**
     * @Description: [功能描述]
     * @param banned
     * @param n
     * @param maxSum
     * @return int
     * @author marks
     * @CreateDate: 2025/3/26 10:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] banned, int n, int maxSum) {
        List<Integer> list = Arrays.stream(banned).boxed().collect(Collectors.toList());
        int ans = 0;

        return ans;
    }
}
