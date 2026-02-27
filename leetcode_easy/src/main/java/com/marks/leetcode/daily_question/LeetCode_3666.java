package com.marks.leetcode.daily_question;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3666 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/27 10:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3666 {

    /**
     * @Description:
     * 给你一个二进制字符串 s 和一个整数 k。
     * 在一次操作中，你必须选择 恰好 k 个 不同的 下标，并将每个 '0' 翻转 为 '1'，每个 '1' 翻转为 '0'。
     * 返回使字符串中所有字符都等于 '1' 所需的 最少 操作次数。如果不可能，则返回 -1。
     * tips:
     * 1 <= s.length <= 10^5
     * s[i] 的值为 '0' 或 '1'。
     * 1 <= k <= s.length
     * @param: s
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/02/27 10:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minOperations(String s, int k) {
        int result;
        result = method_01(s, k);
        result = method_02(s, k);
        return result;
    }

    /**
     * @Description:
     * 1. 现在不太想看题解, 待后续处理 need todo
     * @param: s
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/02/27 11:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private int method_02(String s, int k) {
        int n = s.length(), m = 0;
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        List<TreeSet<Integer>> nodeSets = new ArrayList<>();
        nodeSets.add(new TreeSet<>());
        nodeSets.add(new TreeSet<>());
        for (int i = 0; i <= n; i++) {
            nodeSets.get(i % 2).add(i);
            if (i < n && s.charAt(i) == '0') {
                m++;
            }
        }
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(m);
        dist[m] = 0;
        nodeSets.get(m % 2).remove(m);
        while (!q.isEmpty()) {
            m = q.poll();
            int c1 = Math.max(k - n + m, 0), c2 = Math.min(m, k);
            int lnode = m + k - 2 * c2, rnode = m + k - 2 * c1;
            TreeSet<Integer> nodeSet = nodeSets.get(lnode % 2);
            for (Integer m2 = nodeSet.ceiling(lnode); m2 != null && m2 <= rnode; m2 = nodeSet.ceiling(lnode)) {
                dist[m2] = dist[m] + 1;
                q.offer(m2);
                nodeSet.remove(m2);
            }
        }
        return dist[0] == Integer.MAX_VALUE ? -1 : dist[0];
    }

    /**
     * @Description:
     * 1. 选取任意k个索引进行翻转操作, 所以这个s 中 '0' 和 '1' 的顺序没有关系, 只需要关注于 '0' 和 '1' 的数量即可, 分别 sum0 和 sum1 记录 '0' 和 '1' 的数量
     * 2. sum0 > k 时, 取 k 个 '0' 翻转为 '1' sum0 -= k, sum1 += k; 可以用一个while 循环, 直到 sum0 <= k, 并且 int ans 记录次数
     * 3. 次数 0 < sum0 < k时, 将 k 进行分割, [1, k - 1] .... [(k - 1)/2 , (k + 1)/2], int sub = k - sum0; 需要增加sub 个 '0'
     * 4. 将 i 个 '0' 转为1, 将 j 个 '1' 转为 0; 并且 i + j = k; j - i = sub; 求解 i, j => 2 * j = k + sub
     * 2 * j = 2 * k - sum0, 要使得 j 存在, 需要 2 * k - sum0 是一个偶数, 就需要 sum0 是偶数, j = (2 * k - sum0) / 2; i = k - j;
     * 5. 同时需要验证 0 <= i <= sum0, 0 <= j <= sum1; ans++, 只需要1次转换; 此时有 sum0 = k, 还需要一次转换将所有sum0 转为 '1'; ans++;
     * E1:
     * 输入： s = "0101", k = 3
     * sum0 = 2, sum1 = 2, k = 3; j = (2 * k - sum0) / 2 = 2; i = 1;
     * 6. 假设 sum0 不是一个偶数, 但是可以通过转换为偶数, 满足条件; 这种改如何进行转换操作? 继续讨论吧
     * 7. sum0, sum1, k; 直接默认k > 2;
     * 8. 存在问题, 直接看题解
     * @param: s
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/02/27 10:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s, int k) {
        int n = s.length();
        char[] chars = s.toCharArray();
        int sum0 = 0, sum1 = 0;
        for (int i = 0; i < n; i++) {
            if (chars[i] == '0') {
                sum0++;
            } else {
                sum1++;
            }
        }
        int ans = 0;
        while (sum0 >= k) {
            ans++;
            sum0 -= k;
            sum1 += k;
        }
        if (sum0 == 0) {
            return ans;
        }
        if (sum0 % 2 == 0) {
            // 判断 j 和 i 是否存在
            int j = (2 * k - sum0) / 2;
            int i = k - j;
            if (0 <= i && i <= sum0 && 0 <= j && j <= sum1) {
                return ans + 2;
            }
        }
        // 特殊情况处理 k = 2
        if (k == 2 && sum0 % 2 == 1) {
            // 无法进行转换, 使得 sum0 变成偶数
            return -1;
        }

        return 0;
    }

}
