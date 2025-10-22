package com.marks.leetcode.DP;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/20 11:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3316 {

    
    /**
     * @Description:
     * 给你一个长度为 n 的字符串 source ，一个字符串 pattern 且它是 source 的 子序列 ，
     * 和一个 有序 整数数组 targetIndices ，整数数组中的元素是 [0, n - 1] 中 互不相同 的数字。
     *
     * 定义一次 操作 为删除 source 中下标在 idx 的一个字符，且需要满足：
     *
     * idx 是 targetIndices 中的一个元素。
     * 删除字符后，pattern 仍然是 source 的一个 子序列 。
     * 执行操作后 不会 改变字符在 source 中的下标位置。比方说，如果从 "acb" 中删除 'c' ，下标为 2 的字符仍然是 'b' 。
     *
     * 请你返回 最多 可以进行多少次删除操作。
     *
     * 子序列指的是在原字符串里删除若干个（也可以不删除）字符后，不改变顺序地连接剩余字符得到的字符串。
     *
     * tips:
     * 1 <= n == source.length <= 3 * 10^3
     * 1 <= pattern.length <= n
     * 1 <= targetIndices.length <= n
     * targetIndices 是一个升序数组。
     * 输入保证 targetIndices 包含的元素在 [0, n - 1] 中且互不相同。
     * source 和 pattern 只包含小写英文字母。
     * 输入保证 pattern 是 source 的一个子序列。
     * @param source 
     * @param pattern 
     * @param targetIndices
     * @return int
     * @author marks
     * @CreateDate: 2025/10/20 11:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxRemovals(String source, String pattern, int[] targetIndices) {
        int result;
        result = method_01(source, pattern, targetIndices);
        return result;
    }

    
    /**
     * @Description:
     * 输入：source = "abbaa", pattern = "aba", targetIndices = [0,1,2]
     * 输出：1
     * 1. 在targetIndices[] 中找到最多的删除次数,
     * @param source 
     * @param pattern 
     * @param targetIndices 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/20 11:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String source, String pattern, int[] targetIndices) {
        Set<Integer> set = new HashSet<>();
        for (int targetIndex : targetIndices) {
            set.add(targetIndex);
        }
        int n = source.length();
        int m = pattern.length();
        int[][] dp = new int[n][m + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = -1;
            }
        }
        int ans = dfs(source.toCharArray(), pattern.toCharArray(), n - 1, m - 1, set, dp);
        
        return ans;
    }

    private int dfs(char[] sourceArray, char[] patternArray, int i, int j, Set<Integer> set, int[][] dp) {
        if (i < j) {
            // 不合法状态, 即pattern 的长度比source的长度长
            return Integer.MIN_VALUE;
        }
        if (i < 0) {
            return 0;
        }

        if (dp[i][j] != -1) { // 已经计算过
            return dp[i][j];
        }
        int res = dfs(sourceArray, patternArray, i - 1, j, set, dp) + (set.contains(i) ? 1 : 0);
        if (j >= 0 && sourceArray[i] == patternArray[j]) {
            res = Math.max(res, dfs(sourceArray, patternArray, i - 1, j - 1, set, dp));
        }

        return res;
    }

}
