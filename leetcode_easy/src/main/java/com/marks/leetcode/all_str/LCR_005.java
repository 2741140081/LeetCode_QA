package com.marks.leetcode.all_str;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCR_005 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/25 10:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCR_005 {

    /**
     * @Description:
     * 给定一个字符串数组 words，请计算当两个字符串 words[i] 和 words[j] 不包含相同字符时，它们长度的乘积的最大值。
     * 假设字符串中只包含英语的小写字母。如果没有不包含相同字符的一对字符串，返回 0。
     * @param: words
     * @return int
     * @author marks
     * @CreateDate: 2026/06/25 10:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxProduct(String[] words) {
        int result;
        result = method_01(words);
        return result;
    }

    /**
     * @Description:
     * 1. 先统计每个字符串中字符出现的次数, 然后 将 i 与 j 进行比较, 如果没有相同字符, 即cnt[i_0] * cnt[j_0] > 0, 则说明 i 不满足,
     * 2. 时间复杂度为 O(n^2)
     * 3. 有没有进阶方法, 有一个想法是 假设 i 与 j 有重复字符, 将将 j 中不同的字符添加到 i 中 这种感觉想并查集, 但是处理起来有点麻烦.
     * 先用 之前的统计方案处理
     * 4. 理解错误, 只需要比较 i 和 j 即可, 与 其它字符串无关联. 所以不需要使用并查集.
     * AC: 26ms/45.92MB
     * @param: words
     * @return int
     * @author marks
     * @CreateDate: 2026/06/25 10:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] words) {
        List<int[]> cnt = new ArrayList<>();
        for (String word : words) {
            int[] temp = new int[26];
            for (char c : word.toCharArray()) {
                temp[c - 'a']++;
            }
            cnt.add(temp);
        }
        int ans = 0;
        int n = words.length;
        for (int i = 0; i < n - 1; i++) {
            int[] a = cnt.get(i);
            for (int j = i + 1; j < n; j++) {
                boolean flag = true;
                int[] b = cnt.get(j);
                for (int k = 0; k < 26; k++) {
                    if (a[k] * b[k] > 0) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    ans = Math.max(ans, words[i].length() * words[j].length());
                }
            }
        }
        return ans;
    }
}
