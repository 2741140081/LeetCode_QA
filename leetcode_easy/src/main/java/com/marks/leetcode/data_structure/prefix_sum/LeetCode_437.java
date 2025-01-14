package com.marks.leetcode.data_structure.prefix_sum;

import com.marks.utils.TreeNode;

import java.util.HashMap;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/14 9:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_437 {
    private HashMap<Long, Integer> map = new HashMap<>();
    private int count = 0;
    private int target;
    /**
     * @Description: 
     * 给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
     * 路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
     * tips:
     * 二叉树的节点个数的范围是 [0,1000]
     * -10^9 <= Node.val <= 10^9
     * -1000 <= targetSum <= 1000
     * @param root 
     * @param targetSum 
     * @return int
     * @author marks
     * @CreateDate: 2025/1/14 9:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int pathSum(TreeNode root, int targetSum) {
        int result;
        result = method_01(root, targetSum);
        return result;
    }
    
    /**
     * @Description: 树的遍历 + 前缀和 + HashMap
     * 其中树的遍历方式: DFS, 前序遍历
     * pre_j - pre_i = targetSum
     * pre_i = pre_j - targetSum
     * AC:3ms/43.48MB
     * @param root 
     * @param targetSum
     * @return int
     * @author marks
     * @CreateDate: 2025/1/14 9:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(TreeNode root, int targetSum) {
        target = targetSum;
        map.put(0L, 1);
        dfsTree(root, 0L);
        return count;
    }

    private void dfsTree(TreeNode root, long currSum) {
        if (root == null) {
            return;
        }
        currSum += root.getVal();
        long key = currSum - target;
        if (map.containsKey(key)) {
            count += map.get(key);
        }
        map.put(currSum, map.getOrDefault(currSum, 0) + 1);
        dfsTree(root.getLeft(), currSum);
        dfsTree(root.getRight(), currSum);
        map.put(currSum, map.getOrDefault(currSum, 0) - 1);
    }
}
