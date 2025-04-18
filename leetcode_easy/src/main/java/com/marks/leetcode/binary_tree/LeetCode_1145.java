package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/18 14:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1145 {

    private TreeNode target;
    
    /**
     * @Description:
     * 有两位极客玩家参与了一场「二叉树着色」的游戏。
     * 游戏中，给出二叉树的根节点 root，树上总共有 n 个节点，且 n 为奇数，其中每个节点上的值从 1 到 n 各不相同。
     *
     * 最开始时：
     *
     * 「一号」玩家从 [1, n] 中取一个值 x（1 <= x <= n）；
     * 「二号」玩家也从 [1, n] 中取一个值 y（1 <= y <= n）且 y != x。
     * 「一号」玩家给值为 x 的节点染上红色，而「二号」玩家给值为 y 的节点染上蓝色。
     *
     * 之后两位玩家轮流进行操作，「一号」玩家先手。
     * 每一回合，玩家选择一个被他染过色的节点，将所选节点一个 未着色 的邻节点（即左右子节点、或父节点）进行染色（「一号」玩家染红色，「二号」玩家染蓝色）。
     *
     * 如果（且仅在此种情况下）当前玩家无法找到这样的节点来染色时，其回合就会被跳过。
     *
     * 若两个玩家都没有可以染色的节点时，游戏结束。着色节点最多的那位玩家获得胜利 ✌️。
     *
     * 现在，假设你是「二号」玩家，根据所给出的输入，假如存在一个 y 值可以确保你赢得这场游戏，则返回 true ；若无法获胜，就请返回 false 。
     * @param root 
     * @param n 
     * @param x
     * @return boolean
     * @author marks
     * @CreateDate: 2025/4/18 14:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        boolean result;
        result = method_01(root, n, x);
        return result;
    }

    /**
     * @Description:
     * 1. 假设x是叶子节点, y最优解是占领 x 所在的父节点,那么我们判断总节点数 n - 1 > 1,
     * 2. 假设x是根节点/父节点
     * 2.1 只有一个子节点
     * 2.2 有2个子节点
     * 3. 占领x的父节点, 并且计算 x 的子节点个数和(a), 那么我们可以 n - a = b
     * 4. a 包含3个部分 a = left + right + 1,
     * left, right, b, 取这者的最大值
     * max = Math.max(left, Math.max(right, b))
     * max > n - max
     *
     * AC: 0ms/40.49MB
     * @param root 
     * @param n 
     * @param x 
     * @return boolean
     * @author marks
     * @CreateDate: 2025/4/18 14:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(TreeNode root, int n, int x) {
        // 遍历root, 找到节点 x
        dfsFindTargetNode(root, x);

        // 目标节点左子树的数量
        int left = dfsGetCount(target.left);
        int right = dfsGetCount(target.right);

        int extra = n - 1 - left - right; // 目标节点以外的节点数量
        int max = Math.max(extra, Math.max(left, right)); // y 能够得到的最大节点数量

        return max > n - max;
    }

    private int dfsGetCount(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int curr = 1; // 当前节点
        return curr + dfsGetCount(root.left) + dfsGetCount(root.right);
    }

    private void dfsFindTargetNode(TreeNode root, int x) {
        if (root == null) {
            return;
        }
        if (root.val == x) {
            target = root;
            // 找到之后返回, 避免浪费时间
            return;
        }

        // 递归查找
        dfsFindTargetNode(root.left, x);
        dfsFindTargetNode(root.right, x);
    }
}
