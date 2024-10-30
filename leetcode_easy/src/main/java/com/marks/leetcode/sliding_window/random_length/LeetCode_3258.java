package com.marks.leetcode.sliding_window.random_length;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/30 15:47
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3258 {
    /**
     * @Description: [
     * 给你一个 二进制 字符串 s 和一个整数 k。
     *
     * 如果一个 二进制字符串 满足以下任一条件，则认为该字符串满足 k 约束：
     *
     * 字符串中 0 的数量最多为 k。
     * 字符串中 1 的数量最多为 k。
     * 返回一个整数，表示 s 的所有满足 k 约束 的子字符串的数量。
     * tips:
     * 1 <= s.length <= 50
     * 1 <= k <= s.length
     * s[i] 是 '0' 或 '1'。
     * ]
     * @param s
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/30 15:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countKConstraintSubstrings(String s, int k) {
        int result = 0;
        result = method_01(s, k);
        return result;
    }

    /**
     * @Description: [
     * AC:1ms/41.37MB
     * ]
     * @param s
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/30 15:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s, int k) {
        int n = s.length();
        int ans = 0;
        int[] cnt = new int[2];
        int left = 0;
        for (int right = 0; right < n; right++) {
            if (s.charAt(right) == '0') {
                cnt[0]++;
            }else {
                cnt[1]++;
            }
            while (cnt[0] > k && cnt[1] > k && left <= right) {
                if (s.charAt(left) == '0') {
                    cnt[0]--;
                }else {
                    cnt[1]--;
                }
                left++;
            }
            if (cnt[0] <= k || cnt[1] <= k) {
                ans += (right - left + 1);
            }
        }
        return ans;
    }
}
