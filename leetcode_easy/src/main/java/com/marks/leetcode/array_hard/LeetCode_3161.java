package com.marks.leetcode.array_hard;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3161 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/17 10:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3161 {

    /**
     * @Description:
     * 有一条无限长的数轴，原点在 0 处，沿着 x 轴 正 方向无限延伸。
     * 给你一个二维数组 queries ，它包含两种操作：
     * 操作类型 1 ：queries[i] = [1, x] 。在距离原点 x 处建一个障碍物。数据保证当操作执行的时候，位置 x 处 没有 任何障碍物。
     * 操作类型 2 ：queries[i] = [2, x, sz] 。判断在数轴范围 [0, x] 内是否可以放置一个长度为 sz 的物块，这个物块需要 完全 放置在范围 [0, x] 内。
     * 如果物块与任何障碍物有重合，那么这个物块 不能 被放置，但物块可以与障碍物刚好接触。注意，你只是进行查询，并 不是 真的放置这个物块。每个查询都是相互独立的。
     * 请你返回一个 boolean 数组results ，如果第 i 个操作类型 2 的操作你可以放置物块，那么 results[i] 为 true ，否则为 false 。
     *
     * tips:
     * 1 <= queries.length <= 15 * 10^4
     * 2 <= queries[i].length <= 3
     * 1 <= queries[i][0] <= 2
     * 1 <= x, sz <= min(5 * 10^4, 3 * queries.length)
     * 输入保证操作 1 中，x 处不会有障碍物。
     * 输入保证至少有一个操作类型 2 。
     * @param: queries
     * @return java.util.List<java.lang.Boolean>
     * @author marks
     * @CreateDate: 2026/08/17 10:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Boolean> getResults(int[][] queries) {
        List<Boolean> result;
        result = method_01(queries);
        return result;
    }

    /**
     * @Description:
     * 1. 应该是一个动态的线段树, 用于查询区间的最大长度.
     * 2. 障碍物只是一个点, 可以放置的距离即为两个点之间的长度 right - left
     * todo, 当前还是不能理解如何使用动态开点线段树. 当前代码是cv
     * @param: queries
     * @return java.util.List<java.lang.Boolean>
     * @author marks
     * @CreateDate: 2026/08/17 10:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Boolean> method_01(int[][] queries) {
        int n = 0;
        for (int[] query : queries) {
            n = Math.max(n, query[1]);
        }

        Node root = new Node();
        root.max = n;
        root.lv = -1;
        root.rv = -1;

        List<Boolean> ans = new ArrayList<>();
        for (int[] query : queries) {
            if (query[0] == 1) {
                // 障碍物
                update(root, 0, n, query[1]);
            } else {
                // 查询
                int right = query[1];
                int[] maxLen = query(root, 0, n, 0, right);

                ans.add(maxLen[1] >= query[2] || maxLen[0] >= query[2] || maxLen[2] >= query[2]);
            }
        }

        return ans;
    }


    class Node {
        int max;
        int lv;
        int rv;
        Node left;
        Node right;
    }

    private void update(Node root, int l, int r, int x) {
        if (l > x || r < x) {
            return;
        }
        if (l == r) {
            root.max = 0;
            root.lv = 0;
            root.rv = 0;
            return;
        }
        int m = l + ((r - l) >> 1);
        // pushDown
        if (root.left == null) {
            root.left = new Node();
            root.left.max = m - l;
            root.left.lv = -1;
            root.left.rv = -1;
        }
        if (root.right == null) {
            root.right = new Node();
            root.right.max = r - m - 1;
            root.right.lv = -1;
            root.right.rv = -1;
        }
        update(root.left, l, m, x);
        update(root.right, m + 1, r, x);
        // pushUp
        // 这题最恶心的是两个相邻区间之间有一个长度为 1 的区间，需要单独计算
        int[] ans = pushUp(new int[]{root.left.lv, root.left.max, root.left.rv}, new int[]{root.right.lv, root.right.max, root.right.rv});
        root.lv = ans[0];
        root.max = ans[1];
        root.rv = ans[2];
    }
    // [lv, max, rv]
    private int[] pushUp(int[] left, int[] right) {
        int[] ans = new int[3];
        if (left[0] == -1) {
            if (right[0] == -1) {
                ans[0] = -1;
            } else {
                ans[0] = left[1] + right[0] + 1;
            }
        } else {
            ans[0] = left[0];
        }

        if (right[2] == -1) {
            if (left[2] == -1) {
                ans[2] = -1;
            } else {
                ans[2] = right[1] + left[2] + 1;
            }
        } else {
            ans[2] = right[2];
        }
        int t = 0;
        if (left[2] == -1) {
            t += left[1];
        } else {
            t += left[2];
        }
        if (right[0] == -1) {
            t += right[1] + 1;
        } else {
            t += right[0] + 1;
        }
        ans[1] = Math.max(t, Math.max(left[1], right[1]));
        return ans;
    }

    // return: [lv,max,rv]
    private int[] query(Node root, int l, int r, int s, int e) {
        if (root == null) {
            root = new Node();
            root.lv = -1;
            root.max = r - l;
            root.rv = -1;
        }
        if (l >= s && r <= e) {
            return new int[]{root.lv, root.max, root.rv};
        }
        int m = l + ((r - l) >> 1);
        // 要分类讨论，因为有个 +1 的脏数据
        if (e <= m) {
            return query(root.left, l, m, s, e);
        }
        if (s > m) {
            return query(root.right,m + 1, r, s, e);
        }
        int[] left = query(root.left, l, m, s, e);
        int[] right = query(root.right, m + 1, r, s, e);

        return pushUp(left, right);
    }

}
