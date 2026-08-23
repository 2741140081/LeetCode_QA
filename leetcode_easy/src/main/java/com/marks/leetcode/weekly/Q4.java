package com.marks.leetcode.weekly;

import java.util.HashMap;
import java.util.Map;

public class Q4 {

    public boolean[] validSubarrays(int[] nums, int k, int[][] queries) {
        boolean[] result;
        result = method_01(nums, k, queries);
        return result;
    }

    // 线段树, 数据需要使用 Map 进行存储, 需要查询不同的数字, 以及各个数字的频率
    // 超时
    private boolean[] method_01(int[] nums, int k, int[][] queries) {
        SegmentTree tree = new SegmentTree(nums);
        int n = queries.length;
        boolean[] ans = new boolean[n];
        for (int i = 0; i < n; i++) {
            Map<Integer, Integer> map = tree.query(queries[i][0], queries[i][1]);
            if (map.size() == k) {
                boolean flag = true;
                for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    if (entry.getValue() % 2 != 0) {
                        flag = false;
                        break;
                    }
                }
                ans[i] = flag;
            }
        }

        return ans;
    }


    class SegmentTree {
        private Map<Integer, Map<Integer, Integer>> tree;
        private int n;

        public SegmentTree(int[] nums) {
            this.n = nums.length;
            this.tree = new HashMap<>();
            buildTree(nums, 0, 0, n - 1);
        }


        private void buildTree(int[] nums, int treeIndex, int start, int end) {
            if (start == end) {
                tree.put(treeIndex, new HashMap<>());
                tree.get(treeIndex).put(nums[start], 1);
            } else {
                int mid = (end - start) / 2 + start;
                int leftChildIndex = 2 * treeIndex + 1;
                int rightChildIndex = 2 * treeIndex + 2;

                buildTree(nums, leftChildIndex, start, mid);
                buildTree(nums, rightChildIndex, mid + 1, end);

                tree.put(treeIndex, new HashMap<>());
                Map<Integer, Integer> curr = tree.get(treeIndex);
                // 遍历左节点
                for (Map.Entry<Integer, Integer> leftEntry : tree.get(leftChildIndex).entrySet()) {
                    curr.merge(leftEntry.getKey(), leftEntry.getValue(), Integer::sum);
                }
                // 遍历右节点
                for (Map.Entry<Integer, Integer> rightEntry : tree.get(rightChildIndex).entrySet()) {
                    curr.merge(rightEntry.getKey(), rightEntry.getValue(), Integer::sum);
                }
            }
        }

        public Map<Integer, Integer> query(int queryStart, int queryEnd) {
            return query(0, 0, n - 1, queryStart, queryEnd);
        }

        private Map<Integer, Integer> query(int treeIndex, int start, int end, int queryStart, int queryEnd) {
            if (queryEnd < start || queryStart > end) {
                return new HashMap<>();
            } else if (queryStart <= start && queryEnd >= end) {
                return new HashMap<>(tree.getOrDefault(treeIndex, new HashMap<>()));
            } else {
                int mid = (end - start) / 2 + start;
                int leftChildIndex = 2 * treeIndex + 1;
                int rightChildIndex = 2 * treeIndex + 2;

                Map<Integer, Integer> left = query(leftChildIndex, start, mid, queryStart, queryEnd);
                Map<Integer, Integer> right = query(rightChildIndex, mid + 1, end, queryStart, queryEnd);
                for (Map.Entry<Integer, Integer> entry : right.entrySet()) {
                    left.merge(entry.getKey(), entry.getValue(), Integer::sum);
                }
                return left;
            }
        }
    }
}
