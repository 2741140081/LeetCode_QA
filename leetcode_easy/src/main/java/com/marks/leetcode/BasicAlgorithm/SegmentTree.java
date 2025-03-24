package com.marks.leetcode.BasicAlgorithm;

/**
 * <p>项目名称: 线段树 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/24 17:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class SegmentTree {
    private int[] tree;
    private int n;

    public SegmentTree(int[] nums) {
        this.n = nums.length;
        this.tree = new int[4 * n];
        buildTree(nums, 0, 0, n - 1);
    }

    private void buildTree(int[] nums, int treeIndex, int start, int end) {
        if (start == end) {
            tree[treeIndex] = nums[start];
        } else {
            int mid = (end - start) / 2 + start;
            int leftChildIndex = 2 * treeIndex + 1;
            int rightChildIndex = 2 * treeIndex + 2;

            buildTree(nums, leftChildIndex, start, mid);
            buildTree(nums, rightChildIndex, mid + 1, end);

            // 对于区间求和的线段树，这里使用求和操作
            // 如果是求区间最大值/最小值，则相应地修改为取max/min操作

            tree[treeIndex] = tree[leftChildIndex] + tree[rightChildIndex];
        }
    }

    // 查询 区间 [queryStart, queryEnd] 的区间和
    public int query(int queryStart, int queryEnd) {
        return query(0, 0, n - 1, queryStart, queryEnd);
    }


    public int query(int treeIndex, int start, int end, int queryStart, int queryEnd) {
        if (queryStart < start || queryEnd > end) {
            // 当前查询不在
            return 0;
        } else if (queryStart >= start && queryEnd <= end) {
            return tree[treeIndex];
        } else {
            int mid = (end - start) / 2 + start;
            int leftChildIndex = 2 * treeIndex + 1;
            int rightChildIndex = 2 * treeIndex + 2;

            int leftSum = query(leftChildIndex, start, mid, queryStart, queryEnd);
            int rightSum = query(rightChildIndex, mid + 1, end, queryStart, queryEnd);

            return leftSum + rightSum;
        }
    }

    public void update(int updateIndex, int updateValue) {
        update(0, 0, n - 1, updateIndex, updateValue);
    }

    private void update(int treeIndex, int start, int end, int updateIndex, int updateValue) {
        if (start == end) {
            tree[treeIndex] = updateValue;
        } else {
            int mid = (end - start) / 2 + start;
            int leftChildIndex = treeIndex * 2 + 1;
            int rightChildIndex = treeIndex * 2 + 2;

            if (updateIndex <= mid) {
                update(leftChildIndex, start, mid, updateIndex, updateValue);
            } else {
                update(rightChildIndex, mid + 1, end, updateIndex, updateValue);
            }

            tree[treeIndex] = tree[leftChildIndex] + tree[rightChildIndex];
        }
    }
}
