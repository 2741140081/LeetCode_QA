package com.marks.leetcode.array_hard;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.TreeMap;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2071 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/5 17:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2071 {

    /**
     * @Description:
     * 给你 n 个任务和 m 个工人。每个任务需要一定的力量值才能完成，
     * 需要的力量值保存在下标从 0 开始的整数数组 tasks 中，第 i 个任务需要 tasks[i] 的力量才能完成。
     * 每个工人的力量值保存在下标从 0 开始的整数数组 workers 中，第 j 个工人的力量值为 workers[j] 。
     * 每个工人只能完成 一个 任务，且力量值需要 大于等于 该任务的力量要求值（即 workers[j] >= tasks[i] ）。
     * 除此以外，你还有 pills 个神奇药丸，可以给 一个工人的力量值 增加 strength 。
     * 你可以决定给哪些工人使用药丸，但每个工人 最多 只能使用 一片 药丸。
     * 给你下标从 0 开始的整数数组tasks 和 workers 以及两个整数 pills 和 strength ，请你返回 最多 有多少个任务可以被完成。
     *
     * tips:
     * n == tasks.length
     * m == workers.length
     * 1 <= n, m <= 5 * 10^4
     * 0 <= pills <= m
     * 0 <= tasks[i], workers[j], strength <= 10^9
     * @param: tasks
     * @param: workers
     * @param: pills
     * @param: strength
     * @return int
     * @author marks
     * @CreateDate: 2026/08/05 17:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
        int result;
        result = method_01(tasks, workers, pills, strength);
        return result;
    }

    /**
     * @Description:
     * 1. 查看官方题解, 使用二分法 + 有序集合 + 贪心
     * 2. 难点在于如何安排, 当前是先安排任务难度大的, 并且在力量值不足时, 选择一个符合的最小力量值
     * AC: 1153ms/96.57MB
     * 优化: 使用双端队列替代有序集合, 优化时间复杂度
     * AC: 67ms/66.09MB
     * @param: tasks
     * @param: workers
     * @param: pills
     * @param: strength
     * @return int
     * @author marks
     * @CreateDate: 2026/08/05 17:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] tasks, int[] workers, int pills, int strength) {
        // 对 tasks 进行升序排序
        Arrays.sort(tasks);
        // 对 workers 升序排序
        Arrays.sort(workers);
        int n = tasks.length, m = workers.length;
        // 通过二分法找到最大完成任务数量
        int ans = 0;
        int left = 1, right = Math.min(n, m);
        while (left <= right) {
            int mid = (right - left) / 2 + left;
//            if (check(tasks, workers, pills, strength, mid)) {
            if (checkByDeque(tasks, workers, pills, strength, mid)) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return ans;
    }

    /**
     * @Description:
     * 1. 使用双端队列替代有序集合
     * @param: tasks
     * @param: workers
     * @param: pills
     * @param: strength
     * @param: mid
     * @return boolean
     * @author marks
     * @CreateDate: 2026/08/06 10:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean checkByDeque(int[] tasks, int[] workers, int pills, int strength, int mid) {
        int m = workers.length;
        // 创建队列
        Deque<Integer> queue = new ArrayDeque<>();
        int idx = m - 1;// 待添加的下标
        // 倒序遍历 [0 ~ mid - 1]
        for (int i = mid - 1; i >= 0; i--) {
            // 向队列中添加所有符合要求的工人
            while (idx >= m - mid && workers[idx] + strength >= tasks[i]) {
                queue.offer(workers[idx]);
                idx--;
            }
            if (queue.isEmpty()) {
                return false;
            }
            // 如果队列右侧(大)的元素值 >= tasks[i], 则弹出该元素
            if (queue.peek() >= tasks[i]) {
                queue.pop();
            } else {
                // 需要使用药物
                if (pills == 0) {
                    return false;
                }
                // 弹出左侧(小)的元素
                queue.pollLast();
                pills--;
            }
        }
        return true;
    }

    /**
     * @Description:
     * 1. 用来判断能否完成 k 个任务
     * 2. 使用有序集合存储工人 mid 个 力量最大的工人信息
     * @param: tasks
     * @param: workers
     * @param: pills
     * @param: strength
     * @param: mid
     * @return boolean
     * @author marks
     * @CreateDate: 2026/08/06 9:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean check(int[] tasks, int[] workers, int pills, int strength, int mid) {
        // 构建有序集合 TreeMap
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        // 遍历 [m - mid, m - 1], 得到 mid 个最大力量的工人
        int m = workers.length;
        for (int i = m - mid; i < m; i++) {
            treeMap.merge(workers[i], 1, Integer::sum);
        }
        // 对 tasks[0 ~ mid - 1] 进行降序排序, 优先处理需要力量值更高的任务
        for (int i = mid - 1; i >= 0; i--) {
            int currTask = tasks[i];
            // 如果有序集合的最大值小于 currTask
            Integer key = treeMap.lastKey();
            if (key < currTask) {
                if (pills == 0) { // 没有药物可用, 返回 false
                    return false;
                }
                // 需要使用药物来提升力量
                currTask -= strength;
                // 在有序集合中找到最小大于 currTask 的, 然后移除该工人
                key = treeMap.ceilingKey(currTask);
                if (key == null) {
                    return false;
                }
                // 移除一个 key
                treeMap.merge(key, -1, Integer::sum);
                if (treeMap.get(key) == 0) {
                    treeMap.remove(key);
                }
                // 减少药物数量
                pills--;
            } else {
                // 有序集合尾部元素大于需要的力量值, 取一个尾部元素用来处理当前任务
                treeMap.merge(key, -1, Integer::sum);
                if (treeMap.get(key) == 0) {
                    treeMap.remove(key);
                }
            }
        }

        return true;
    }

}
