package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1130 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/13 15:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1130 {

    /**
     * @Description:
     * 给你一个正整数数组 arr，考虑所有满足以下条件的二叉树：
     * 每个节点都有 0 个或是 2 个子节点。
     * 数组 arr 中的值与树的中序遍历中每个叶节点的值一一对应。
     * 每个非叶节点的值等于其左子树和右子树中叶节点的最大值的乘积。
     * 在所有这样的二叉树中，返回每个非叶节点的值的最小可能总和。这个和的值是一个 32 位整数。
     * 如果一个节点有 0 个子节点，那么该节点为叶节点。
     *
     * tips:
     * 2 <= arr.length <= 40
     * 1 <= arr[i] <= 15
     * 答案保证是一个 32 位带符号整数，即小于 2^31 。
     * @param: arr
     * @return int
     * @author marks
     * @CreateDate: 2026/05/13 15:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int mctFromLeafValues(int[] arr) {
        int result;
        result = method_01(arr);
        return result;
    }

    /**
     * @Description:
     * 1. 二叉树的中序遍历: 左-根-右
     * 2. 怎么样使得乘积最小, 即越小的节点值放在最下面, 越大的值放在上面的叶子节点
     * 3. 需要构建一个单调递减栈, 当前位于 i 处, 如果栈不为空, 分情况讨论:
     * 3.1 stack.peek() > arr[i], 不执行操作, 最后将 arr[i] 进行 push
     * 3.2 stack.peek() <= arr[i], 弹出栈顶元素 int top = stack.pop();, 然后对比 下一个栈中的元素和arr[i] 的大小 Math.min(stack.peek(), arr[i]) * top;
     * 3.3 最后处理栈中剩余的元素. 直到栈大小小于 2 为止.
     * AC: 1ms/42.26MB
     * @param: arr
     * @return int
     * @author marks
     * @CreateDate: 2026/05/13 15:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr) {
        Deque<Integer> stack = new ArrayDeque<>();
        int ans = 0;
        for (int j : arr) {
            while (!stack.isEmpty() && stack.peek() <= j) {
                int top = stack.pop();
                if (stack.isEmpty() || stack.peek() > j) {
                    ans += (j * top);
                } else {
                    ans += (stack.peek() * top);
                }
            }
            stack.push(j);
        }
        while (stack.size() >= 2) {
            int top = stack.pop();
            ans += (stack.peek() * top);
        }
        return ans;
    }

}
