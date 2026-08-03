package com.marks.leetcode.array_hard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1889 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/3 16:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1889 {

    /**
     * @Description:
     * 给你 n 个包裹，你需要把它们装在箱子里，每个箱子装一个包裹。
     * 总共有 m 个供应商提供 不同尺寸 的箱子（每个规格都有无数个箱子）。
     * 如果一个包裹的尺寸 小于等于 一个箱子的尺寸，那么这个包裹就可以放入这个箱子之中。
     * 包裹的尺寸用一个整数数组 packages 表示，其中 packages[i] 是第 i 个包裹的尺寸。
     * 供应商用二维数组 boxes 表示，其中 boxes[j] 是第 j 个供应商提供的所有箱子尺寸的数组。
     * 你想要选择 一个供应商 并只使用该供应商提供的箱子，使得 总浪费空间最小 。
     * 对于每个装了包裹的箱子，我们定义 浪费的 空间等于 箱子的尺寸 - 包裹的尺寸 。总浪费空间 为 所有 箱子中浪费空间的总和。
     * 比方说，如果你想要用尺寸数组为 [4,8] 的箱子装下尺寸为 [2,3,5] 的包裹，
     * 你可以将尺寸为 2 和 3 的两个包裹装入两个尺寸为 4 的箱子中，同时把尺寸为 5 的包裹装入尺寸为 8 的箱子中。
     * 总浪费空间为 (4-2) + (4-3) + (8-5) = 6 。
     * 请你选择 最优 箱子供应商，使得 总浪费空间最小 。
     * 如果 无法 将所有包裹放入箱子中，请你返回 -1 。
     * 由于答案可能会 很大 ，请返回它对 10^9 + 7 取余 的结果。
     *
     * tips:
     * n == packages.length
     * m == boxes.length
     * 1 <= n <= 10^5
     * 1 <= m <= 10^5
     * 1 <= packages[i] <= 10^5
     * 1 <= boxes[j].length <= 10^5
     * 1 <= boxes[j][k] <= 10^5
     * sum(boxes[j].length) <= 10^5
     * boxes[j] 中的元素 互不相同 。
     * @param: packages
     * @param: boxes
     * @return int
     * @author marks
     * @CreateDate: 2026/08/03 16:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minWastedSpace(int[] packages, int[][] boxes) {
        int result;
        result = method_01(packages, boxes);
        return result;
    }

    /**
     * @Description:
     * 1. 只能选择一个供应商提供的箱子的尺寸, 所以需要统计每个供应商在最优解的情况下, 此时的浪费的空间数值 long wasteSize
     * 2. 每个箱子都只能存储一个包裹
     * 3. 使用线段树, 用于查询区间内的包裹数量和大小总和
     * AC: 206ms/221.59MB
     * @param: packages
     * @param: boxes
     * @return int
     * @author marks
     * @CreateDate: 2026/08/03 16:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] packages, int[][] boxes) {
        int n = packages.length;
        // 对 packages 进行升序排序
        Arrays.sort(packages);
        Map<Integer, Integer> map = new HashMap<>();
        for (int pack : packages) {
            map.merge(pack, 1, Integer::sum);
        }
        // 最大包裹尺寸
        int maxPackage = packages[n - 1];
        // 创建线段树
        SegmentTree segTree = new SegmentTree(maxPackage + 1);
        // 更新线段树
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            long sum = (long) key * value;
            segTree.update(key, value, sum);
        }

        long minWasteSize = Long.MAX_VALUE;
        // 遍历 boxes
        for (int[] box : boxes) {
            // 升序排序
            Arrays.sort(box);
            int m = box.length;
            if (box[m - 1] < maxPackage) {
                // 不满足条件, 处理下一个供应商方案
                continue;
            }
            int left = 0;
            long wasteSize = 0;
            for (int right : box) {
                Pair res = segTree.query(left, right);
                int cnt = res.getCnt();
                long currSum = res.getSum();
                // 计算浪费空间总和
                wasteSize += ((long) right * cnt - currSum);
                // 更新 left
                left = right + 1;
            }
            // 更新 minWasteSize
            minWasteSize = Math.min(minWasteSize, wasteSize);
        }

        return minWasteSize == Long.MAX_VALUE ? -1 : (int) (minWasteSize % (1e9 + 7));
    }

    class Pair {
        private int cnt;
        private long sum;

        public Pair(int cnt, long sum) {
            this.cnt = cnt;
            this.sum = sum;
        }

        public int getCnt() {
            return cnt;
        }

        public long getSum() {
            return sum;
        }
    }

    /**
     * 线段树类，使用 Pair 数组存储节点信息
     */
    class SegmentTree {
        private int n;
        private Pair[] tree; // 使用 Pair 数组替代原来的 countTree 和 sumTree

        public SegmentTree(int size) {
            this.n = size;
            this.tree = new Pair[4 * n];
            // 初始化节点，防止空指针
            for (int i = 0; i < 4 * n; i++) {
                tree[i] = new Pair(0, 0);
            }
        }

        /**
         * 更新线段树节点
         * @param idx 包裹尺寸（作为索引）
         * @param cnt 该尺寸的数量
         * @param total 该尺寸的总和
         */
        public void update(int idx, int cnt, long total) {
            update(0, 0, n - 1, idx, cnt, total);
        }

        private void update(int node, int start, int end, int idx, int cnt, long total) {
            if (start == end) {
                tree[node] = new Pair(cnt, total);
                return;
            }
            int mid = (start + end) / 2;
            if (idx <= mid) {
                update(2 * node + 1, start, mid, idx, cnt, total);
            } else {
                update(2 * node + 2, mid + 1, end, idx, cnt, total);
            }
            // 合并左右子节点的信息
            Pair leftChild = tree[2 * node + 1];
            Pair rightChild = tree[2 * node + 2];
            tree[node] = new Pair(leftChild.getCnt() + rightChild.getCnt(),
                    leftChild.getSum() + rightChild.getSum());
        }

        /**
         * 公开的查询方法，返回 Pair 对象
         */
        public Pair query(int l, int r) {
            return query(0, 0, n - 1, l, r);
        }

        /**
         * 通用的查询方法，返回包含 count 和 sum 的 Pair 对象
         */
        private Pair query(int node, int start, int end, int l, int r) {
            if (r < start || end < l) {
                return new Pair(0, 0); // 区间无交集，返回 0
            }
            if (l <= start && end <= r) {
                return tree[node]; // 当前节点区间完全包含在查询区间内
            }
            int mid = (start + end) / 2;
            Pair leftRes = query(2 * node + 1, start, mid, l, r);
            Pair rightRes = query(2 * node + 2, mid + 1, end, l, r);
            // 合并左右子节点的查询结果
            return new Pair(leftRes.getCnt() + rightRes.getCnt(),
                    leftRes.getSum() + rightRes.getSum());
        }
    }

}
