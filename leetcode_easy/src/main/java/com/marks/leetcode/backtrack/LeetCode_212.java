package com.marks.leetcode.backtrack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/3 9:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_212 {

    /**
     * @Description:
     * 给定一个 m x n 二维字符网格 board 和一个单词（字符串）列表 words， 返回所有二维网格上的单词 。
     * 单词必须按照字母顺序，通过 相邻的单元格 内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。
     * 同一个单元格内的字母在一个单词中不允许被重复使用。
     *
     * tips:
     * m == board.length
     * n == board[i].length
     * 1 <= m, n <= 12
     * board[i][j] 是一个小写英文字母
     * 1 <= words.length <= 3 * 104
     * 1 <= words[i].length <= 10
     * words[i] 由小写英文字母组成
     * words 中的所有字符串互不相同
     * @param board
     * @param words
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2025/9/3 9:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<String> findWords(char[][] board, String[] words) {
        List<String> result1;
        List<String> result2;
        // 获取当前系统时间
        long startTime = System.currentTimeMillis();
        result1 = method_01(board, words);
        long endTime = System.currentTimeMillis();
        System.out.println("method_01 耗时：" + (endTime - startTime) + "ms");
        // 获取当前系统时间
        startTime = System.currentTimeMillis();
        result2 = method_02(board, words);
        endTime = System.currentTimeMillis();
        System.out.println("method_02 耗时：" + (endTime - startTime) + "ms");
//        System.out.println(result2);
        // result1 和 result2 的结果不同, 找到不同的word
        for (String word : result1) {
            if (!result2.contains(word)) {
                System.out.println(word);
            }
        }
        return result1;
    }


    /**
     * @Description:
     * 果然通过字典树进行优化, 成功解决超时问题!!! 时间复杂度降低
     * AC: 215ms/192.53MB
     * @param board
     * @param words
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2025/9/3 11:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private List<String> method_02(char[][] board, String[] words) {
        // 构建字典树
        n = 10; // words[i] 最大长度是10
        Trie root = new Trie();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Trie curr = root;
                char ch = board[i][j];
                if (curr.child[ch - 'a'] == null) {
                    curr.child[ch - 'a'] = new Trie();
                }
                curr = curr.child[ch - 'a'];
                curr.isFinished = true;
                board[i][j] = '#';
                backtrack(board, curr, i, j, 1);
                board[i][j] = ch;
            }
        }
        List<String> ans = new ArrayList<>();
        for (String word : words) {
            if (root.search(word)) {
                ans.add(word);
            }
        }
        return ans;
    }

    private void backtrack(char[][] board, Trie curr, int i, int j, int index) {
        if (index == n) {
            return;
        }
        // next node
        for (int[] dir : dirs) {
            int nextI = i + dir[0];
            int nextJ = j + dir[1];
            if (nextI >= 0 && nextI < board.length && nextJ >= 0 && nextJ < board[0].length && board[nextI][nextJ] != '#') {
                char temp = board[nextI][nextJ];
                Trie next = curr;
                // 判断 Trie 是否存在 child
                if (next.child[board[nextI][nextJ] - 'a'] == null) {
                    next.child[board[nextI][nextJ] - 'a'] = new Trie();
                }
                next = next.child[board[nextI][nextJ] - 'a'];
                next.isFinished = true;

                board[nextI][nextJ] = '#';
                backtrack(board, next,  nextI, nextJ, index + 1);
                board[nextI][nextJ] = temp;
            }
        }
    }

    private class Trie {
        public boolean isFinished;
        public Trie[] child;

        public Trie() {
            this.isFinished = false;
            this.child = new Trie[26];
        }

        public boolean search(String word) {
            Trie node = searchPrefix(word);
            return node != null && node.isFinished;
        }

        private Trie searchPrefix(String word) {
            Trie node = this;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (node.child[ch - 'a'] == null) {
                    return null;
                }
                node = node.child[ch - 'a'];
            }
            return node;
        }
    }



    /**
     * @Description:
     * E1:
     * 输入：board = [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]], words = ["oath","pea","eat","rain"]
     * 输出：["eat","oath"]
     * 1. 回溯算法
     * 超时!!!(63/65)
     * 2. 改思路, 构建一个字典树, 单词的最大长度是10, 通过递归和回溯来构建该字典树, @see method_02()
     * @param board
     * @param words
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2025/9/3 9:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private boolean flag;
    private int n;
    private List<String> method_01(char[][] board, String[] words) {
        List<String>[] lists = new ArrayList[26];

        Set<String> set = new HashSet<>();
        for (int i = 0; i < 26; i++) {
            lists[i] = new ArrayList<>();
        }
        // 将相同首字母的单词放在一起
        for (String word : words) {
            lists[word.charAt(0) - 'a'].add(word);
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                for (String word : lists[board[i][j] - 'a']) {
                    flag = false;
                    n = word.length();
                    board[i][j] = '#';
                    backtrack(board, word, 1, i, j);
                    board[i][j] = word.charAt(0);
                    if (flag) {
                        set.add(word);
                    }
                }
            }
        }
        // 将set集合转为list集合
        List<String> ans = new ArrayList<>(set);
        return ans;
    }

    private void backtrack(char[][] board, String word, int index, int i, int j) {
        if (flag) {
            return;
        }
        if (index == n) {
            flag = true;
            return;
        }

        for (int[] dir : dirs) {
            int nextI = i + dir[0];
            int nextJ = j + dir[1];
            if (nextI >= 0 && nextI < board.length && nextJ >= 0 && nextJ < board[0].length && board[nextI][nextJ] == word.charAt(index)) {
                char temp = board[nextI][nextJ];
                board[nextI][nextJ] = '#';
                backtrack(board, word, index + 1, nextI, nextJ);
                board[nextI][nextJ] = temp;
            }
        }
    }

}
