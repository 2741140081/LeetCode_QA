package com.marks.leetcode.sliding_window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_187 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/23 11:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_187 {

    /**
     * @Description:
     * DNA序列 由一系列核苷酸组成，缩写为 'A', 'C', 'G' 和 'T'.。
     * 例如，"ACGAATTCCG" 是一个 DNA序列 。
     * 在研究 DNA 时，识别 DNA 中的重复序列非常有用。
     * 给定一个表示 DNA序列 的字符串 s ，返回所有在 DNA 分子中出现不止一次的 长度为 10 的序列(子字符串)。
     * 你可以按 任意顺序 返回答案。
     * tips:
     * 0 <= s.length <= 10^5
     * s[i]=='A'、'C'、'G' or 'T'
     * @param: s
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2026/01/23 11:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<String> findRepeatedDnaSequences(String s) {
        List<String> result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * 1. 需要存储子串的数量, 需要用Map<String, Integer> map;
     * 2. 是通过subString截取还是用List<Character> list; 存储? 使用subString()来操作
     * AC: 35ms/54.86MB
     * @param: s
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2026/01/23 11:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<String> method_01(String s) {
        int n = s.length();
        List<String> ans = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        for (int right = 10; right <= n; right++) {
            String subString = s.substring(right - 10, right);
            map.put(subString, map.getOrDefault(subString, 0) + 1);
            if (map.get(subString) == 2) {
                // 添加重复的子串
                ans.add(subString);
            }
        }

        return ans;
    }

}
