package com.marks.leetcode.data_structure.heap_advance;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/17 14:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1953 {
    /**
     * @Description:
     * 给你 n 个项目，编号从 0 到 n - 1 。同时给你一个整数数组 milestones ，其中每个 milestones[i] 表示第 i 个项目中的阶段任务数量。
     * 你可以按下面两个规则参与项目中的工作：
     *
     * 每周，你将会完成 某一个 项目中的 恰好一个 阶段任务。你每周都 必须 工作。
     * 在 连续的 两周中，你 不能 参与并完成同一个项目中的两个阶段任务。
     * 一旦所有项目中的全部阶段任务都完成，或者执行仅剩的一个阶段任务将会导致你违反上面的规则，你将 停止工作。注意，由于这些条件的限制，你可能无法完成所有阶段任务。
     * 返回在不违反上面规则的情况下你 最多 能工作多少周。
     *
     * tips:
     * n = 10^5
     * milestones[i] = 10^9
     * @param milestones
     * @return long
     * @author marks
     * @CreateDate: 2025/2/17 14:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long numberOfWeeks(int[] milestones) {
        long result;
        result = method_01(milestones);
        result = method_02(milestones);
        return result;
    }

    /**
     * @Description:
     *
     * AC: 2ms/54.91MB
     * @param milestones
     * @return long
     * @author marks
     * @CreateDate: 2025/2/17 14:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_02(int[] milestones) {
        int longest = milestones[0]; // 耗时最长工作所需周数
        long rest = 0;
        for (int count : milestones) {
            longest = Math.max(longest, count);
            rest += count;
        }
        // 其余工作共计所需周数
        rest -= longest;
        if (longest > rest + 1) {
            // 此时无法完成所耗时最长的工作
            return rest * 2 + 1;
        } else {
            // 此时可以完成所有工作
            return longest + rest;
        }
    }

    /**
     * @Description:
     * 暴力必定超时, 因为时间复杂度 10^5 * 10^9 = 10^14
     * @param milestones
     * @return long
     * @author marks
     * @CreateDate: 2025/2/17 14:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] milestones) {
        int n = milestones.length;
        long ans = 0;
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> {
            if (o1[0] == o2[0]) {
                return o1[1] - o2[1];
            } else {
                return o2[0] - o1[0];
            }
        });
        for (int i = 0; i < n; i++) {
            queue.offer(new int[] {milestones[i], i});
        }

        int index = -1;
        while (!queue.isEmpty()) {
            if (queue.size() > 1) {
                // 每次取2个
                int[] first = queue.poll();
                if (index != first[1]) {
                    ans++;
                    if (queue.peek()[0] == 1) {
                        ans++;
                        index = queue.poll()[1];
                    } else {
                        int[] second = queue.poll();
                        second[0]--;
                        index = second[1];
                        ans++;
                        queue.offer(second);
                    }
                    if (first[0] > 1) {
                        first[0]--;
                        queue.offer(first);
                    }

                }
            } else {
                ans++;
                break;
            }
        }

        return ans;
    }
}
