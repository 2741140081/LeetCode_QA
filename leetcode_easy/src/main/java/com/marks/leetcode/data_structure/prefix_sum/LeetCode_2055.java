package com.marks.leetcode.data_structure.prefix_sum;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/13 14:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2055 {
    /**
     * @Description:
     * 给你一个长桌子，桌子上盘子和蜡烛排成一列。给你一个下标从 0 开始的字符串 s ，它只包含字符 '*' 和 '|' ，其中 '*' 表示一个 盘子 ，'|' 表示一支 蜡烛 。
     * 同时给你一个下标从 0 开始的二维整数数组 queries ，其中 queries[i] = [lefti, righti] 表示 子字符串 s[lefti...righti] （包含左右端点的字符）。
     * 对于每个查询，你需要找到 子字符串中 在 两支蜡烛之间 的盘子的 数目 。如果一个盘子在 子字符串中 左边和右边 都 至少有一支蜡烛，那么这个盘子满足在 两支蜡烛之间 。
     *
     * 比方说，s = "||**||**|*" ，查询 [3, 8] ，表示的是子字符串 "*||**|" 。子字符串中在两支蜡烛之间的盘子数目为 2 ，子字符串中右边两个盘子在它们左边和右边 都 至少有一支蜡烛。
     * 请你返回一个整数数组 answer ，其中 answer[i] 是第 i 个查询的答案。
     * tips:
     * 3 <= s.length <= 10^5
     * s 只包含字符 '*' 和 '|' 。
     * 1 <= queries.length <= 10^5
     * queries[i].length == 2
     * 0 <= lefti <= righti < s.length
     * @param s
     * @param queries
     * @return int[]
     * @author marks
     * @CreateDate: 2025/1/13 14:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] platesBetweenCandles(String s, int[][] queries) {
        int[] result;
        result = method_01(s, queries);
        result = method_02(s, queries);
        return result;
    }

    /**
     * @Description:
     * 查看官方题解,
     * 优化method_01, 添加预处理, [|, *, *, |, *, *, |]
     * 添加int[] left 记录左侧的|的坐标
     * 9ms/79.36MB
     * @param s
     * @param queries
     * @return int[]
     * @author marks
     * @CreateDate: 2025/1/13 14:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(String s, int[][] queries) {
        int n = s.length();
        int[] preSum = new int[n];
        for (int i = 0, sum = 0; i < n; i++) {
            if (s.charAt(i) == '*') {
                sum++;
            }
            preSum[i] = sum;
        }
        int[] left = new int[n];
        for (int i = 0, temp = -1; i < n; i++) {
            if (s.charAt(i) == '|') {
                temp = i;
            }
            left[i] = temp;
        }

        int[] right = new int[n];
        /*
        "**|**|***|**"
        left = [-1, -1, 2, 2, 2, 5, 5, 5, 5, 9]
        right = [2, 2, 2, 5, 5, 5, 9, 9, 9, 9]
        [[2,5],[5,9]]
         */
        for (int i = n - 1, temp = -1; i >= 0; i--) {
            if (s.charAt(i) == '|') {
                temp = i;
            }
            right[i] = temp;
        }
        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int start =  queries[i][0];
            int end = queries[i][1];
            int x = right[start], y = left[end];
            ans[i] = x == -1 || y == -1 || x >= y ? 0 : preSum[y] - preSum[x];
        }
        return ans;
    }

    /**
     * @Description:
     * AC:1487ms/77.03MB
     * @param s
     * @param queries
     * @return int[]
     * @author marks
     * @CreateDate: 2025/1/13 14:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(String s, int[][] queries) {
        int n = s.length();
        int[] pre = new int[n];
        int left = 0;
        int right = n - 1;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '|') {
                list.add(i);
            }
        }
        if (list.size() < 2 || list.size() == n) {
            return new int[queries.length];
        }

        for (int i = left + 1; i <= right; i++) {
            pre[i] = pre[i - 1] + (s.charAt(i) == '*' ? 1 : 0);
        }
        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int start = queries[i][0];
            int end = queries[i][1];
            if (((end - start + 1) - (pre[end] - pre[start] + (s.charAt(start) == '*' ? 1 : 0))) < 2) {
                ans[i] = 0;
                continue;
            }
            int index = 0;
            while (index < list.size()) {
                if (list.get(index) >= start) {
                    break;
                }
                index++;
            }
            start = list.get(index);
            index = list.size() - 1;
            while (index >= 0) {
                if (list.get(index) <= end) {
                    break;
                }
                index++;
            }
            end = list.get(index);
            ans[i] = pre[end] - pre[start];
        }
        return ans;
    }
}
