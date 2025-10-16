package com.marks.leetcode.DP;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: 面试题 17.13 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/14 14:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_Interview_17_13 {

    
    /**
     * @Description:
     * 哦，不！你不小心把一个长篇文章中的空格、标点都删掉了，并且大写也弄成了小写。
     * 像句子"I reset the computer. It still didn’t boot!"已经变成了"iresetthecomputeritstilldidntboot"。
     * 在处理标点符号和大小写之前，你得先把它断成词语。
     * 当然了，你有一本厚厚的词典dictionary，不过，有些词没在词典里。
     * 假设文章用sentence表示，设计一个算法，把文章断开，要求未识别的字符最少，返回未识别的字符数。
     *
     * 注意：本题相对原题稍作改动，只需返回未识别的字符数
     * @param dictionary 
     * @param sentence
     * @return int
     * @author marks
     * @CreateDate: 2025/10/14 14:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int respace(String[] dictionary, String sentence) {
        int result;
        result = method_01(dictionary, sentence);
        result = method_02(dictionary, sentence);
        return result;
    }

    /**
     * @Description:
     * 1. 基于method_01 的时间复杂度太高, 查看官解使用了字典树,
     * 希望method_02 可以通过字典树来优化时间复杂度
     * 2. 构建一个字典树类 Trie树, 需要存储 dictionary[i] 单词的倒序, 方便查询
     * AC: 11ms/64.25MB
     * 果然使用字典树可以优化算法的时间复杂度
     * @param dictionary
     * @param sentence
     * @return int
     * @author marks
     * @CreateDate: 2025/10/14 15:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String[] dictionary, String sentence) {
        Trie root = new Trie();
        for (String s : dictionary) {
            root.insert(s);
        }
        int n = sentence.length();
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i - 1] + 1;
            Trie curPos = root;
            for (int l = i; l >= 1; l--) {
                int t = sentence.charAt(l - 1) - 'a';
                if (curPos.child[t] == null) {
                    break;
                } else if (curPos.child[t].isFinished) {
                    dp[i] = Math.min(dp[i], dp[l - 1]);
                }
                if (dp[i] == 0) {
                    // 提前剪枝? 如果此时dp[i] = 0, 表明当前s[0, i] 全部在字典中, 不需要继续遍历到0处
                    break;
                }
                curPos = curPos.child[t];
            }
        }
        return 0;
    }

    private class Trie {
        public boolean isFinished;
        public Trie[] child;

        public Trie() {
            this.isFinished = false;
            this.child = new Trie[26];
        }

        public void insert(String s) {
            Trie curPos = this;

            for (int i = s.length() - 1; i >= 0; --i) {
                int t = s.charAt(i) - 'a';
                if (curPos.child[t] == null) {
                    curPos.child[t] = new Trie();
                }
                curPos = curPos.child[t];
            }
            curPos.isFinished = true;
        }
    }

    /**
     * @Description:
     * E1:
     * dictionary = ["looked","just","like","her","brother"]
     * sentence = "jesslookedjustliketimherbrother"
     * 输出： 7
     * 解释： 断句后为"jess looked just like tim her brother"，共7个未识别字符。
     * 1. 外层遍历的返回是 0 ~ n(n 是 sentence 的长度), 初始化一个 int[] dp = new int[n + 1]; dp[0] = 0
     * 2. 当前遍历到节点 i, dp[i] = dp[i - 1] + 1; 先不处理结尾的字符是否存在字典中, 先直接将未识别的字符数 + 1
     * 3. 然后从后向前遍历, 截取字符串[l, i], 使用set.contains(substring) 判断是否在字典中, sentence.substring(l, i + 1), 需要包含i节点的字符
     * 4. 假设 substring 在字典中, dp[i] = Math.min(dp[i], dp[l - 1]);
     *
     * AC: 654ms/45.01MB
     *
     * 时间复杂度有点高, 看官方题解可以用字典树进行优化? 见method_02
     * @param dictionary 
     * @param sentence 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/14 14:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] dictionary, String sentence) {
        // Set 集合来存储字典, 方便查询
        Set<String> set = new HashSet<>();
        for (String s : dictionary) {
            set.add(s);
        }
        int n = sentence.length();
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            // 默认未识别的字符数 + 1
            dp[i] = dp[i - 1] + 1;
            for (int l = i; l >= 1; l--) {
                String substring = sentence.substring(l - 1, i);
                if (set.contains(substring)) {
                    // 假设 substring 在字典中, dp[i] = Math.min(dp[i], dp[l - 1]);
                    dp[i] = Math.min(dp[i], dp[l - 1]);
                }
            }
        }
        
        return dp[n];
    }

}
