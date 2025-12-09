package com.marks.leetcode.hotLC;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_207 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/9 9:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_207 {

    /**
     * @Description:
     * 你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
     * 在选修某些课程之前需要一些先修课程。
     * 先修课程按数组 prerequisites 给出，其中 prerequisites[i] = [ai, bi] ，表示如果要学习课程 ai 则 必须 先学习课程  bi 。
     * 例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
     * 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
     * @param: numCourses
     * @param: prerequisites
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/09 9:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        boolean result;
        result = method_01(numCourses, prerequisites);
        return result;
    }

    /**
     * @Description:
     * 1. 课程的关系可以描述为图, 构建邻接表
     * 2. 有个疑问是[a1, b1], [a1, c1] 那么如果我需要学习课程a1, 是否只需要满足b1, c1中的一个即可？
     * 3. 错误 41/54, 错误案例: [[1,0],[1,2],[0,1]]; 2 -> 1 <-> 0
     * 4. 需要判断下一个节点的入度是否为0, 如果为0, 才能添加到队列中
     * 5. AC: 34ms/114.90MB
     * 6. 使用List<Integer>[] 来优化邻接表空间和时间
     * 7. AC: 6ms/46.47MB
     * 8. 优化最后的判断, 即提前计算已经完成的课程数量, 每当indegree[i] == 0, 则说明i节点已经完成, int ans = 0, ans++;
     * @param: numCourses
     * @param: prerequisites
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/09 9:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int numCourses, int[][] prerequisites) {
        // 构建邻接表, 可以使用List<Integer>[numCourses] 来优化邻接表空间
        List<Integer>[] graph = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<>();
        }
        // 找到入度为0的节点
        int[] inDegree = new int[numCourses];
        for (int[] prerequisite : prerequisites) {
            graph[prerequisite[1]].add(prerequisite[0]);
            inDegree[prerequisite[0]]++;
        }
        // 判断是否已经遍历, 防止成环
        boolean[] visited = new boolean[numCourses];
        int ans = 0; // 存储已经完成的课程数量
        // 使用bfs求解, 即使用队列来存储入度为0的节点
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
                ans++;
                visited[i] = true;
            }
        }
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int i : graph[cur]) {
                inDegree[i]--;
                if (inDegree[i] == 0) {
                    queue.offer(i);
                    ans++;
                    visited[i] = true;
                }
            }
        }

        return ans == numCourses;
    }
}
