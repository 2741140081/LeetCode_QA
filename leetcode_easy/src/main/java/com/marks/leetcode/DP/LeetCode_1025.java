package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1025 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/3 11:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1025 {

    public boolean divisorGame(int n) {
        boolean result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * n = 4
     * A: 1, 2, => n1 = (3, 2)
     * B: n1 = 3; b:1, n2 = 2 A win
     * B: n1 = 2; b:1, n2 = 1 B win
     * AC: 0ms/41.52MB
     * @param: n
     * @return boolean
     * @author marks
     * @CreateDate: 2025/11/03 11:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int n) {

        return n % 2 == 0;
    }

}
