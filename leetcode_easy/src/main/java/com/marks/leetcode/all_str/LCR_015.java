package com.marks.leetcode.all_str;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCR_015 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/25 10:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCR_015 {

    /**
     * @Description:
     * 给定两个字符串 s 和 p，找到 s 中所有 p 的 变位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
     * 变位词 指字母相同，但排列不同的字符串。
     * tips:
     * 1 <= s.length, p.length <= 3 * 10^4
     * s 和 p 仅包含小写字母
     * @param: s
     * @param: p
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/06/25 10:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result;
        result = method_01(s, p);
        return result;
    }

    /**
     * @Description:
     * 1. 滑动窗口
     * AC: 3ms/46.15MB
     * @param: s
     * @param: p
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/06/25 10:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(String s, String p) {
        int m = s.length(), n = p.length();
        List<Integer> ans = new ArrayList<>();
        if (m < n) {
            return ans;
        }
        int[] cnt = new int[26];
        int[] target = new int[26];
        for (int i = 0; i < n; i++) {
            cnt[s.charAt(i) - 'a']++;
            target[p.charAt(i) - 'a']++;
        }
        boolean flag = true;
        int left = 0;
        for (int i = 0; i < 26; i++) {
            if (cnt[i] != target[i]) {
                flag = false;
                break;
            }
        }
        if (flag) {
            ans.add(left);
        }

        for (int i = n; i < m; i++) {
            int prev = s.charAt(left) - 'a';
            int idx = s.charAt(i) - 'a';
            cnt[prev]--;
            cnt[idx]++;
            flag = true;
            for (int j = 0; j < 26; j++) {
                if (cnt[j] != target[j]) {
                    flag = false;
                    break;
                }
            }

            left++;
            if (flag) {
                ans.add(left);
            }
        }
        return ans;
    }

}
