package com.marks.leetcode.DP;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/22 15:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1130 {

    
    /**
     * @Description:
     * 给你一个正整数数组 arr，考虑所有满足以下条件的二叉树：
     *
     * 每个节点都有 0 个或是 2 个子节点。
     * 数组 arr 中的值与树的中序遍历中每个叶节点的值一一对应。
     * 每个非叶节点的值等于其左子树和右子树中叶节点的最大值的乘积。
     * 在所有这样的二叉树中，返回每个非叶节点的值的最小可能总和。这个和的值是一个 32 位整数。
     *
     * 如果一个节点有 0 个子节点，那么该节点为叶节点。
     * @param arr
     * @return int
     * @author marks
     * @CreateDate: 2025/10/22 15:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int mctFromLeafValues(int[] arr) {
        int result;
        result = method_01(arr);
        return result;
    }

    
    /**
     * @Description:
     * E1:
     * 输入：arr = [6,2,4]
     * 输出：32
     * 1. 找到最小的非叶子节点之和, 中序遍历: 左子树 + 根节点 + 右子树
     * 2. arr[0] 必定是左子树的叶子节点 arr[0] = 6, arr[2] 必定是右子树, 因为这棵树相当于是完全二叉树,
     * 所有根节点必定有2个子节点, 所有的叶子节点都是0个节点
     * 3. 只有3个叶子节点的二叉树是怎么样的? 3层, 6, 2 是左子树, 4 是右子树
     * 4.1 12, 4 => 24 => sum = 24 + 12 = 36
     * 4.2 6 是左子树, 2, 4 是右子树, 2 * 4 =8, 6, 4 = 24, 8 + 24 = 32
     *
     * @param arr 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/22 15:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr) {
        int ans = 0;
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < arr.length; i++) {
            int cur = arr[i];
            while (!queue.isEmpty() && queue.peek() <= cur) {
                int y = queue.poll();
                if (queue.isEmpty() || queue.peek() > cur) {
                    ans += (cur * y);
                } else {
                    ans += (y * queue.peek());
                }
            }
            queue.push(cur);
        }
        while (queue.size() > 1) {
            int y = queue.poll();
            ans += (y * queue.peek());
        }
        return ans;
    }

}
