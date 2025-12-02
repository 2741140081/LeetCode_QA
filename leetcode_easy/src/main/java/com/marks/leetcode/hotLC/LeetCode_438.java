package com.marks.leetcode.hotLC;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_438 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/1 16:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_438 {

    /**
     * @Description:
     * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
     * tips:
     * 1 <= s.length, p.length <= 3 * 10^4
     * s 和 p 仅包含小写字母
     * @param: s
     * @param: p
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/12/01 16:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result;
        result = method_01(s, p);
        return result;
    }

    /**
     * @Description:
     * 滑动窗口
     * 1. 存储p 中每个字符出现的次数, 初始化一个固定长度pLength 的滑动窗口, 记录窗口内每个字符出现的次数
     * 2. List<Integer> 用于记录结果, 比较窗口内每个字符出现的次数和p 中每个字符出现的次数
     * 3. 移动窗口, 移除最左边的字符, 添加最右边的字符, 如果相等就想ans添加结果
     * AC: 10ms/46.11MB
     * @param: s
     * @param: p
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/12/01 16:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(String s, String p) {
        int pLength = p.length();
        int n = s.length();
        int[] pCount = new int[26];
        for (int i = 0; i < pLength; i++) {
            pCount[p.charAt(i) - 'a']++;
        }
        List<Integer> ans = new ArrayList<>();
        // 创建滑动窗口
        int[] window = new int[26];
        if (n < pLength) {
            return ans;
        }
        for (int i = 0; i < pLength; i++) {
            window[s.charAt(i) - 'a']++;
        }
        if (checkCharArray(pCount, window)) {
            ans.add(0);
        }
        for (int i = pLength; i < n; i++) {
            int prev = s.charAt(i - pLength) - 'a';
            int curr = s.charAt(i) - 'a';
            window[prev]--;
            window[curr]++;
            if (checkCharArray(pCount, window)) {
                ans.add(i - pLength + 1);
            }
        }

        return ans;
    }

    private boolean checkCharArray(int[] pCount, int[] window) {
        for (int i = 0; i < pCount.length; i++) {
            if (pCount[i] != window[i]) {
                return false;
            }
        }
        return true;

    }

}
