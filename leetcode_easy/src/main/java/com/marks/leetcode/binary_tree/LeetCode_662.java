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
 * @date 2025/5/30 18:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_662 {

    /**
     * @Description:
     * 给你一棵二叉树的根节点 root ，返回树的 最大宽度 。
     *
     * 树的 最大宽度 是所有层中最大的 宽度 。
     *
     * 每一层的 宽度 被定义为该层最左和最右的非空节点（即，两个端点）之间的长度。将这个二叉树视作与满二叉树结构相同，两端点间会出现一些延伸到这一层的 null 节点，这些 null 节点也计入长度。
     *
     * 题目数据保证答案将会在  32 位 带符号整数范围内。
     *
     * tips:
     * 树中节点的数目范围是 [1, 3000]
     * -100 <= Node.val <= 100
     * @param root
     * @return int
     * @author marks
     * @CreateDate: 2025/5/30 18:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int widthOfBinaryTree(TreeNode root) {
        int result;
        result = method_01(root);
        result = method_02(root);
        return result;
    }

    /**
     * @Description:
     * 1. 求每层的最小和最大的编号, 两者相减就是宽度max - min + 1
     * 2. 第n层的第i个节点的子节点编号, {2*i - 1, 2*i}
     * 查看官方题解
     * AC: 2ms/43.24MB
     * @param root
     * @return int
     * @author marks
     * @CreateDate: 2025/6/3 11:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int ans = 1;

        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(root, 1));

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Pair pair = queue.poll();
                TreeNode currNode = pair.node;
                int number = pair.level;

                if (currNode.left != null) {
                    queue.add(new Pair(currNode.left, 2 * number - 1));
                    list.add(2 * number - 1);
                }

                if (currNode.right != null) {
                    queue.add(new Pair(currNode.right, 2 * number));
                    list.add(2 * number);
                }
            }
            if (!list.isEmpty()) {
                ans = Math.max(ans, (list.get(list.size() - 1) - list.get(0) + 1));
            }
        }

        return ans;
    }

    class Pair {
        TreeNode node;
        int level;

        public Pair(TreeNode node, int level) {
            this.node = node;
            this.level = level;
        }
    }

    /**
     * @Description:
     * 1. 感觉的思路应该是用 一颗 满 二叉树, 不去管节点是否为空, 即假设该节点是null, 那么也给它补全它的子节点的null值
     * 2. 使用List<T> list, 存储所有的值, 包括null值。
     * 3. 去除list的前后的null值, 计算中间的值
     * 4. 可能存在类似链表的数, 这个数宽度是1?
     * 5. 对于null的值,我们可以将其修改 if(node.left is null) then node.left = new TreeNode(-101);
     * 6. 判断结束条件? 如果遍历 queue 完成后, 如果 queue中的值存在不为 -101的值, 就继续遍历, 否则退出循环.
     *
     * 暴力破解失败, 超时! 还是要通过编号来处理
     * @param root
     * @return int
     * @author marks
     * @CreateDate: 2025/5/30 18:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int ans = root != null ? 1 : 0;
        int dummyValue = -101;
        boolean flag = true;


        while (!queue.isEmpty() && flag) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                TreeNode currNode = queue.poll();
                if (currNode.left == null) {
                    currNode.left = new TreeNode(dummyValue);
                }

                if (currNode.right == null) {
                    currNode.right = new TreeNode(dummyValue);
                }

                list.add(currNode.left.val, currNode.right.val);

                queue.add(currNode.left);
                queue.add(currNode.right);
            }

            // 删除list前后无关的值
            int count = deleteValueForList(list, dummyValue);
            if (count == 0) {
                flag = false;
            }
            if (count > 1) {
                ans = Math.max(count, ans);
            }
        }


        return ans;
    }

    private int deleteValueForList(List<Integer> list, int dummyValue) {
        // 删除前面的-101
        while (!list.isEmpty() && list.get(0) == dummyValue) {
            list.remove(0);
        }

        // 删除后面的-101
        while(!list.isEmpty() && list.get(list.size()-1) == -101) {
            list.remove(list.size()-1);
        }


        return list.size();
    }
}
