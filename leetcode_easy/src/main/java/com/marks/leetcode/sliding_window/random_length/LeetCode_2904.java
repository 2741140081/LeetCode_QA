package com.marks.leetcode.sliding_window.random_length;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/25 9:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2904 {
    /**
     * @Description: [
     * 给你一个二进制字符串 s 和一个正整数 k 。
     *
     * 如果 s 的某个子字符串中 1 的个数恰好等于 k ，则称这个子字符串是一个 美丽子字符串 。
     *
     * 令 len 等于 最短 美丽子字符串的长度。
     *
     * 返回长度等于 len 且字典序 最小 的美丽子字符串。如果 s 中不含美丽子字符串，则返回一个 空 字符串。
     *
     * 对于相同长度的两个字符串 a 和 b ，如果在 a 和 b 出现不同的第一个位置上，a 中该位置上的字符严格大于 b 中的对应字符，则认为字符串 a 字典序 大于 字符串 b 。
     *
     * 例如，"abcd" 的字典序大于 "abcc" ，因为两个字符串出现不同的第一个位置对应第四个字符，而 d 大于 c 。
     *
     * tips:
     * 1 <= s.length <= 100
     * 1 <= k <= s.length
     * ]
     * @param s
     * @param k
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2024/10/25 9:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String shortestBeautifulSubstring(String s, int k) {
        String result = "";
        result = method_01(s, k);
        return result;
    }
    /**
     * @Description: [
     * E1:
     * 输入：s = "100011001", k = 3
     * 输出："11001"
     * 解释：示例中共有 7 个美丽子字符串：
     * 1. 子字符串 "100011001" 。
     * 2. 子字符串 "100011001" 。
     * 3. 子字符串 "100011001" 。
     * 4. 子字符串 "100011001" 。
     * 5. 子字符串 "100011001" 。
     * 6. 子字符串 "100011001" 。
     * 7. 子字符串 "100011001" 。
     * 最短美丽子字符串的长度是 5 。
     * 长度为 5 且字典序最小的美丽子字符串是子字符串 "11001"
     *
     * AC:2ms/41.79MB
     * ]
     * @param s
     * @param k
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2024/10/25 9:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s, int k) {
        int n = s.length();
        int left = 0;
        int[] cnt = new int[2];
        ArrayList<String> list = new ArrayList<>();
        int ans = n + 1;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                cnt[0]++;
            }else {
                cnt[1]++;
            }
            while (left < n && cnt[1] > k) {
                if (s.charAt(left) == '0') {
                    cnt[0]--;
                }else {
                    cnt[1]--;
                }
                left++;
            }
            while (left < n && s.charAt(left) == '0') {
                cnt[0]--;
                left++;
            }
            if (cnt[1] == k) {
                ans = Math.min(ans, i - left + 1);
                list.add(s.substring(left, i+1));
            }
        }


        ArrayList<String> arrayList = new ArrayList<>();
        for (String s1 : list) {
            if (s1.length() == ans) {
                arrayList.add(s1);
            }
        }
        // 在长度全部为ans的list中找到字典序最小的子字符串
        String res = arrayList.size() > 0 ? arrayList.get(0) : "";
        for (int i = 1; i < arrayList.size(); i++) {
            String temp = arrayList.get(i);
            if (!res.equals(temp)) {
                res = checkLexicographicOrder(res, temp);
            }
        }

        return res;
    }

    private String checkLexicographicOrder(String s1, String s2) {
        int n = s1.length();
        for (int i = 0; i < n; i++) {
            if (s1.charAt(i) > s2.charAt(i)) {
                return s2;
            } else if (s1.charAt(i) < s2.charAt(i)) {
                return s1;
            }
        }
        return s1;
    }
}
