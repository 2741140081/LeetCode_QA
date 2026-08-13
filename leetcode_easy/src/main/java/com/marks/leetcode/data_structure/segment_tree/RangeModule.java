package com.marks.leetcode.data_structure.segment_tree;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: RangeModule </p>
 * <p>描述: [715. Range 模块] </p>
 * Range模块是跟踪数字范围的模块。设计一个数据结构来跟踪表示为 半开区间 的范围并查询它们。
 * 半开区间 [left, right) 表示所有 left <= x < right 的实数 x 。
 * 实现 RangeModule 类:
 * RangeModule() 初始化数据结构的对象。
 * void addRange(int left, int right) 添加 半开区间 [left, right)，跟踪该区间中的每个实数。
 * 添加与当前跟踪的数字部分重叠的区间时，应当添加在区间 [left, right) 中尚未跟踪的任何数字到该区间中。
 * boolean queryRange(int left, int right) 只有在当前正在跟踪区间 [left, right) 中的每一个实数时，才返回 true ，否则返回 false 。
 * void removeRange(int left, int right) 停止跟踪 半开区间 [left, right) 中当前正在跟踪的每个实数。
 *
 * tips:
 * 1 <= left < right <= 10^9
 * 在单个测试用例中，对 addRange 、  queryRange 和 removeRange 的调用总数不超过 10^4 次
 * @author marks
 * @version v1.0
 * @date 2026/6/16 15:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class RangeModule {
    class Node {
        Node ls, rs;
        int sum, add;
    }
    int N = (int)1e9 + 10;
    Node root = new Node();
    void update(Node node, int lc, int rc, int l, int r, int v) {
        int len = rc - lc + 1;
        if (l <= lc && rc <= r) {
            node.sum = v == 1 ? len : 0;
            node.add = v;
            return ;
        }
        pushdown(node, len);
        int mid = lc + rc >> 1;
        if (l <= mid) update(node.ls, lc, mid, l, r, v);
        if (r > mid) update(node.rs, mid + 1, rc, l, r, v);
        pushup(node);
    }
    int query(Node node, int lc, int rc, int l, int r) {
        if (l <= lc && rc <= r) return node.sum;
        pushdown(node, rc - lc + 1);
        int mid = lc + rc >> 1, ans = 0;
        if (l <= mid) ans = query(node.ls, lc, mid, l, r);
        if (r > mid) ans += query(node.rs, mid + 1, rc, l, r);
        return ans;
    }
    void pushdown(Node node, int len) {
        if (node.ls == null) node.ls = new Node();
        if (node.rs == null) node.rs = new Node();
        if (node.add == 0) return ;
        int add = node.add;
        if (add == -1) {
            node.ls.sum = node.rs.sum = 0;
        } else {
            node.ls.sum = len - len / 2;
            node.rs.sum = len / 2;
        }
        node.ls.add = node.rs.add = add;
        node.add = 0;
    }
    void pushup(Node node) {
        node.sum = node.ls.sum + node.rs.sum;
    }
    public void addRange(int left, int right) {
        update(root, 1, N - 1, left, right - 1, 1);
    }
    public boolean queryRange(int left, int right) {
        return query(root, 1, N - 1, left, right - 1) == right - left;
    }
    public void removeRange(int left, int right) {
        update(root, 1, N - 1, left, right - 1, -1);
    }

}
