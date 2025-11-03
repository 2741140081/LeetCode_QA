package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3389 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/30 14:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3389 {

    /***
     * @Description:
     * 给你一个字符串 s 。
     * 如果字符串 t 中的字符出现次数相等，那么我们称 t 为 好的 。
     * 你可以执行以下操作 任意次 ：
     * 从 s 中删除一个字符。
     * 往 s 中添加一个字符。
     * 将 s 中一个字母变成字母表中下一个字母。
     * 注意 ，第三个操作不能将 'z' 变为 'a' 。
     *
     * 请你返回将 s 变 好 的 最少 操作次数。
     *
     * tips:
     * 1 <= s.length <= 2 * 10^4
     * s 只包含小写英文字母。
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2025/10/30 14:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int makeStringGood(String s) {
        int result;
        result = method_01(s);
//        result = method_02(s);
        return result;
    }


    /***
     * @Description:
     * 1. 统计字符串中每种字符出现的次数 int[] count 来记录吧
     * 2. 要使得字符串变为好字符串, 定义一个常数 c, 0 <= c <= Max(count[i]), 需要让 count[i] == c 或者 count[i] == 0
     * 3. 3种操作
     * 3.1 删除一个字符, count[i]--
     * 3.2 添加一个字符, count[i]++
     * 3.3 将 s 中一个字母变成字母表中下一个字母, count[i]--, count[i + 1]++
     * 4. 所以需要倒敘遍历字符串, 找到最小的转换次数, 例如当前是i, 并且需要 i + 1是 count[i] < c, 即需要提高的
     * 5. 此时dp[i] = Math.min(min(dp[i]) + min(dp[i + 1]), count[i] > c 并且 sub > c - count[i + 1] ? count[i] - c,
     * count[i] < c 时, c - count[i + 1] = sub, sub <= count[i] ? count[i] : c - count[i + 1] )
     * AC: 18ms/46.32MB
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2025/10/30 14:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int[] count = new int[26];

        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
        }
        int c = Arrays.stream(count).max().getAsInt();
        int ans = s.length(); // c = 0, all the characters to zero, there is nothing.
        while (c > 0) {
            int[] dp = new int[27];
            dp[25] = Math.min(count[25], Math.abs(c - count[25]));
            for (int i = dp.length - 3; i >= 0; i--) {
                int curr = count[i];
                int next = count[i + 1];
                dp[i] = dp[i + 1] + Math.min(curr, Math.abs(curr - c));
                if (next < c) {
                    // the next need to increase, this is type 3
                    int sub = curr > c ? c : 0;
                    // if curr > target, need the curr to decrease, this is type 1, sub = curr - c
                    // and the nextSub = c - next, so we need to compare the sub and nextSub, which one is bigger
                    // that is dp[i] = Math.max(sub, nextSub);
                    // if curr < target, need the curr to decrease, this is type 1, sub = c - curr
                    // so the same to compare the sub and nextSub, which one is bigger
                    // that one question is need to compare the curr is bigger or smaller than c
                    dp[i] = Math.min(dp[i], dp[i + 2] + Math.max(curr - sub, c - next));
                }
            }
            if (dp[0] == 1) {
                System.out.println("ans = " + ans);
            }
            ans = Math.min(ans, dp[0]);
            c--;
        }
        return ans;
    }

    /***
     * @Description:
     * 1. 我想到了, 为什么是 23 是最小的
     * 2. [8, 0, 9, 6, 4, 0, 9, 0, 25, 0]
     * 3. [1, 0, 0, 3, 4, 0, 0, 0, 16, 0]
     * 4. [1, 0, 0, 6, 0, 0, 0, 0, 16, 0]
     * 1 + 6 + 16 = 23
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2025/10/31 10:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String s) {
        int[] cnt = new int[26];
        for (char b : s.toCharArray()) {
            cnt[b - 'a']++;
        }
        int m = Arrays.stream(cnt).max().getAsInt();

        int ans = s.length(); // target = 0 时的答案
        int[] f = new int[27];
        for (int target = 1; target <= m; target++) {
            f[25] = Math.min(cnt[25], Math.abs(cnt[25] - target));
            for (int i = 24; i >= 0; i--) {
                int x = cnt[i];
                int y = cnt[i + 1];
                // 单独操作 x（变成 target 或 0）
                f[i] = f[i + 1] + Math.min(x, Math.abs(x - target));
                // x 变成 target 或 0，y 变成 target
                if (y < target) { // 只有当 y 需要变大时，才去执行第三种操作
                    int t = x > target ? target : 0;
                    f[i] = Math.min(f[i], f[i + 2] + Math.max(x - t, target - y));
                }
            }

            ans = Math.min(ans, f[0]);
        }
        return ans;
    }

}
