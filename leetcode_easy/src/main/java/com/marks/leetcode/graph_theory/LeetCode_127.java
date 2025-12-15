package com.marks.leetcode.graph_theory;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_127 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/15 10:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_127 {

    /**
     * @Description:
     * 字典 wordList 中从单词 beginWord 到 endWord 的 转换序列 是一个按下述规格形成的序列 beginWord -> s1 -> s2 -> ... -> sk：
     *
     * 每一对相邻的单词只差一个字母。
     * 对于 1 <= i <= k 时，每个 si 都在 wordList 中。注意， beginWord 不需要在 wordList 中。
     * sk == endWord
     * 给你两个单词 beginWord 和 endWord 和一个字典 wordList ，返回 从 beginWord 到 endWord 的 最短转换序列 中的 单词数目 。如果不存在这样的转换序列，返回 0 。
     * @param: beginWord
     * @param: endWord
     * @param: wordList
     * @return int
     * @author marks
     * @CreateDate: 2025/12/15 10:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        int result;
        result = method_01(beginWord, endWord, wordList);
        return result;
    }

    /**
     * @Description:
     * 1. beginWord -> s1 -> s2 -> ... -> sk, beginWord 可以不在 wordList 中,
     * 但是如果在, 那么就需要把 beginWord[i] 的 visited[] 设置为 true
     * 2. 创建一个队列, 添加 beginWord, 创建一个 visited[] 数组, 添加 beginWord[i] 的 visited[] 值为 true
     * 3. 有一点疑问是关于相邻单词只差一个字母, 但是它没有说这种, 单词只差一个字母不同, 但是排列的顺序不一样,
     * 例如, hot, tah 对于这两者而言, 他们到底是差一个字母不同还是3个字母不同。 根据结果来看是3个字母不同
     * 4. AC: 587ms/45.91MB, 时间复杂度O(n^2), 空间复杂度O(n), 怎么优化?
     * 5. 是不是应该对wordList进行按照与beginWord的不同字母的数量进行排序, 然后进行判断, 优化时间复杂度? 待定
     *
     * @param: beginWord
     * @param: endWord
     * @param: wordList
     * @return int
     * @author marks
     * @CreateDate: 2025/12/15 10:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String beginWord, String endWord, List<String> wordList) {
        int n = wordList.size();
        boolean[] visited = new boolean[n];
        // 创建一个队列, 添加 beginWord
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(beginWord);
        // 判断beginWord是否在wordList中, 如果在, 那么就需要把 beginWord[i] 的 visited[] 设置为 true
        // 标记位, 判断endWord是否在wordList中
        boolean flag = false;
        for (int i = 0; i < n; i++) {
            if (wordList.get(i).equals(beginWord)) {
                visited[i] = true;
            }
            // 判断endWord是否在wordList中, 如果不在, 那么返回0
            if (wordList.get(i).equals(endWord)) {
                flag = true;
            }
        }
        if (!flag) {
            return 0;
        }
        int ans = 0;
        // 广度优先搜索
        while (!queue.isEmpty()) {
            int size = queue.size();
            ans++;
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();
                if (endWord.equals(curr)) {
                    return ans;
                }
                for (int j = 0; j < n; j++) {
                    if (!visited[j] && isNext(curr, wordList.get(j))) {
                        queue.offer(wordList.get(j));
                        visited[j] = true;
                    }
                }
            }
        }
        return 0;
    }

    private boolean isNext(String curr, String next) {
        int count = 0;
        int n = curr.length();
        for (int i = 0; i < n; i++) {
            if (curr.charAt(i) != next.charAt(i)) {
                count++;
            }
        }
        return count == 1;
    }

}
