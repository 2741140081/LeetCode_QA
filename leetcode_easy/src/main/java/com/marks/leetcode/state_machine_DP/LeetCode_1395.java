package com.marks.leetcode.state_machine_DP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/9 15:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1395 {
    int maxN;
    int[] c;
    List<Integer> disc;
    int[] iLess;
    int[] iMore;
    int[] kLess;
    int[] kMore;
    /**
     * @Description: [
     * n 名士兵站成一排。每个士兵都有一个 独一无二 的评分 rating 。
     *
     * 从中选出 3 个士兵组成一个作战单位，规则如下：
     *
     * 从队伍中选出下标分别为 i、j、k 的 3 名士兵，他们的评分分别为 rating[i]、rating[j]、rating[k]
     * 作战单位需满足： rating[i] < rating[j] < rating[k] 或者 rating[i] > rating[j] > rating[k] ，其中  0 <= i < j < k < n
     * 请你返回按上述条件组建的作战单位的方案数。
     * tips:
     * n == rating.length
     * 3 <= n <= 1000
     * 1 <= rating[i] <= 10^5
     * rating 中的元素都是唯一的
     * ]
     * @param rating
     * @return int
     * @author marks
     * @CreateDate: 2024/9/9 15:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numTeams(int[] rating) {
        int result = 0;
//        result = method_01(rating);
//        result = method_02(rating);
        result = method_03(rating);
        return result;
    }

    /**
     * @Description: [官方题解:离散化树状数组
     * AC:12ms/42.46MB
     * ]
     * @param rating
     * @return int
     * @author marks
     * @CreateDate: 2024/9/9 16:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_03(int[] rating) {
        int n = rating.length;
        maxN = n + 2;
        c = new int[maxN];
        disc = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            disc.add(rating[i]);
        }
        disc.add(-1);
        Collections.sort(disc);
        iLess = new int[n];
        iMore = new int[n];
        kLess = new int[n];
        kMore = new int[n];

        for (int i = 0; i < n; i++) {
            int id = getId(rating[i]);
            iLess[i] = get(id);
            iMore[i] = get(n + 1) - get(id);
            add(id, 1);
        }

        c = new int[maxN];
        for (int i = n - 1; i >= 0; i--) {
            int id = getId(rating[i]);
            kLess[i] = get(id);
            kMore[i] = get(n + 1) - get(id);
            add(id, 1);
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans += iLess[i] * kMore[i] + kLess[i] * iMore[i];
        }
        return ans;
    }

    /**
     * @Description: [二分法]
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/9/9 16:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int getId(int target) {
        int low = 0;
        int high = disc.size() - 1;
        while (low < high) {
            int mid = (high - low) / 2 + low;
            if (disc.get(mid) < target) {
                low = mid + 1;
            }else {
                high = mid;
            }
        }
        return low;
    }

    private int get(int p) {
        int r = 0;
        while (p > 0) {
            r += c[p];
            p -= lowbit(p);
        }
        return r;
    }

    private void add(int p, int v) {
        while (p < maxN) {
            c[p] += v;
            p += lowbit(p);
        }
    }

    private int lowbit(int x) {
        return x & (-x);
    }



    /**
     * @Description: [
     * 官方题解: 枚举中间点
     *
     * AC:23ms/41.17MB
     * ]
     * @param rating
     * @return int
     * @author marks
     * @CreateDate: 2024/9/9 15:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] rating) {
        int n = rating.length;
        int ans = 0;
        for (int j = 1; j < n - 1; j++) {
            int less_i = 0;
            int less_k = 0;
            int more_i = 0;
            int more_k = 0;
            for (int i = 0; i < j; i++) {
                if (rating[i] < rating[j]) {
                    less_i++;
                }else {
                    more_i++;
                }
            }

            for (int k = j + 1; k < n; k++) {
                if (rating[j] < rating[k]) {
                    less_k++;
                }else {
                    more_k++;
                }
            }
            ans += less_i * less_k + more_i * more_k;
        }
        return ans;
    }

    /**
     * @Description: [
     * 试一下暴力
     * AC:2410ms/41.21MB
     * ]
     * @param rating
     * @return int
     * @author marks
     * @CreateDate: 2024/9/9 15:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] rating) {
        int n = rating.length;
        int ans = 0;
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                if (rating[i] > rating[j]) {
                    for (int k = j + 1; k < n; k++) {
                        if (rating[j] > rating[k]) {
                            ans++;
                        }
                    }
                }else {
                    for (int k = j + 1; k < n; k++) {
                        if (rating[j] < rating[k]) {
                            ans++;
                        }
                    }
                }

            }
        }
        return ans;
    }
}
