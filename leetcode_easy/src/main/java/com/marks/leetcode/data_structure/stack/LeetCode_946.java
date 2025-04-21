package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/15 11:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_946 {
    /**
     * @Description:
     * 给定 pushed 和 popped 两个序列，每个序列中的 值都不重复，
     * 只有当它们可能是在最初空栈上进行的推入 push 和弹出 pop 操作序列的结果时，返回 true；否则，返回 false 。
     * @param pushed
     * @param popped
     * @return boolean
     * @author marks
     * @CreateDate: 2025/1/15 11:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        boolean result;
        result = method_01(pushed, popped);
        return result;
    }

    /**
     * @Description:
     * AC:1ms/43.54MB
     * @param pushed
     * @param popped
     * @return boolean
     * @author marks
     * @CreateDate: 2025/1/15 11:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] pushed, int[] popped) {
        Deque<Integer> stack = new ArrayDeque<>();
        int right = 0;
        for (int i = 0; i < pushed.length; i++) {
            stack.push(pushed[i]);
            while (!stack.isEmpty() && stack.peek() == popped[right]) {
                stack.poll();
                right++;
            }
        }
        if (stack.isEmpty()) {
            return true;
        }else {
            return false;
        }
    }
}
