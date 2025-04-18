package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/18 15:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2385 {
    private TreeNode target;
    private int distance = -1; // root 节点到达 target 节点的 距离

    private int num;

    /**
     * @Description:
     * 给你一棵二叉树的根节点 root ，二叉树中节点的值 互不相同 。另给你一个整数 start 。在第 0 分钟，感染 将会从值为 start 的节点开始爆发。
     *
     * 每分钟，如果节点满足以下全部条件，就会被感染：
     *
     * 节点此前还没有感染。
     * 节点与一个已感染节点相邻。
     * 返回感染整棵树需要的分钟数。
     *
     * tips:
     * 树中节点的数目在范围 [1, 10^5] 内
     * 1 <= Node.val <= 10^5
     * 每个节点的值 互不相同
     * 树中必定存在值为 start 的节点
     * @param root
     * @param start
     * @return int
     * @author marks
     * @CreateDate: 2025/4/18 15:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int amountOfTime(TreeNode root, int start) {
        int result;
        result = method_01(root, start);
        return result;
    }

    /**
     * @Description:
     * 1. 找到 target node, 并且得到 root 节点到达 target节点的距离
     *
     * @param root
     * @param start
     * @return int
     * @author marks
     * @CreateDate: 2025/4/18 15:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(TreeNode root, int start) {

        if (root.val == start) {
            distance = 0; // start 从 root开始
        } else {
            num = start;
            dfsGetDistance(root, 0);
        }
        return 0;
    }

    private void dfsGetDistance(TreeNode root, int step) {
        if (root == null) {
            return;
        }
        if (distance == -1) {
            // 表示为进行初始化赋值, 或者说没有找到对应 target 节点
            if (root.val == num) {
                target = root;
                distance = step;
            }
        }
        // 递归执行
        TreeNode left = root.left, right = root.right;
        dfsGetDistance(left, step + 1);
        dfsGetDistance(right, step + 1);
    }
}
