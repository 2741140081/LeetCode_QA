package com.marks.leetcode.hash_table;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_522 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/29 14:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_522 {

    /**
     * @Description:
     * 给定字符串列表 strs ，返回其中 最长的特殊序列 的长度。如果最长特殊序列不存在，返回 -1 。
     * 特殊序列 定义如下：该序列为某字符串 独有的子序列（即不能是其他字符串的子序列）。
     *  s 的 子序列可以通过删去字符串 s 中的某些字符实现。
     * 例如，"abc" 是 "aebdc" 的子序列，因为您可以删除"aebdc"中的下划线字符来得到 "abc" 。"aebdc"的子序列还包括"aebdc"、 "aeb" 和 "" (空字符串)。
     *
     * tips:
     * 2 <= strs.length <= 50
     * 1 <= strs[i].length <= 10
     * strs[i] 只包含小写英文字母
     * @param: strs
     * @return int
     * @author marks
     * @CreateDate: 2026/05/29 14:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findLUSlength(String[] strs) {
        int result;
        result = method_01(strs);
        result = method_02(strs);
        return result;
    }

    /**
     * @Description:
     * 1. 使用双重for 循环, 判断 i 是否是 j 的子序列
     * AC: 1ms/41.96MB
     * @param: strs
     * @return int
     * @author marks
     * @CreateDate: 2026/05/29 16:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String[] strs) {
        int n = strs.length;
        int ans = -1;
        for (int i = 0; i < n; i++) {
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                if (isSubsequence(strs[i], strs[j])) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                ans = Math.max(ans, strs[i].length());
            }
        }

        return ans;
    }

    private boolean isSubsequence(String s1, String s2) {
        int left = 0, right = 0;
        while (left < s1.length() && right < s2.length()) {
            if (s1.charAt(left) == s2.charAt(right)) {
                left++;
            }
            right++;
        }
        return left == s1.length();
    }

    /**
     * @Description:
     * 1. 使用 map 存储字符串出现的次数
     * 2. 对于 strs[i], 需要枚举所有的子序列, 并且添加到 map 中,
     * AC: 62ms/49.75MB
     * @param: strs
     * @return int
     * @author marks
     * @CreateDate: 2026/05/29 14:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] strs) {
        Map<String, Integer> map = new HashMap<>();
        for (String str : strs) {
            int n = str.length();
            for (int i = 0; i < (1 << n); i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    if ((i & (1 << j)) != 0) {
                        sb.append(str.charAt(j));
                    }
                }
                map.merge(sb.toString(), 1, Integer::sum);
            }
        }
        int ans = -1;
        for (String str : map.keySet()) {
            if (map.get(str) == 1) {
                ans = Math.max(ans, str.length());
            }
        }

        return ans;
    }

}
