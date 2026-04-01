package com.marks.leetcode.daily_question;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3474 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/31 14:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3474 {

    /**
     * @Description:
     * 给你两个字符串，str1 和 str2，其长度分别为 n 和 m 。
     * 如果一个长度为 n + m - 1 的字符串 word 的每个下标 0 <= i <= n - 1 都满足以下条件，则称其由 str1 和 str2 生成：
     *
     * 如果 str1[i] == 'T'，则长度为 m 的 子字符串（从下标 i 开始）与 str2 相等，即 word[i..(i + m - 1)] == str2。
     * 如果 str1[i] == 'F'，则长度为 m 的 子字符串（从下标 i 开始）与 str2 不相等，即 word[i..(i + m - 1)] != str2。
     * 返回可以由 str1 和 str2 生成 的 字典序最小 的字符串。如果不存在满足条件的字符串，返回空字符串 ""。
     *
     * 如果字符串 a 在第一个不同字符的位置上比字符串 b 的对应字符在字母表中更靠前，则称字符串 a 的 字典序 小于 字符串 b。
     * 如果前 min(a.length, b.length) 个字符都相同，则较短的字符串字典序更小。
     *
     * 子字符串 是字符串中的一个连续、非空 的字符序列。
     *
     * tips:
     * 1 <= n == str1.length <= 10^4
     * 1 <= m == str2.length <= 500
     * str1 仅由 'T' 或 'F' 组成。
     * str2 仅由小写英文字母组成
     * @param: str1
     * @param: str2
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/03/31 14:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String generateString(String str1, String str2) {
        String result;
        result = method_01(str1, str2);
        result = method_02(str1, str2);
        return result;
    }

    private String method_02(String str1, String str2) {
        int n = str1.length(), m = str2.length();
        char[] s = new char[n + m - 1];
        int[] fixed = new int[n + m - 1];

        Arrays.fill(s, 'a');

        for (int i = 0; i < n; i++) {
            if (str1.charAt(i) == 'T') {
                for (int j = i; j < i + m; j++) {
                    if (fixed[j] == 1 && s[j] != str2.charAt(j - i)) {
                        return "";
                    } else {
                        s[j] = str2.charAt(j - i);
                        fixed[j] = 1;
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (str1.charAt(i) == 'F') {
                boolean flag = false;
                int idx = -1;
                for (int j = i + m - 1; j >= i; j--) {
                    if (str2.charAt(j - i) != s[j]) {
                        flag = true;
                    }
                    if (idx == -1 && fixed[j] == 0) {
                        idx = j;
                    }
                }
                if (flag) {
                    continue;
                } else if (idx != -1) {
                    s[idx] = 'b';
                } else {
                    return "";
                }
            }
        }
        return new String(s);
    }


    /**
     * @Description:
     * 1. 能否优先处理 T 的情况
     * 2. 处理 F 的情况, used[i] = true, 表示这个已经时确定值, 无法修改
     * 3. 处理 F 存在问题, 不能这样写, 用Set 记录不能等于的值是不行的, 因为只需要一处不等即可, 不需要处处不等
     * 4. 可以记录能修改的次数 count, diff 只记录used[i + j] = true 的情况, diff = true 时, 则count 都是可以修改的,
     * diff = false 时, 将前面都修改0 ~ count - 2 都修改成 'a' 最小字典序, 并且判断是否'a' 是否与 str2.charAt(j) 相同,
     * 如果不同, 则最后一个字符修改成 'a', 否则修改成。
     * 5. 不能这样写, 因为当前 i 的修改会影响后续 i 的修改. 先看看提示吧.
     * 6. 看了提示, 使用动态规划, 并且未知/used[i] = false 可以用['a', 'b'] 中的任意一个进行填充.
     * 7. 看看题解怎么使用dp 吧
     *
     *
     * @param: str1
     * @param: str2
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/03/31 14:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String str1, String str2) {
        int n = str1.length(), m = str2.length();
        char[] word = new char[n + m - 1];
        boolean[] used = new boolean[n + m - 1];
        // 处理 T 的情况
        for (int i = 0; i < n; i++) {
            if (str1.charAt(i) == 'T') {
                for (int j = 0; j < m; j++) {
                    // 判断 used[i + j] 是否使用过
                    if (used[i + j]) {
                        // 如果使用过了, 则判断当前字符是否相等, 如果不相等则返回 ""
                        if (word[i + j] != str2.charAt(j)) {
                            return "";
                        }
                    } else {
                        // 如果没有使用过, 则将当前字符赋给 word[i + j]
                        word[i + j] = str2.charAt(j);
                        used[i + j] = true;
                    }
                }
            }
        }
        // 处理 F 的情况, 并且使用 Map 记录 word[i + j] 使用情况, Map<Integer, List<Character>>
        Map<Integer, Set<Character>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (str1.charAt(i) == 'F') {
                // 设置一个标记位, 判断是否有不同
                boolean diff = false;
                for (int j = 0; j < m; j++) {
                    // 判断 used[i + j] 是否使用过
                    if (used[i + j]) {
                        // 已经使用
                        if (word[i + j] != str2.charAt(j)) {
                            diff = true;
                        }
                    } else {
                        // 未使用, 判断map是否包含当前下标
                        if (map.containsKey(i + j)) {
                            // 如果有下标, 将当前字符添加到Set集合, 表面改字符不能等于str2.charAt(j)
                            map.get(i + j).add(str2.charAt(j));
                        } else {
                            // 如果没有下标, 创建一个Set集合, 将当前字符添加到Set集合, 添加到map中
                            Set<Character> set = new HashSet<>();
                            set.add(str2.charAt(j));
                            map.put(i + j, set);
                        }
                        diff = true;
                    }
                }
                // 如果 diff = false, 则说明为 F 不能满足条件
                if (!diff) {
                    return "";
                }
            }
        }
        // 遍历 Map, 给 map.get(i + j) 设置一个最小的字典序, 并且 set 不包含该字符
        for (Map.Entry<Integer, Set<Character>> entry : map.entrySet()) {
            int i = entry.getKey();
            Set<Character> set = entry.getValue();
            // 判断set.size == 26
            if (set.size() == 26) {
                return "";
            }
            // 遍历26个字母
            for (char c = 'a'; c <= 'z'; c++) {
                if (!set.contains(c)) {
                    word[i] = c;
                    break;
                }
            }
        }
        // 返回word[]
        return new String(word);
    }

}
