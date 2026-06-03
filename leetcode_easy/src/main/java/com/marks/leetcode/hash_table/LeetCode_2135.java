package com.marks.leetcode.hash_table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2135 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/3 14:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2135 {

    /**
     * @Description:
     * 给你两个下标从 0 开始的字符串数组 startWords 和 targetWords 。每个字符串都仅由 小写英文字母 组成。
     * 对于 targetWords 中的每个字符串，检查是否能够从 startWords 中选出一个字符串，执行一次 转换操作 ，得到的结果与当前 targetWords 字符串相等。
     * 转换操作 如下面两步所述：
     * 追加 任何 不存在 于当前字符串的任一小写字母到当前字符串的末尾。
     * 例如，如果字符串为 "abc" ，那么字母 'd'、'e' 或 'y' 都可以加到该字符串末尾，但 'a' 就不行。如果追加的是 'd' ，那么结果字符串为 "abcd" 。
     * 重排 新字符串中的字母，可以按 任意 顺序重新排布字母。
     * 例如，"abcd" 可以重排为 "acbd"、"bacd"、"cbda"，以此类推。注意，它也可以重排为 "abcd" 自身。
     * 找出 targetWords 中有多少字符串能够由 startWords 中的 任一 字符串执行上述转换操作获得。返回 targetWords 中这类 字符串的数目 。
     * 注意：你仅能验证 targetWords 中的字符串是否可以由 startWords 中的某个字符串经执行操作获得。startWords  中的字符串在这一过程中 不 发生实际变更。
     *
     * tips:
     * 1 <= startWords.length, targetWords.length <= 5 * 10^4
     * 1 <= startWords[i].length, targetWords[j].length <= 26
     * startWords 和 targetWords 中的每个字符串都仅由小写英文字母组成
     * 在 startWords 或 targetWords 的任一字符串中，每个字母至多出现一次
     * @param: startWords
     * @param: targetWords
     * @return int
     * @author marks
     * @CreateDate: 2026/06/03 14:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int wordCount(String[] startWords, String[] targetWords) {
        int result;
        result = method_01(startWords, targetWords);
        return result;
    }

    /**
     * @Description:
     * 1. 对于每一个字符串而言, 每种字符都只会出现1次, 所以可以用一个 26 位的二进制数表示, 其中第 i 位表示第 i 个字符出现, 为1, 例如 "ab", 可以用 11 表示
     * 2. 对于 targetWords[i], 可以减少任意位置上一个字符, 假设 int n = targetWords[i].length(), 那么就有 n 种子串, 判断子串是否存在 set 中, 如果存在, 则返回 true
     * 3. 需要将转换后的 startWords 存入 Set 集合中
     * AC: 52ms/78.36MB
     * @param: startWords
     * @param: targetWords
     * @return int
     * @author marks
     * @CreateDate: 2026/06/03 14:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] startWords, String[] targetWords) {
        Set<Integer> set = new HashSet<>();
        for (String startWord : startWords) {
            int key = StringToInteger(startWord);
            set.add(key);
        }
        int ans = 0;
        for (String targetWord : targetWords) {
            int key = StringToInteger(targetWord);
            // 判断 key 的 第 i 位是否为1, 如果为1, 将其转换为0, 然后set 判断转换后的 key 是否存在

            for (int i = 0; i < 26; i++) {
                if ((key & (1 << i)) != 0) {
                    // 将当前位变为0, 然后判断是否存在
                    int temp = key ^ (1 << i);
                    if (set.contains(temp)) {
                        // 找到后直接剪枝
                        ans++;
                        break;
                    }
                }
            }

        }
        return ans;
    }

    private int StringToInteger(String startWord) {
        int ans = 0;
        for (int i = 0; i < startWord.length(); i++) {
            ans |= 1 << (startWord.charAt(i) - 'a');
        }
        return ans;
    }

}
