package com.marks.leetcode.array_hard;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCP_32 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/8 10:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_32 {

    /**
     * @Description:
     * 某实验室计算机待处理任务以 [start,end,period] 格式记于二维数组 tasks，
     * 表示完成该任务的时间范围为起始时间 start 至结束时间 end 之间，需要计算机投入 period 的时长，
     * 注意：
     * period 可为不连续时间
     * 首尾时间均包含在内
     * 处于开机状态的计算机可同时处理任意多个任务，请返回电脑最少开机多久，可处理完所有任务。
     *
     * tips:
     * 2 <= tasks.length <= 10^5
     * tasks[i].length == 3
     * 0 <= tasks[i][0] <= tasks[i][1] <= 10^9
     * 1 <= tasks[i][2] <= tasks[i][1]-tasks[i][0] + 1
     * @param: tasks
     * @return int
     * @author marks
     * @CreateDate: 2026/09/08 10:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int processTasks(int[][] tasks) {
        int result;
        result = method_01(tasks);
        return result;
    }

    /**
     * @Description:
     * 1. 为了使得时间最短, 需要尽可能多地重叠任务
     * todo
     * @param: tasks
     * @return int
     * @author marks
     * @CreateDate: 2026/09/08 10:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] tasks) {

        return 0;
    }

}
