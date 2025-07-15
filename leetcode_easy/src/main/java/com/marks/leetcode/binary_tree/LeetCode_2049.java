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
 * @date 2025/7/14 14:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2049 {
    private int n;
    private long max = 1L;
    private int count = 0;

    /**
     * @Description:
     * 给你一棵根节点为 0 的 二叉树 ，它总共有 n 个节点，节点编号为 0 到 n - 1 。
     * 同时给你一个下标从 0 开始的整数数组 parents 表示这棵树，其中 parents[i] 是节点 i 的父节点。
     * 由于节点 0 是根，所以 parents[0] == -1 。
     *
     * 一个子树的 大小 为这个子树内节点的数目。
     * 每个节点都有一个与之关联的 分数 。
     * 求出某个节点分数的方法是，将这个节点和与它相连的边全部 删除 ，剩余部分是若干个 非空 子树，这个节点的 分数 为所有这些子树 大小的乘积 。
     *
     * 请你返回有 最高得分 节点的 数目 。
     *
     * tips:
     * n == parents.length
     * 2 <= n <= 10^5
     * parents[0] == -1
     * 对于 i != 0 ，有 0 <= parents[i] <= n - 1
     * parents 表示一棵二叉树。
     * @param parents
     * @return int
     * @author marks
     * @CreateDate: 2025/7/14 14:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countHighestScoreNodes(int[] parents) {
        int result;
        result = method_01(parents);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：parents = [-1,2,0,2,0]
     * 输出：3
     * 解释：
     * - 节点 0 的分数为：3 * 1 = 3
     * - 节点 1 的分数为：4 = 4
     * - 节点 2 的分数为：1 * 1 * 2 = 2
     * - 节点 3 的分数为：4 = 4
     * - 节点 4 的分数为：4 = 4
     * 最高得分为 4 ，有三个节点得分为 4 （分别是节点 1，3 和 4 ）。
     *
     *        0
     *    2      4
     * 1    3
     * ==>
     *        4
     *    2     0
     * 0    0
     *
     * 情况已经很明显了, 假设树有 n 个简单, n = parents.length;
     * 另外我们是二叉树, 目标节点, leftChild, rightChild, myself(1), n - left - right - 1
     * 将不为0的数进行相乘, 乘积是 ans, 判断ans <> res, res 初始化为1, count 默认为0, 如果相等, res++
     *
     * 1. 第一件事情是, 通过parent[] 数组来构建
     *
     * AC: 109ms/84.31MB
     * @param parents
     * @return int
     * @author marks
     * @CreateDate: 2025/7/14 14:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] parents) {
        n = parents.length; // 节点总数
        Map<Integer, List<Integer>> map = new HashMap<>();

        // 一维数组转为二维的Map, Map {key:父节点, value:子节点, 子节点即当前的下标}
        for (int i = 1; i < n; i++) {
            int num = parents[i];
            map.computeIfAbsent(num, k -> new ArrayList<>()).add(i);
        }
        // BFS构建二叉树,
        TreeNode root = new TreeNode(0);
        buildTreeByBFS(root, map);

        // DFS来重新非二叉树节点进行赋值并且计算, 即二叉树节点中存储该节点的左右子树的节点数之和 + 1, 即左子树节点数 + 右子树节点数 + 自身节点数 1
        dfs(root);

        return count;
    }

    private int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftSize = dfs(root.left);
        int rightSize = dfs(root.right);

        // 更新当前节点值, 左右子树和 + 1
        root.val = leftSize + rightSize + 1;

        // 计算当前节点的最大乘积
        int left = leftSize == 0 ? 1 : leftSize;
        int right = rightSize == 0 ? 1 : rightSize;
        int other = (n - root.val) == 0 ? 1 : (n - root.val);
        long product = (long)left * right * other;

        if (product > max) {
            max = product;
            count = 1;
        } else if (product == max) {
            count++;
        }

        return root.val;
    }

    private void buildTreeByBFS(TreeNode root, Map<Integer, List<Integer>> map) {
        // 队列来存储未构建的父节点
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode currNode = queue.poll();
            if (map.containsKey(currNode.val)) {
                List<Integer> childList = map.get(currNode.val);
                if (childList.size() == 2) {
                    currNode.left = new TreeNode(childList.get(0));
                    currNode.right = new TreeNode(childList.get(1));
                    // 子节点作为父节点进行添加
                    queue.add(currNode.left);
                    queue.add(currNode.right);
                } else if (childList.size() == 1){
                    currNode.left = new TreeNode(childList.get(0));
                    queue.add(currNode.left);
                }
            } else {
                // 该节点不是父节点, 证明这是叶子节点, 给叶子节点赋值为1
                currNode.val = 1;
            }
        }
    }
}
