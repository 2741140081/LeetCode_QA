package com.marks.leetcode.data_structure.binary_indexed_tree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/19 15:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCR_170 {
    /**
     * @Description:
     * 在股票交易中，如果前一天的股价高于后一天的股价，则可以认为存在一个「交易逆序对」。
     * 请设计一个程序，输入一段时间内的股票交易记录 record，返回其中存在的「交易逆序对」总数。
     *
     * tips:
     * 0 <= record.length <= 50000
     * @param record
     * @return int
     * @author marks
     * @CreateDate: 2025/3/19 15:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int reversePairs(int[] record) {
        int result;
        result = method_01(record);
        return result;
    }

    /**
     * @Description:
     * 输入：record = [9, 7, 5, 4, 6]
     * 输出：8
     * 解释：交易中的逆序对为 (9, 7), (9, 5), (9, 4), (9, 6), (7, 5), (7, 4), (7, 6), (5, 4)。
     *
     * 1. 我们先把树状数组写出来
     *
     * AC: 59ms/54.13MB
     * @param record 
     * @return int
     * @author marks
     * @CreateDate: 2025/3/19 15:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] record) {
        int n = record.length;
        if (n <= 1) {
            return 0;
        }
        int[] record_copy = Arrays.copyOf(record, n);
        Arrays.sort(record_copy);
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(record_copy[i], i + 1);
        }

        int ans = 0;
        BinaryIndexedTree tree = new BinaryIndexedTree(n);
        tree.add(map.get(record[0]));

        for (int i = 1; i < n; i++) {
            int val = record[i];
            ans += (i - tree.get(map.get(val)));
            tree.add(map.get(val));
        }
        return ans;
    }

    private static class BinaryIndexedTree {
        private int[] tree;

        public BinaryIndexedTree(int n) {
            tree = new int[n + 1];
        }

        public void add(int i) {
            while (i < tree.length) {
                tree[i]++;
                i += i & -i;
            }
        }

        public int get(int i) {
            int sum = 0;
            while (i > 0) {
                sum += tree[i];
                i -= i & -i;
            }
            return sum;
        }
    }
}
