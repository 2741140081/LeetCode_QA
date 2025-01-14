package com.marks.leetcode.data_structure.stack;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/14 17:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1441 {
    /**
     * @Description:
     * 给你一个数组 target 和一个整数 n。每次迭代，需要从  list = { 1 , 2 , 3 ..., n } 中依次读取一个数字。
     *
     * 请使用下述操作来构建目标数组 target ：
     *
     * "Push"：从 list 中读取一个新元素， 并将其推入数组中。
     * "Pop"：删除数组中的最后一个元素。
     * 如果目标数组构建完成，就停止读取更多元素。
     * 题目数据保证目标数组严格递增，并且只包含 1 到 n 之间的数字。
     *
     * 请返回构建目标数组所用的操作序列。如果存在多个可行方案，返回任一即可。
     * @param target
     * @param n
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2025/1/14 17:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<String> buildArray(int[] target, int n) {
        List<String> result;
        result = method_01(target, n);
        return result;
    }

    /**
     * @Description:
     * AC:0ms/41.98MB
     * @param target
     * @param n
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2025/1/14 17:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<String> method_01(int[] target, int n) {
        Deque<Integer> stack = new LinkedList<>();
        List<String> ans = new ArrayList<>();
        for (int i = 1, index = 0; i <= n && index < target.length; i++) {
            stack.push(i);
            ans.add("Push");
            if (stack.peek() == target[index]) {
                index++;
            }else {
                stack.poll();
                stack.pop();
                ans.add("Poll");
            }
        }
        return ans;
    }
}
