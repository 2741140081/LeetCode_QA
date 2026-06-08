package com.marks.leetcode.hash_table;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_Interview_17_7 </p>
 * <p>描述: 面试题 17.07. 婴儿名字
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/8 14:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_Interview_17_7 {

    /**
     * @Description:
     * 每年，政府都会公布一万个最常见的婴儿名字和它们出现的频率，也就是同名婴儿的数量。
     * 有些名字有多种拼法，例如，John 和 Jon 本质上是相同的名字，但被当成了两个名字公布出来。
     * 给定两个列表，一个是名字及对应的频率，另一个是本质相同的名字对。设计一个算法打印出每个真实名字的实际频率。
     * 注意，如果 John 和 Jon 是相同的，并且 Jon 和 Johnny 相同，则 John 与 Johnny 也相同，即它们有传递和对称性。
     * 在结果列表中，选择 字典序最小 的名字作为真实名字。
     *
     * tips:
     * names.length <= 100000
     * @param: names
     * @param: synonyms
     * @return java.lang.String[]
     * @author marks
     * @CreateDate: 2026/06/08 14:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String[] trulyMostPopular(String[] names, String[] synonyms) {
        String[] result;
        result = method_01(names, synonyms);
        return result;
    }

    /**
     * @Description:
     * 1. 构建并查集, 根据 synonyms 构建并查集, 每一个名称对应一个下标
     * 2. 防止 names 中存在重复的名称, 使用 map 存储, int n = map.size();
     * 3. 构建 UnionFind uf = new UnionFind(n); int count = uf.count; 即最后真实名称的数量.
     * 4. int id = uf.get(currName); 然后构建新的 map 分别存储 minMap 和 countMap, 存储 minName 和 count.
     * minName: Map<Integer, String>; countMap: Map<Integer, Integer>;
     * 5. "John(15)", 需要通过字符串分割得到名称和数量.
     * 6. "(Jon,John)" 需要通过字符串分割分别得到两个名称, 然后通过 uf.union(id1, id2);
     * AC: 106ms/72.02MB
     * @param: names
     * @param: synonyms
     * @return java.lang.String[]
     * @author marks
     * @CreateDate: 2026/06/08 14:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String[] method_01(String[] names, String[] synonyms) {
        Map<String, Integer> map = new HashMap<>();
        for (String name : names) {
            String tempName = name.substring(0, name.indexOf('('));
            int cnt = Integer.parseInt(name.substring(name.indexOf('(') + 1, name.indexOf(')')));
            map.merge(tempName, cnt, Integer::sum);
        }
        Map<String, Integer> indexMap = new HashMap<>();
        int index = 0;

        for (String synonym : synonyms) {
            // 通过逗号确定分割点, 然后截取名称
            String s1 = synonym.substring(1, synonym.indexOf(","));
            String s2 = synonym.substring(synonym.indexOf(",") + 1, synonym.length() - 1);
            if (!indexMap.containsKey(s1)) {
                indexMap.put(s1, index++);
            }
            if (!indexMap.containsKey(s2)) {
                indexMap.put(s2, index++);
            }
        }

        UnionFind uf = new UnionFind(index);
        for (String synonym : synonyms) {
            // 通过逗号确定分割点, 然后截取名称
            String s1 = synonym.substring(1, synonym.indexOf(","));
            String s2 = synonym.substring(synonym.indexOf(",") + 1, synonym.length() - 1);
            int id1 = indexMap.get(s1);
            int id2 = indexMap.get(s2);
            uf.union(id1, id2);
        }
        Map<Integer, String> minMap = new HashMap<>();
        Map<Integer, Integer> countMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String name = entry.getKey();
            if (indexMap.containsKey(name)) {
                int id = uf.get(indexMap.get(name));
                // 判断 minMap中是否存在该名称, 如果不存在则添加, 否则更新名称
                if (!minMap.containsKey(id)) {
                    minMap.put(id, name);
                } else {
                    String minName = minMap.get(id);
                    if (name.compareTo(minName) < 0) {
                        minMap.put(id, name);
                    }
                }
                countMap.merge(id, entry.getValue(), Integer::sum);
            } else {
                minMap.put(index, name);
                countMap.put(index, entry.getValue());
                index++;
            }
        }
        int n = minMap.size();
        String[] result = new String[n];
        int idx = 0;
        for (Map.Entry<Integer, String> entry : minMap.entrySet()) {
            int id = entry.getKey();
            String minName = entry.getValue();
            int cnt = countMap.get(id);
            result[idx++] = minName + "(" + cnt + ")";
        }

        return result;
    }

    private class UnionFind {
        private int[] root;
        private int count;

        public UnionFind(int n) {
            this.root = new int[n];
            this.count = n;
            for (int i = 0; i < n; i++) {
                root[i] = i;
            }
        }

        public int get(int x) {
            if (x == root[x]) {
                return x;
            }
            return root[x] = get(root[x]);
        }

        public void union(int x, int y) {
            int rootX = get(x);
            int rootY = get(y);
            if (rootX != rootY) {
                root[rootX] = rootY;
                count--;
            }
        }

    }

}
