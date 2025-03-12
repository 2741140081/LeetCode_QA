package com.marks.leetcode.data_structure.union_find;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/12 10:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1061 {
    /**
     * @Description:
     * 给出长度相同的两个字符串s1 和 s2 ，还有一个字符串 baseStr 。
     *
     * 其中  s1[i] 和 s2[i]  是一组等价字符。
     *
     * 举个例子，如果 s1 = "abc" 且 s2 = "cde"，那么就有 'a' == 'c', 'b' == 'd', 'c' == 'e'。
     * 等价字符遵循任何等价关系的一般规则：
     *
     *  自反性 ：'a' == 'a'
     *  对称性 ：'a' == 'b' 则必定有 'b' == 'a'
     *  传递性 ：'a' == 'b' 且 'b' == 'c' 就表明 'a' == 'c'
     * 例如， s1 = "abc" 和 s2 = "cde" 的等价信息和之前的例子一样，那么 baseStr = "eed" , "acd" 或 "aab"，这三个字符串都是等价的，而 "aab" 是 baseStr 的按字典序最小的等价字符串
     *
     * 利用 s1 和 s2 的等价信息，找出并返回 baseStr 的按字典序排列最小的等价字符串。
     *
     * @param s1 
     * @param s2 
     * @param baseStr
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/3/12 10:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String smallestEquivalentString(String s1, String s2, String baseStr) {
        String result;
        result = method_01(s1, s2, baseStr);
        return result;
    }

    /**
     * @Description:
     * 感觉就是一个并查集, 基于 s1 union s2,
     * 1. 这个可以使用 int[26] 存储, s1 union s2
     * 2. 将字典序大的 x union 小的后面 y, 也就是 当 find(x) => y
     * 3. 遍历 baseStr,  uf.find(i)
     *
     * AC: 2ms/41.00MB
     * @param s1 
     * @param s2 
     * @param baseStr 
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/3/12 10:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s1, String s2, String baseStr) {
        UnionFind uf = new UnionFind(26);
        int n = s1.length();

        for (int i = 0; i < n; i++) {
            int ch_1 = s1.charAt(i) - 'a';
            int ch_2 = s2.charAt(i) - 'a';
            uf.union(ch_1, ch_2);
        }

        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < baseStr.length(); i++) {
            char ch = (char) (uf.find(baseStr.charAt(i) - 'a') + 'a');
            ans.append(ch);
        }

        return ans.toString();
    }

    private static class UnionFind {
        private int[] parent;

        public UnionFind(int size) {
            parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] == x) {
                return parent[x];
            } else {
                x = parent[x];
                return find(x);
            }
        }

        public void union(int x, int y) {
            int parentX = find(x);
            int parentY = find(y);
            if (parentX != parentY) {
                parent[Math.max(parentX, parentY)] = Math.min(parentX, parentY);
            }
        }
    }
}
