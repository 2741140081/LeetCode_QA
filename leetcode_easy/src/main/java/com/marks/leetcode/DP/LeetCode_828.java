package com.marks.leetcode.DP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_828 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/9 15:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_828 {

    /**
     * @Description:
     * 我们定义了一个函数 countUniqueChars(s) 来统计字符串 s 中的唯一字符，并返回唯一字符的个数。
     * 例如：s = "LEETCODE" ，则其中 "L", "T","C","O","D" 都是唯一字符，因为它们只出现一次，所以 countUniqueChars(s) = 5 。
     * 本题将会给你一个字符串 s ，我们需要返回 countUniqueChars(t) 的总和，其中 t 是 s 的子字符串。输入用例保证返回值为 32 位整数。
     * 注意，某些子字符串可能是重复的，但你统计时也必须算上这些重复的子字符串（也就是说，你必须统计 s 的所有子字符串中的唯一字符）。
     *
     * tips:
     * 1 <= s.length <= 10^5
     * s 只包含大写英文字符
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2026/04/09 15:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int uniqueLetterString(String s) {
        int result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * 1. 贡献法, 求出 第 i 个字符的贡献值, 需要确保 i 在[j ~ k] 范围内是唯一的
     * 2. 要实现1, 需要记录每个字符的上一次出现的位置, 以及下一次出现的位置
     * AC: 62ms/49.66MB
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2026/04/09 15:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        Map<Character, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!map.containsKey(c)) {
                map.put(c, new ArrayList<>());
                map.get(c).add(-1);
            }
            map.get(c).add(i);
        }
        // 遍历map
        int ans = 0;
        for (Map.Entry<Character, List<Integer>> entry : map.entrySet()) {
            List<Integer> list = entry.getValue();
            // 处理末尾节点
            list.add(s.length());
            for (int i = 1; i < list.size() - 1; i++) {
                ans += (list.get(i) - list.get(i - 1)) * (list.get(i + 1) - list.get(i));
            }
        }
        return ans;
    }

}
