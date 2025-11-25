package com.marks.leetcode.DP;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_526 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/24 15:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_526 {

    /**
     * @Description:
     * 假设有从 1 到 n 的 n 个整数。
     * 用这些整数构造一个数组 perm（下标从 1 开始），只要满足下述条件 之一 ，该数组就是一个 优美的排列 ：
     * perm[i] 能够被 i 整除
     * i 能够被 perm[i] 整除
     * 给你一个整数 n ，返回可以构造的 优美排列 的 数量 。
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/24 15:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countArrangement(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    private List<Integer>[] lists;
    private boolean[] used;
    private int ans;


    /**
     * @Description:
     * int[] perm = new int[n + 1]; perm[i] = j, prem[i] % i == 0 || i % j == 0, 只要满足条件之一即可
     * AC: 43ms/45.50MB
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/24 15:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        lists = new List[n + 1];
        used = new boolean[n + 1];
        ans = 0;
        for (int i = 1; i <= n; i++) {
            lists[i] = new ArrayList<>();
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i % j == 0 || j % i == 0) {
                    lists[i].add(j);
                }
            }
        }
        backTracking(1, n);
        return ans;
    }

    private void backTracking(int index, int n) {
        if (index > n) {
            ans++;
            return;
        }
        for (int i : lists[index]) {
            if (!used[i]) {
                used[i] = true;
                backTracking(index + 1, n);
                used[i] = false;
            }
        }
    }
}
