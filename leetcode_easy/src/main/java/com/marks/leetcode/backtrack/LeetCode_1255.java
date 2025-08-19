package com.marks.leetcode.backtrack;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/19 10:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1255 {

    
    /**
     * @Description:
     * 你将会得到一份单词表 words，一个字母表 letters （可能会有重复字母），以及每个字母对应的得分情况表 score。
     *
     * 请你帮忙计算玩家在单词拼写游戏中所能获得的「最高得分」：能够由 letters 里的字母拼写出的 任意 属于 words 单词子集中，分数最高的单词集合的得分。
     *
     * 单词拼写游戏的规则概述如下：
     *
     * 玩家需要用字母表 letters 里的字母来拼写单词表 words 中的单词。
     * 可以只使用字母表 letters 中的部分字母，但是每个字母最多被使用一次。
     * 单词表 words 中每个单词只能计分（使用）一次。
     * 根据字母得分情况表score，字母 'a', 'b', 'c', ... , 'z' 对应的得分分别为 score[0], score[1], ..., score[25]。
     * 本场游戏的「得分」是指：玩家所拼写出的单词集合里包含的所有字母的得分之和。
     *
     * @param words 
     * @param letters 
     * @param score
     * @return int
     * @author marks
     * @CreateDate: 2025/8/19 10:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxScoreWords(String[] words, char[] letters, int[] score) {
        // 时间复杂度分析：
        // 1. method_01 使用回溯算法，对于每个单词都有选或不选两种选择，因此时间复杂度为 O(2^n)，其中 n 是单词数组的长度。
        // 2. 在每层递归中，需要检查当前单词是否能由剩余字母组成，这需要遍历单词的所有字符，最坏情况下每个单词的长度为 m，
        //    因此每层递归的时间复杂度为 O(m)。
        // 3. 综合起来，总的时间复杂度为 O(2^n * m)。
        //
        // 空间复杂度分析：
        // 1. 递归调用栈的深度等于单词数组的长度 n，因此递归栈的空间复杂度为 O(n)。
        // 2. 在递归过程中，我们使用了额外的数组来记录字母计数，数组大小为常数 26，因此这部分空间复杂度为 O(1)。
        // 3. 综合起来，总的空间复杂度为 O(n)。
        int result;
        result = method_01(words, letters, score);
        return result;
    }

    
    /**
     * @Description:
     * E1:
     * 输入：words = ["dog","cat","dad","good"], letters = ["a","a","c","d","d","d","g","o","o"], score = [1,0,9,5,0,0,3,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0]
     * 输出：23
     * 1. 回溯算法, 递归的对象是 words[i], 对于每个 i, 可以取或者不取,
     * 2. 如果取了 words[i] = "cat", 则需要判断 letters[] 的剩余字符是否满足组成 words[i] 的条件, boolean flag,
     * 3. 如果满足, 则将 letters[] 中满足 words[i] 的字符减去, 递归处理下一个单词,
     * 4. 对于判断 letter[] 满足条件这个逻辑, 可以用 int[26] count 来记录, 对单词 words[i], 也可以将其转换为 int[] currWordsCount[26]
     * 5. just do it!
     *
     * AC: 1ms(84.00%)/41.59MB(48.00%)
     * @param words 
     * @param letters 
     * @param score 
     * @return int
     * @author marks
     * @CreateDate: 2025/8/19 10:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int n;
    private int ans;
    private int method_01(String[] words, char[] letters, int[] score) {
        n = words.length;
        ans = 0;
        int[] count = new int[26];
        for (char ch : letters) {
            count[ch - 'a']++;
        }
        backtrack(words, count, score, 0, 0);

        return ans;
    }

    private void backtrack(String[] words, int[] count, int[] score, int index, int sumScore) {
        if (index == n) {
            ans = Math.max(ans, sumScore);
            return;
        }
        boolean flag = true;
        char[] tempArray = words[index].toCharArray();
        int[] currWordsCount = new int[26];
        int sum = 0;
        for (char ch : tempArray) {
            sum += score[ch - 'a'];
            currWordsCount[ch - 'a']++;
            if (count[ch - 'a'] < currWordsCount[ch - 'a']) {
                flag = false;
                break;
            }
        }
        if (flag) {
            // 取当前words[index]
            int[] copy = count.clone();
            for (int i = 0; i < 26; i++) {
                copy[i] -= currWordsCount[i];
            }
            backtrack(words, copy, score, index + 1, sumScore + sum);
            backtrack(words, count, score, index + 1, sumScore);
        } else {
            backtrack(words, count, score, index + 1, sumScore);
        }
    }
}
