package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/15 14:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2673 {

    private int ans = 0;

    /**
     * @Description:
     * 给你一个整数 n 表示一棵 满二叉树 里面节点的数目，节点编号从 1 到 n 。
     * 根节点编号为 1 ，树中每个非叶子节点 i 都有两个孩子，分别是左孩子 2 * i 和右孩子 2 * i + 1 。
     *
     * 树中每个节点都有一个值，用下标从 0 开始、长度为 n 的整数数组 cost 表示，其中 cost[i] 是第 i + 1 个节点的值。
     * 每次操作，你可以将树中 任意 节点的值 增加 1 。你可以执行操作 任意 次。
     *
     * 你的目标是让根到每一个 叶子结点 的路径值相等。请你返回 最少 需要执行增加操作多少次。
     * 注意：
     * 满二叉树 指的是一棵树，它满足树中除了叶子节点外每个节点都恰好有 2 个子节点，且所有叶子节点距离根节点距离相同。
     * 路径值 指的是路径上所有节点的值之和。
     *
     * tips:
     * 3 <= n <= 10^5
     * n + 1 是 2 的幂
     * cost.length == n
     * 1 <= cost[i] <= 10^4
     * @param n
     * @param cost
     * @return int
     * @author marks
     * @CreateDate: 2025/7/15 14:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minIncrements(int n, int[] cost) {
        int result;
        result = method_01(n, cost);
        result = method_02(n, cost);
        return result;
    }

    /**
     * @Description:
     * E1: 输入：n = 7, cost = [1,5,2,2,3,3,1]
     * 直接复制成 arr = [0, 1, 5, 2, 2, 3, 3, 1]
     *
     * @param n
     * @param cost
     * @return int
     * @author marks
     * @CreateDate: 2025/7/15 15:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int n, int[] cost) {
        int res = 0;
        for (int i = n - 2; i > 0; i -= 2) {
            res += Math.abs(cost[i] - cost[i + 1]);
            // 叶节点 i 和 i+1 的双亲节点下标为 i/2（整数除法）
            cost[i / 2] += Math.max(cost[i], cost[i + 1]);
        }
        return res;
    }

    /**
     * @Description:
     * E1: 输入：n = 7, cost = [1,5,2,2,3,3,1]
     * 输出：6
     * 解释：我们执行以下的增加操作：
     * - 将节点 4 的值增加一次。
     * - 将节点 3 的值增加三次。
     * - 将节点 7 的值增加两次。
     * 从根到叶子的每一条路径值都为 9 。
     * 总共增加次数为 1 + 3 + 2 = 6 。
     * 这是最小的答案。
     *
     *
     * 假设有一个父节点 p, p的左右子树 cost之和分别是 cLeft 和 cRight,
     * 如果c1 != c2, 需要给ans += Math.abs(c1 - c2)
     * 1 + 2 + (8 - 5) = 1 + 2 + 3 = 6
     *
     * 1. 构建满二叉树
     * 2. 给父节点赋值, 并且计算 左右子树差值, 父节点赋值, 父节点自身的值 += 左右子节点最大值
     *
     * AC: 27ms/56.41MB
     *
     * 不是最优解, 而且可能是最差的解
     * @param n
     * @param cost
     * @return int
     * @author marks
     * @CreateDate: 2025/7/15 14:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[] cost) {
        TreeNode root = new TreeNode(1);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode currNode = queue.poll();
            int i = currNode.val;
            if ((2 * i + 1) <= n) {
                currNode.left = new TreeNode(2 * i);
                currNode.right = new TreeNode(2 * i + 1);
                queue.add(currNode.left);
                queue.add(currNode.right);
            }
        }

        dfs(root, cost);

        return ans;
    }

    private int dfs(TreeNode root, int[] cost) {
        if (root == null) {
            return 0;
        }
        int left = dfs(root.left, cost);
        int right = dfs(root.right, cost);

        // 左右子树差值
        ans += Math.abs(left - right);

        int self = cost[root.val - 1];

        return self + Math.max(left, right);
    }
}
