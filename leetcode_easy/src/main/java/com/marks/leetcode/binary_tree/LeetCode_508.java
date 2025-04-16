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
 * @date 2025/4/16 10:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_508 {

    private Map<Integer, Integer> map = new HashMap<>(); // 存储二叉树和及其出现次数
    
    /**
     * @Description:
     * 给你一个二叉树的根结点 root ，请返回出现次数最多的子树元素和。
     * 如果有多个元素出现的次数相同，返回所有出现次数最多的子树元素和（不限顺序）。
     *
     * 一个结点的 「子树元素和」 定义为以该结点为根的二叉树上所有结点的元素之和（包括结点本身）。
     * @param root
     * @return int[]
     * @author marks
     * @CreateDate: 2025/4/16 10:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] findFrequentTreeSum(TreeNode root) {
        int[] result;
        result = method_01(root);
        return result;
    }

    /**
     * @Description:
     * AC: 7ms/44.60MB
     * @param root 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/4/16 10:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(TreeNode root) {
        getSum(root);

        // 对 map 进行排序
        int maxValue = Collections.max(map.values());
        List<Integer> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == maxValue) {
                list.add(entry.getKey());
            }
        }

        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    private int getSum(TreeNode root) {
        int currSum = root.val;
        TreeNode left = root.left, right = root.right;

        if (left != null) {
            currSum += getSum(left);
        }

        if (right != null) {
            currSum += getSum(right);
        }
        map.merge(currSum, 1, Integer::sum);
        return currSum;
    }
}
