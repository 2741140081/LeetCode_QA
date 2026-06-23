package com.marks.leetcode.all_str;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_809 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/23 14:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_809 {

    /**
     * @Description:
     * 有时候人们会用重复写一些字母来表示额外的感受，比如 "hello" -> "heeellooo", "hi" -> "hiii"。
     * 我们将相邻字母都相同的一串字符定义为相同字母组，例如："h", "eee", "ll", "ooo"。
     * 对于一个给定的字符串 S ，如果另一个单词能够通过将一些字母组扩张从而使其和 S 相同，我们将这个单词定义为可扩张的（stretchy）。
     * 扩张操作定义如下：选择一个字母组（包含字母 c ），然后往其中添加相同的字母 c 使其长度达到 3 或以上。
     *
     * 例如，以 "hello" 为例，我们可以对字母组 "o" 扩张得到 "hellooo"，但是无法以同样的方法得到 "helloo" 因为字母组 "oo" 长度小于 3。
     * 此外，我们可以进行另一种扩张 "ll" -> "lllll" 以获得 "helllllooo"。
     * 如果 s = "helllllooo"，那么查询词 "hello" 是可扩张的，因为可以对它执行这两种扩张操作使得 query = "hello" -> "hellooo" -> "helllllooo" = s。
     * 输入一组查询单词，输出其中可扩张的单词数量。
     *
     * tips:
     * 1 <= s.length, words.length <= 100
     * 1 <= words[i].length <= 100
     * s 和所有在 words 中的单词都只由小写字母组成。
     * @param: s
     * @param: words
     * @return int
     * @author marks
     * @CreateDate: 2026/06/23 14:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int expressiveWords(String s, String[] words) {
        int result;
        result = method_01(s, words);
        return result;
    }

    /**
     * @Description:
     * 1. 需要判断 words[i] 是否可扩张 成 s, 先对字符串进行压缩，将相同字母组压缩成单个字母, 并且记录压缩的字母组长度
     * 2. 例如 hello 可以压缩成 h1 e1 l2 o1, 使用一个自定义类存储压缩的字母组信息
     * 3. 之前的理解有问题, 只需要扩张后的字母组长度 > 原来的字母, 并且扩张后的字母组长度 >= 3, 只要这两个条件满足即可
     * AC: 6ms/44.77MB
     * @param: s
     * @param: words
     * @return int
     * @author marks
     * @CreateDate: 2026/06/23 14:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s, String[] words) {
        // 先对 s 进行压缩
        int sLen = s.length();
        List<Info> sList = new ArrayList<>();
        char prev = s.charAt(0);
        int cnt = 1;
        for (int i = 1; i < sLen; i++) {
            char cur = s.charAt(i);
            if (cur == prev) {
                cnt++;
            } else {
                sList.add(new Info(prev, cnt));
                prev = cur;
                cnt = 1;
            }
        }
        // 添加最后一个字母组
        sList.add(new Info(prev, cnt));
        int ans = 0; // 可扩张单词数量
        for (String word : words) {
            int wordLen = word.length();
            if (wordLen > sLen - 2) {
                continue;
            }
            List<Info> wordList = new ArrayList<>();
            prev = word.charAt(0);
            cnt = 1;
            for (int i = 1; i < wordLen; i++) {
                char cur = word.charAt(i);
                if (cur == prev) {
                    cnt++;
                } else {
                    wordList.add(new Info(prev, cnt));
                    prev = cur;
                    cnt = 1;
                }
            }
            wordList.add(new Info(prev, cnt));
            if (checkValid(sList, wordList)) {
                ans++;
            }
        }

        return ans;
    }

    private boolean checkValid(List<Info> sList, List<Info> wordList) {
        int sLen = sList.size(), wordLen = wordList.size();
        if (sLen != wordLen) {
            return false;
        }
        boolean flag = false;
        for (int i = 0; i < sLen; i++) {
            Info sInfo = sList.get(i);
            Info wordInfo = wordList.get(i);
            if (sInfo.ch != wordInfo.ch || sInfo.count < wordInfo.count) {
                return false;
            }
            if (sInfo.count > wordInfo.count) {
                if (sInfo.count < 3) {
                    return false;
                }
                flag = true;
            }
        }
        return flag;
    }

    private class Info {
        char ch;
        int count;
        public Info(char ch, int count) {
            this.ch = ch;
            this.count = count;
        }
    }

}
