package com.marks.leetcode.data_structure.union_find;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_839 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/17 16:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_839 {

    /**
     * @Description:
     * 如果交换字符串 X 中的两个不同位置的字母，使得它和字符串 Y 相等，那么称 X 和 Y 两个字符串相似。
     * 如果这两个字符串本身是相等的，那它们也是相似的。
     * 例如，"tars" 和 "rats" 是相似的 (交换 0 与 2 的位置)； "rats" 和 "arts" 也是相似的，但是 "star" 不与 "tars"，"rats"，或 "arts" 相似。
     *
     * 总之，它们通过相似性形成了两个关联组：{"tars", "rats", "arts"} 和 {"star"}。注意，"tars" 和 "arts" 是在同一组中，即使它们并不相似。形式上，对每个组而言，要确定一个单词在组中，只需要这个词和该组中至少一个单词相似。
     *
     * 给你一个字符串列表 strs。列表中的每个字符串都是 strs 中其它所有字符串的一个字母异位词。请问 strs 中有多少个相似字符串组？
     *
     * tips:
     * 1 <= strs.length <= 300
     * 1 <= strs[i].length <= 300
     * strs[i] 只包含小写字母。
     * strs 中的所有单词都具有相同的长度，且是彼此的字母异位词。
     * @param: strs
     * @return int
     * @author marks
     * @CreateDate: 2025/12/17 16:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numSimilarGroups(String[] strs) {
        int result;
        result = method_01(strs);
        return result;
    }

    /**
     * @Description:
     * 1. 假设有字符串X, Y, int count; 记录位次不同的次数, 例如 X.charAt(i) != Y.charAt(i), count++;
     * 2. if (count == 0 || count == 2); x and y are similar, 继续上述步骤, 使用广度优先搜索, 直到所有字符串都处理完毕
     * AC: 137ms/44.14MB, 时间复杂度O(n^2 * m), 空间复杂度O(n)
     * 时间复杂度有点高, 想一想能从那些方面进行优化
     * @param: strs
     * @return int
     * @author marks
     * @CreateDate: 2025/12/17 16:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] strs) {
        int n = strs.length;
        boolean[] visited = new boolean[n]; // 创建一个长度为n的布尔数组, 用来记录字符串是否被访问过
        int ans = 0; // 创建一个计数器, 用来记录相似字符串组数
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                ans++;
                // 创建一个队列, 用来记录待处理的字符串索引
                Queue<Integer> queue = new LinkedList<>();
                queue.offer(i);
                while (!queue.isEmpty()) {
                    int curr = queue.poll();
                    visited[curr] = true;
                    String x = strs[curr];
                    for (int j = i + 1; j < n; j++) {
                        // 应该可以从 i + 1 开始, 因为 前 i 已经被处理过了
                        if (!visited[j]) {
                            String y = strs[j];
                            int diff = compareStr(x, y);
                            if (diff == 2) {
                                queue.offer(j);
                            } else if (diff == 0) {
                                // 没有必要再次将相同的字符串加入队列, 只需要标记为已访问
                                visited[j] = true;
                            }
                        }
                    }
                }
            }
        }

        return ans;
    }

    private int compareStr(String x, String y) {
        int res = 0;
        for (int i = 0; i < x.length(); i++) {
            if (x.charAt(i) != y.charAt(i)) {
                res++;
            }
        }
        return res;
    }

}
