package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCR_148 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/29 16:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCR_148 {

    /**
     * @Description:
     * 现在图书馆有一堆图书需要放入书架，并且图书馆的书架是一种特殊的数据结构，只能按照 一定 的顺序 放入 和 拿取 书籍。
     * 给定一个表示图书放入顺序的整数序列 putIn，请判断序列 takeOut 是否为按照正确的顺序拿取书籍的操作序列。你可以假设放入书架的所有书籍编号都不相同。
     * @param: putIn
     * @param: takeOut
     * @return boolean
     * @author marks
     * @CreateDate: 2026/01/29 16:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean validateBookSequences(int[] putIn, int[] takeOut) {
        boolean result;
        result = method_01(putIn, takeOut);
        return result;
    }

    /**
     * @Description:
     * 输入：putIn = [6,7,8,9,10,11], takeOut = [9,11,10,8,7,6]
     * 输出：true
     * AC: 3ms/45.61MB
     * @param: putIn
     * @param: takeOut
     * @return boolean
     * @author marks
     * @CreateDate: 2026/01/29 16:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] putIn, int[] takeOut) {
        // 构建栈模拟放入和取出的操作
        Deque<Integer> stack = new ArrayDeque<>();
        int index = 0; // 取出的索引
        for (int book : putIn) {
            // 先放入 putIn[i]
            stack.push(book);
            // 尝试取出 takeOut[index]
            while (!stack.isEmpty() && stack.peek() == takeOut[index]) {
                stack.pop();
                index++;
            }

        }
        return stack.isEmpty();
    }

}
