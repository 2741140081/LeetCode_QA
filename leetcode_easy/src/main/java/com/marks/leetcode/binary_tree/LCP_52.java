package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/22 15:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_52 {
    private Map<Integer, Integer> map = new HashMap<>(); // 其中key = 0 表示蓝色, key = 1 表示红色
    private List<Integer> list = new ArrayList<>();
    /**
     * @Description:
     * 欢迎各位勇者来到力扣城，本次试炼主题为「二叉搜索树染色」。
     *
     * 每位勇士面前设有一个二叉搜索树的模型，模型的根节点为 root，树上的各个节点值均不重复。初始时，所有节点均为蓝色。现在按顺序对这棵二叉树进行若干次操作， ops[i] = [type, x, y] 表示第 i 次操作为：
     *
     * type 等于 0 时，将节点值范围在 [x, y] 的节点均染蓝
     * type 等于 1 时，将节点值范围在 [x, y] 的节点均染红
     * 请返回完成所有染色后，该二叉树中红色节点的数量。
     *
     * 注意：
     *
     * 题目保证对于每个操作的 x、y 值定出现在二叉搜索树节点中
     *
     * tips:
     * @param root 
     * @param ops
     * @return int
     * @author marks
     * @CreateDate: 2025/7/22 15:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int getNumber(TreeNode root, int[][] ops) {
        int result;
        result = method_01(root, ops);
        result = method_02(root, ops);
        result = method_03(root, ops);
        return result;
    }

    /**
     * @Description:
     * 换一个思路, 找到二叉搜索树的最大和最小值
     *
     * @param root
     * @param ops
     * @return int
     * @author marks
     * @CreateDate: 2025/7/22 15:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(TreeNode root, int[][] ops) {
        int max = Math.max(root.val, dfsMaxValue(root));
        SegmentTree st = new SegmentTree(max + 1);

        for (int[] op : ops) {
            st.updateRange(op[0], op[1], op[2]);
        }
        dfs(root);
        int ans = 0;
        for (int num : list) {
            if (st.query(num) == 1) {
                ans++;
            }
        }
        return ans;
    }

    private int dfsMaxValue(TreeNode root) {
        int max = root.val;
        if (root.right != null) {
            max = dfsMaxValue(root.right);
        }
        return max;
    }

    private class SegmentTree {
        private long[] tree;
        private long[] lazy;
        private int size;

        public SegmentTree(int size) {
            this.size = size;
            this.tree = new long[4 * size];
            this.lazy = new long[4 * size];
            Arrays.fill(lazy, -1); // -1表示没有延迟更新
        }

        public void updateRange(int value, int start, int end) {
            updateRange(1, 0, size - 1, start, end, value);
        }

        private void updateRange(int node, int nodeStart, int nodeEnd,
                                 int start, int end, int value) {
            // 处理延迟更新
            if (lazy[node] != -1) {
                tree[node] = lazy[node];
                if (nodeStart != nodeEnd) {
                    lazy[2 * node] = lazy[node];
                    lazy[2 * node + 1] = lazy[node];
                }
                lazy[node] = -1;
            }

            // 当前区间与更新区间无交集
            if (nodeStart > end || nodeEnd < start) return;

            // 当前区间完全包含在更新区间内
            if (start <= nodeStart && nodeEnd <= end) {
                tree[node] = value;
                if (nodeStart != nodeEnd) {
                    lazy[2 * node] = value;
                    lazy[2 * node + 1] = value;
                }
                return;
            }

            // 递归更新左右子树
            int mid = (nodeStart + nodeEnd) / 2;
            updateRange(2 * node, nodeStart, mid, start, end, value);
            updateRange(2 * node + 1, mid + 1, nodeEnd, start, end, value);
        }

        public long query(int index) {
            return query(1, 0, size - 1, index);
        }

        private long query(int node, int nodeStart, int nodeEnd, int index) {
            // 处理延迟更新
            if (lazy[node] != -1) {
                tree[node] = lazy[node];
                if (nodeStart != nodeEnd) {
                    lazy[2 * node] = lazy[node];
                    lazy[2 * node + 1] = lazy[node];
                }
                lazy[node] = -1;
            }

            // 找到目标索引
            if (nodeStart == nodeEnd && nodeStart == index) {
                return tree[node];
            }

            int mid = (nodeStart + nodeEnd) / 2;
            if (index <= mid) {
                return query(2 * node, nodeStart, mid, index);
            } else {
                return query(2 * node + 1, mid + 1, nodeEnd, index);
            }
        }
    }


    // 保存的是没有操作过的节点
    TreeSet<Integer> set;

    // 159ms/84.87MB
    private int method_03(TreeNode root, int[][] ops) {
        if(root == null) return 0;

        set = new TreeSet<>();
        build(root);

        int res = 0;

        for(int i = ops.length - 1; i >= 0; i--){
            while(true){
                // 找到第一个大于x的节点
                Integer upper = set.higher(ops[i][1] - 1);
                if(upper == null || upper > ops[i][2]) break;
                // 删除操作过的节点
                set.remove(upper);
                // 如果是染红，记录红色节点数
                if(ops[i][0] == 1) res++;
            }

        }

        return res;
    }

    private void build(TreeNode root){
        if(root == null) return;
        build(root.left);
        set.add(root.val);
        build(root.right);
    }

    /**
     * @Description:
     * 1. 如果我们直接倒序 操作ops, 下一次操作会将上一次操作的相同位置的颜色进行覆盖, 如果我们直接倒序遍历 ops, 并且通过map 分别存储已经染色的节点值,
     * 可以通过map判断当前节点是否染色, 如果染色了就跳过, 如果没有染色, 则对该节点染色, 并且将节点添加到 map 中, 可以设置红蓝两个map, 或者只设置一个map, 将 key设置为不同
     * 2. 我们需要知道 二叉树搜索树 的相关特性? 右子树 > 根节点 > 左子树, 感觉 二叉树的节点值不同
     * 3. 我们能不能直接将二叉搜索树 进行展开为一维List<Integer>, 可以试试看
     *
     * 超时!!! 83/89
     *
     * 修改
     * @param root
     * @param ops
     * @return int
     * @author marks
     * @CreateDate: 2025/7/22 15:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(TreeNode root, int[][] ops) {
        dfs(root);
        // 升序排序
        list.sort(Comparator.comparingInt(o -> o));
        int ans = 0;
        for (int i = ops.length - 1; i >= 0; i--) {
            int color = ops[i][0];
            int left = ops[i][1];
            int right = ops[i][2];
            for (int j = list.indexOf(left); j <= list.indexOf(right); j++) {
                int currVal = list.get(j);
                if (!map.containsKey(currVal)) {
                    map.put(currVal, color);
                    if (color == 1) {
                        ans++;
                    }
                }
            }
        }
        return ans;
    }

    /**
     * @Description:
     * 添加值到list中
     * @param root
     * @return void
     * @author marks
     * @CreateDate: 2025/7/22 15:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void dfs(TreeNode root) {
        if (root == null) {
            return;
        }
        list.add(root.val);
        dfs(root.left);
        dfs(root.right);
    }

}
