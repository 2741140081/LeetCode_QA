package com.marks.leetcode.data_structure.heap_advance;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/7 10:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_630 {
    /**
     * @Description:
     * 这里有 n 门不同的在线课程，按从 1 到 n 编号。给你一个数组 courses ，
     * 其中 courses[i] = [durationi, lastDayi] 表示第 i 门课将会 持续 上 durationi 天课，并且必须在不晚于 lastDayi 的时候完成。
     *
     * 你的学期从第 1 天开始。且不能同时修读两门及两门以上的课程。
     *
     * 返回你最多可以修读的课程数目。
     * tips:
     * 1 <= courses.length <= 10^4
     * 1 <= durationi, lastDayi <= 10^4
     * @param courses
     * @return int
     * @author marks
     * @CreateDate: 2025/3/7 10:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int scheduleCourse(int[][] courses) {
        int result;
        result = method_01(courses);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：courses = [[100, 200], [200, 1300], [1000, 1250], [2000, 3200]]
     *
     * 直接给一个思路: 现将courses[][1] 进行排序, 升序排序
     * 按照贪心来讲: courses[0] 尽可能小, 并且 courses[1] 尽可能大
     *
     * 总结来说, 我们先将courses[][1] 升序排序, 如果遇到 int curr_day > courses[1],
     * 这表示当前日期已经超过了, 我们需要比较 queue 中存储的 courses[0], 取出最大的 courses[0] 对比 curr_courses[0]
     * 如果, curr_courses[0]
     * [100, 200] [1000, 1250], [200, 1300], [2000, 3200]
     *
     * AC: 35ms/54.10MB
     * @param courses 
     * @return int
     * @author marks
     * @CreateDate: 2025/3/7 10:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] courses) {
        int curr_day = 0; // 初始化当前日期为第一天, 纯纯误导我, 一直描述说从1开始, 但是其实第一天是可以使用的, 所以curr_day 初始化为0
        Arrays.sort(courses, Comparator.comparingInt(o -> o[1])); // 截止日期(lastDay)升序排序
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1); // 存储选修的课程的 duration, 降序排序
        for (int[] course : courses) {
            int spendDay = course[0];
            int endDay = course[1];
            if (curr_day + spendDay <= endDay) {
                curr_day += spendDay;
                queue.offer(spendDay);
            } else {
                // 如果超过end_day
                if (!queue.isEmpty() && queue.peek() >= spendDay) {
                    curr_day -= queue.poll();
                    queue.offer(spendDay);
                    curr_day += spendDay;
                }
            }
        }
        return queue.size();
    }
}
