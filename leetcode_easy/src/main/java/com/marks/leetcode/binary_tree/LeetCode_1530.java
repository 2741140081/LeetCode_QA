package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/16 17:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1530 {

    private int ans = 0;

    private int maxDistance;

    /**
     * @Description: [功能描述]
     * @param root
     * @param distance
     * @return int
     * @author marks
     * @CreateDate: 2025/4/16 17:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countPairs(TreeNode root, int distance) {
        int result;
        result = method_01(root, distance);
        return result;
    }

    /**
     * @Description:
     * AC: 8ms/44.10MB
     * @param root
     * @param distance
     * @return int
     * @author marks
     * @CreateDate: 2025/4/16 17:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(TreeNode root, int distance) {
        maxDistance = distance;
        dfsGetDistance(root);
        return ans;
    }

    private List<int[]> dfsGetDistance(TreeNode root) {
        if (root == null) {
            return new ArrayList<>(); // 返回空列表
        }
        List<int[]> currNode = new ArrayList<>();

        // 当前节点为叶子节点
        if (root.left == null && root.right == null) {
            currNode.add(new int[] {1, 1}); // 第1个 '1' 表示距离, 第2个 '1' 表示数量
            return currNode;
        }

        // 当前节点为父节点
        TreeNode left = root.left, right = root.right;
        List<int[]> leftList = new ArrayList<>();
        List<int[]> rightList = new ArrayList<>();
        if (left != null) {
            leftList = dfsGetDistance(left);
        }

        if (right != null) {
            rightList = dfsGetDistance(right);
        }

        // 左子树 和右子树均由叶子节点, 判断左右子树叶子节点的距离是否符合要求
        if (leftList.size() > 0 && rightList.size() > 0) {
            for (int i = 0; i < leftList.size(); i++) {
                int leftDistance = leftList.get(i)[0];
                int leftCount = leftList.get(i)[1];
                for (int j = 0; j < rightList.size(); j++) {
                    int rightDistance = rightList.get(j)[0];
                    if (leftDistance + rightDistance <= maxDistance) {
                        // 这个不是相加, 而是相乘
                        ans += (leftCount * rightList.get(i)[1]);
                    }
                }
            }
        }

        // 合并左右子树的叶子节点, 并且list中节点距离加1
        if (leftList.size() > 0) {
            for (int[] ints : leftList) {
                ints[0]++; // 先增加1个距离, 然后再去比较
                if (ints[0] < maxDistance) {
                    currNode.add(ints);
                }
            }
        }

        if (rightList.size() > 0) {
            for (int[] ints : rightList) {
                ints[0]++;
                if (ints[0] < maxDistance) {
                    currNode.add(ints);
                }
            }
        }
        return currNode;
    }
}
