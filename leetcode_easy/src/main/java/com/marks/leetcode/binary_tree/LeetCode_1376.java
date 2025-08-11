package com.marks.leetcode.binary_tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/30 11:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1376 {
    
    /**
     * @Description:
     * 公司里有 n 名员工，每个员工的 ID 都是独一无二的，编号从 0 到 n - 1。公司的总负责人通过 headID 进行标识。
     *
     * 在 manager 数组中，每个员工都有一个直属负责人，其中 manager[i] 是第 i 名员工的直属负责人。对于总负责人，manager[headID] = -1。题目保证从属关系可以用树结构显示。
     *
     * 公司总负责人想要向公司所有员工通告一条紧急消息。他将会首先通知他的直属下属们，然后由这些下属通知他们的下属，直到所有的员工都得知这条紧急消息。
     *
     * 第 i 名员工需要 informTime[i] 分钟来通知它的所有直属下属（也就是说在 informTime[i] 分钟后，他的所有直属下属都可以开始传播这一消息）。
     *
     * 返回通知所有员工这一紧急消息所需要的 分钟数 。
     * @param n 
     * @param headID 
     * @param manager 
     * @param informTime
     * @return int
     * @author marks
     * @CreateDate: 2025/7/30 11:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        int result;
        result = method_01(n, headID, manager, informTime);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：n = 6, headID = 2, manager = [2,2,-1,2,2,2], informTime = [0,0,1,0,0,0]
     * 输出：1
     * 解释：id = 2 的员工是公司的总负责人，也是其他所有员工的直属负责人，他需要 1 分钟来通知所有员工。
     * 上图显示了公司员工的树结构。
     *
     * AC: 140ms/60.60MB
     * @param n 
     * @param headID 
     * @param manager 
     * @param informTime 
     * @return int
     * @author marks
     * @CreateDate: 2025/7/30 11:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int headID, int[] manager, int[] informTime) {
        List<Integer>[] lists = new List[n];
        for (int i = 0; i < n; i++) {
            lists[i] = new ArrayList<>();
        }

        for (int i = 0; i < manager.length; i++) {
            if (i != headID) {
                lists[manager[i]].add(i);
            }
        }

        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[0])); // 按照时间降序排序
        queue.add(new int[]{informTime[headID], headID});

        int time = 0;
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int preTime = curr[0];
            for (int next : lists[curr[1]]) {
                int nextTime = preTime + informTime[next];
                queue.add(new int[] {nextTime, next});
            }

            time = Math.max(time, queue.peek()[0]);
        }

        return time;
    }
}
