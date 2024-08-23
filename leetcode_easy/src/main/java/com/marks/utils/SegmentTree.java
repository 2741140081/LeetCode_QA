package com.marks.utils;

/**
 * <p>项目名称: 线段树 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/23 16:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class SegmentTree {
    SegmentTree left; // 左孩子节点
    SegmentTree right; // 右孩子节点
    int val; // 当前节点值
    int add; // 懒惰标记值

    private int N = (int) 1e9;
    private SegmentTree root = new SegmentTree();

    public void update(SegmentTree node, int start, int end, int l, int r, int val) {
        if (l <= start && r >= end) {
            node.val = val;
            node.add = val;
            return;
        }
        pushDown(node);

        int mid = (start + end) / 2;
        if (l <= mid) {
            update(node.left, start, mid, l, r, val);
        }
        if (r > mid) {
            update(node.right, mid + 1, end, l, r, val);
        }

        pushUp(node);

    }
    public int query(SegmentTree node, int start, int end, int l, int r) {
        if (l <= start && r >= end) {
            return node.val;
        }
        pushDown(node);
        int mid = (start + end) / 2;
        int ans = 0;
        if (l <= mid) {
            ans = query(node.left, start, mid, l, r);
        }
        if (r > mid) {
            ans = Math.max(ans, query(node.right, mid + 1, end, l, r));
        }
        return ans;
    }

    private void pushUp(SegmentTree node) {
        // 每个节点存储当前区间的最大值
        node.val = Math.max(node.left.val, node.right.val);
    }

    private void pushDown(SegmentTree node) {
        if (node.left == null) {
            node.left = new SegmentTree();
        }
        if (node.right == null) {
            node.right = new SegmentTree();
        }
        if (node.add == 0) {
            return;
        }
        node.left.val = node.add;
        node.right.val = node.add;
        node.left.add = node.add;
        node.right.add = node.add;
        node.add = 0;
    }
}
