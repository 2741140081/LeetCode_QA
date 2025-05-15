package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/13 10:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1609Test {

    @Test
    void isEvenOddTree() {
        // [11,8,6,1,3,9,11,30,20,18,16,12,10,4,2,17]
        int[] nums = {11,8,6,1,3,9,11,30,20,18,16,12,10,4,2,17};
        int n = nums.length;
        int index = 0;
        TreeNode root = new TreeNode(nums[index++]);

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty() && index < n) {
            TreeNode currNode = queue.poll();

            // 赋值左子树
            if (index < n) {
                TreeNode leftNode = new TreeNode(nums[index++]);
                currNode.left = leftNode;
                queue.offer(leftNode);
            }

            // 赋值右子树
            if (index < n) {
                TreeNode rightNode = new TreeNode(nums[index++]);
                currNode.right = rightNode;
                queue.offer(rightNode);
            }
        }

        boolean result = new LeetCode_1609().isEvenOddTree(root);
        System.out.println(result);
    }
}