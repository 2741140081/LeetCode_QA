package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/15 16:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_636 {
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] result;
        result = method_01(n, logs);
        return result;
    }

    /**
     * @Description:
     * "{function_id}:{"start" | "end"}:{timestamp}"
     * 假设 start = 0, end = 1
     * spend = j - i + 1
     * [0, 0, 2] => [0, 1, 5]
     * 0 => 5 - 2 + 1 = 4
     * AC:12ms/44.53MB
     * @param n
     * @param logs
     * @return int[]
     * @author marks
     * @CreateDate: 2025/1/15 16:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int n, List<String> logs) {
        Deque<int[]> stack = new ArrayDeque<>();
        int[] ans = new int[n];
        for (String log : logs) {
            String[] info = log.split(":");
            int id = Integer.parseInt(info[0]);
            String type = info[1];
            int time = Integer.parseInt(info[2]);
            if ("start".equals(type)) {
                if (!stack.isEmpty()) {
                    ans[stack.peek()[0]] += (time - stack.peek()[1]);
                    stack.peek()[1] = time;
                }
                stack.push(new int[] {id, time});
            } else {
                // type = "end"
                int[] curr_node = stack.poll();
                int start_index = curr_node[1];
                ans[curr_node[0]] += time - start_index + 1;
                if (!stack.isEmpty()) {
                    stack.peek()[1] = time + 1;
                }
            }
        }
        return ans;
    }
}
