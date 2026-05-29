package com.marks.leetcode.hash_table;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2262 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/28 15:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2262 {

    /**
     * @Description:
     * 字符串的 引力 定义为：字符串中 不同 字符的数量。
     *
     * 例如，"abbca" 的引力为 3 ，因为其中有 3 个不同字符 'a'、'b' 和 'c' 。
     * 给你一个字符串 s ，返回 其所有子字符串的总引力 。
     * 子字符串 定义为：字符串中的一个连续字符序列。
     *
     * tips:
     * 1 <= s.length <= 10^5
     * s 由小写英文字母组成
     * @param: s
     * @return long
     * @author marks
     * @CreateDate: 2026/05/28 15:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long appealSum(String s) {
        long result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：s = "abbca"
     * 输出：28
     * 1. 对于 s[i], 假设左侧有 l 个连续字符 s[l] != s[i]，并且 右侧有 r 个连续字符 s[r] != s[i]，
     * 如何通过这两个数据计算 s[i] 的引力？ 也就是计算组合数量, s[i] 对每一个组合的引力贡献值是1, s[i] 的总贡献引力数 = 组合数 * 1
     * 2. 现在问题转换为 计算 组合数, 组合数 = (l + 1) * (r + 1)
     * 3. 对于每一个字符 s[i], 需要找到 s[prev] = s[i] 和 s[next] = s[i]
     * 4. 计算E1, s[0] = 5, s[1] = 8, s[2] = 3, s[3] = 8, s[4] = 4, 总数是 28
     * 5. 只需要记录字符前一个的索引, 所有小写字母初始索引设置为 -1
     * AC: 30ms/46.31MB
     * @param: s
     * @return long
     * @author marks
     * @CreateDate: 2026/05/28 15:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(String s) {
        long ans = 0;
        long n = s.length();
        Map<Character, Long> map = new HashMap<>();
        for (int i = 0; i < 26; i++) {
            map.put((char) ('a' + i), -1L);
        }
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            long prev = map.get(c);
            ans += (i - prev) * (n - i);
            map.put(c, (long)i);
        }

        return ans;
    }

}
