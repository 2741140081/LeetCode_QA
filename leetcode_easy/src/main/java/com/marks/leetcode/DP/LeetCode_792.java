package com.marks.leetcode.DP;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_792 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/3 11:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_792 {

    /**
     * @Description:
     * 给定字符串 s 和字符串数组 words, 返回  words[i] 中是s的子序列的单词个数 。
     * 字符串的 子序列 是从原始字符串中生成的新字符串，可以从中删去一些字符(可以是none)，而不改变其余字符的相对顺序。
     * 例如， “ace” 是 “abcde” 的子序列。
     * tips:
     * 1 <= s.length <= 5 * 10^4
     * 1 <= words.length <= 5000
     * 1 <= words[i].length <= 50
     * words[i]和 s 都只由小写字母组成。
     * @param: s
     * @param: words
     * @return int
     * @author marks
     * @CreateDate: 2026/03/03 11:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    public int numMatchingSubseq(String s, String[] words) {
        int result;
        result = method_01(s, words);
        return result;
    }


    /**
     * @Description:
     * E1:
     * 输入: s = "abcde", words = ["a","bb","acd","ace"]
     * 输出: 3
     * 1. 怎么判断 s[i] 是 s 的子序列, 如果只是对两个字符串进行判断, 可以用双指针进行匹配
     * 2. 等下, s[i] 的长度只有50, 并且只包含小写字母, 二维数组进行记录 int[][] dic= new int[50][26]
     * 3. 这个感觉像是字典树, 对于 s[i] 而言, 需要将 i 的坐标添加到 dic 中, 怎么进行添加?
     * 4. 直接看官解
     * AC: 141ms/51.68MB
     * @param: s
     * @param: words
     * @return int
     * @author marks
     * @CreateDate: 2026/03/03 11:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s, String[] words) {
        int n = s.length();
        // 分别记录26个小写字母出现的index值
        List<Integer>[] pos = new ArrayList[26];
        for (int i = 0; i < pos.length; i++) {
            pos[i] = new ArrayList<>();
        }
        for (int i = 0; i < n; i++) {
            pos[s.charAt(i) - 'a'].add(i);
        }
        // 对于每一个word = words[i], 遍历words[i], 记录前一个word[prev] 的下标 index
        // 对于当前 words[i], 在 pos[word[i] - 'a'] 在 index + 1 位置开始进行匹配, 使用二分查找, 找到符合要求的最小 index,
        // 如果不存在, 则表示当前word 不是 s 的子序列
        int ans = 0;
        for (String word : words) {
            int index = -1;
            boolean flag = true;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                int index_next = binarySearch(pos[ch - 'a'], index);
                if (index_next == -1) {
                    flag = false;
                    break;
                }
                index = index_next;
            }
            if (flag) {
                ans++;
            }
        }
        return ans;
    }

    private int binarySearch(List<Integer> pos, int targetIndex) {
        // 先判断存不存在, 然后在进行二分查找
        int left = 0;
        int right = pos.size() - 1;
        if (pos.isEmpty() || pos.get(right) <= targetIndex) {
            return -1;
        }

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (pos.get(mid) > targetIndex) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return pos.get(left) > targetIndex ? pos.get(left) : -1;
    }

}
