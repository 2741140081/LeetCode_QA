package com.marks.leetcode.DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/11 9:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_397 {

    
    /**
     * @Description:
     * 给定一个正整数 n ，你可以做如下操作：
     *
     * 如果 n 是偶数，则用 n / 2替换 n 。
     * 如果 n 是奇数，则可以用 n + 1或n - 1替换 n 。
     * 返回 n 变为 1 所需的 最小替换次数 。
     *
     * tips:
     * 1 <= n <= 2^31 - 1
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2025/10/11 9:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int integerReplacement(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    
    /**
     * @Description:
     * n = 17
     * 1. 这个题目感觉是动态规划解决
     * 2. n 为奇数时，n + 1 or n - 1, 18 或者 16
     * 3. 感觉用回溯法会更好一些
     * AC: 3ms/39.54MB
     * @param n 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/11 9:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        return dfs(n);
    }

    private int dfs(int n) {
        if (n == 1) {
            return 0;
        }
        if (n % 2 == 0) {
            return 1 + dfs(n / 2);
        } else {
            // 由于 2^31 - 1 + 1 会导致溢出, 所以使用 n / 2 + 1来替代 (n + 1) / 2
            return 2 + Math.min(dfs(n / 2), dfs(n / 2 + 1));
        }

    }

}
