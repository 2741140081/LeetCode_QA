package com.marks.leetcode.graph_theory_algorithm.topological_sorting;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/24 16:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_210 {
    /**
     * @Description: [
     * <p>现在你总共有 numCourses 门课需要选，记为 0 到 numCourses - 1。给你一个数组 prerequisites ，其中 prerequisites[i] = [ai, bi] ，表示在选修课程 ai 前 必须 先选修 bi 。
     * <p>例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示：[0,1] 。
     * <p>返回你为了学完所有课程所安排的学习顺序。可能会有多个正确的顺序，你只要返回 任意一种 就可以了。如果不可能完成所有课程，返回 一个空数组 。
     * ]
     * @param numCourses 
     * @param prerequisites 
     * @return int[]
     * @author marks
     * @CreateDate: 2024/12/24 16:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] result;
        result = method_01(numCourses, prerequisites);
        return result;
    }

    /**
     * @Description: [
     * <p>1. 什么时候返回空, 当有向图中存在环时, 返回空数组, Such as: A -> B, B -> C, C -> A
     * <p>2. 使用拓扑排序如何判断图中是否存在环, 拓扑排序, 即不断地删除图中入度为 0的节点, 并且更新向关联的节点, 直到所有入度为 0 的节点全部删除,
     * 判断此时图中是否还存在未遍历的节点, 如果存在, 那么返回空数组, 如果不存在, 那么按照入度为0 节点的poll顺序返回
     * <p>3. 还是用队列存储入度为0的节点, 二维数组List<Integer>[n] 存储管理性, 相当于邻接表, inDegree[n] 存储节点的入度值
     * ]
     * @param numCourses
     * @param prerequisites
     * @return int[]
     * @author marks
     * @CreateDate: 2024/12/24 16:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int numCourses, int[][] prerequisites) {
        int[] inDegree = new int[numCourses];
        List<Integer>[] list = new ArrayList[numCourses];
        Arrays.fill(inDegree, 0);
        for (int i = 0; i < numCourses; i++) {
            list[i] = new ArrayList<>();
        }

        for (int[] prerequisite : prerequisites) {
            int start = prerequisite[1];
            int end = prerequisite[0];
            inDegree[end]++;
            list[start].add(end);
        }
        Queue<Integer> queue = new LinkedList<>();
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
                ans.add(i);
            }
        }

        while (!queue.isEmpty()) {
            Integer curr_i = queue.poll();
            for (Integer j : list[curr_i]) {
                inDegree[j]--;
                if (inDegree[j] == 0) {
                    queue.offer(j);
                    ans.add(j);
                }
            }
        }

        if (ans.size() == numCourses) {
            int[] result = ans.stream().mapToInt(Integer::intValue).toArray();
            return result;
        }else {
            return new int[0];
        }
    }
}
