package com.marks.leetcode.graph_theory_algorithm.topological_sorting;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/27 9:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1462 {
    /**
     * @Description: [
     * <p>你总共需要上 numCourses 门课，课程编号依次为 0 到 numCourses-1 。你会得到一个数组 prerequisite ，
     * 其中 prerequisites[i] = [ai, bi] 表示如果你想选 bi 课程，你 必须 先选 ai 课程。
     *
     * <p>有的课会有直接的先修课程，比如如果想上课程 1 ，你必须先上课程 0 ，那么会以 [0,1] 数对的形式给出先修课程数对。
     * <p>先决条件也可以是 间接 的。如果课程 a 是课程 b 的先决条件，课程 b 是课程 c 的先决条件，那么课程 a 就是课程 c 的先决条件。
     *
     * <p>你也得到一个数组 queries ，其中 queries[j] = [uj, vj]。对于第 j 个查询，您应该回答课程 uj 是否是课程 vj 的先决条件。
     *
     * <p>返回一个布尔数组 answer ，其中 answer[j] 是第 j 个查询的答案。
     * <p>tips:
     * 2 <= numCourses <= 100
     * 0 <= prerequisites.length <= (numCourses * (numCourses - 1) / 2)
     * prerequisites[i].length == 2
     * 0 <= ai, bi <= numCourses - 1
     * ai != bi
     * 每一对 [ai, bi] 都 不同
     * 先修课程图中没有环。
     * 1 <= queries.length <= 10^4
     * 0 <= ui, vi <= numCourses - 1
     * ui != vi
     * ]
     * @param numCourses 
     * @param prerequisites 
     * @param queries
     * @return java.util.List<java.lang.Boolean>
     * @author marks
     * @CreateDate: 2024/12/27 9:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        List<Boolean> result;
        result = method_01(numCourses, prerequisites, queries);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * numCourses = 3, prerequisites = [[1,2],[1,0],[2,0]], queries = [[1,0],[1,2]]
     * 2 -> 1, 0 -> 1
     * <p> 1. 思考的结果就是, 将List<Integer>[n] ans, ans 中存储节点 i 的祖先节点, 即题目中的先决条件
     * <p> 2. 遍历queries, 判断queries[][0] 是否存在List<Integer>[queries[][1]] 中
     * <p> 3. 既然要用拓扑排序, 就需要两个必要条件, a. 入度表, b. 邻接表
     * <p> 4. 刚刚看了一下, 我们需要反过来看, [1, 0] ==> 1 -> 0, 只有修完1, 才能继续修 0, 1是0的先决条件, 但是按照入度看,
     * <p> 5. 发现一个问题 0 -> 1/3 <- 2, 0 和 2 是1, 3的先决条件, 但是在复制List时, 会用inDegree[2] = 0时的List覆盖 inDegree[0]时的List
     * <p> 6. 我们需要List是一个Add 而不是 Copy, 并不是这个问题, 而是inDegree[0] = 0 时, inDegree[1]/[3] = 2, 所以0就漏掉了
     * <p> 7. 这一步不是很理解
     * for (int i = 0; i < numCourses; i++) {
     *     isPre[i][j] = isPre[i][j] | isPre[i][curr_i];
     * }
     * AC:13ms/49.26MB
     * ]
     * @param numCourses
     * @param prerequisites
     * @param queries
     * @return java.util.List<java.lang.Boolean>
     * @author marks
     * @CreateDate: 2024/12/27 10:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Boolean> method_01(int numCourses, int[][] prerequisites, int[][] queries) {
        List<Integer>[] list = new ArrayList[numCourses]; // 相当于邻接表
        boolean[][] isPre = new boolean[numCourses][numCourses];
        int[] inDegree = new int[numCourses]; // 入度表
        Arrays.fill(inDegree, 0);
        for (int i = 0; i < numCourses; i++) {
            list[i] = new ArrayList<>();

        }

        for (int[] prerequisite : prerequisites) {
            int start = prerequisite[0];
            int end = prerequisite[1];
            inDegree[end]++;
            list[start].add(end);
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            int curr_i = queue.poll();
            // 修改inDegree[]
            for (int j : list[curr_i]) {
                isPre[curr_i][j] = true;
                for (int i = 0; i < numCourses; i++) {
                    isPre[i][j] = isPre[i][j] | isPre[i][curr_i]; // curr_i 是 j 的祖先, 如果 i 是curr_i的祖先, 依据传递性, i 同样是 j的祖先
                }
                inDegree[j]--;
                if (inDegree[j] == 0) {
                    queue.offer(j);
                }
            }
        }

        List<Boolean> ans = new ArrayList<>();
        for (int i = 0; i < queries.length; i++) {
            int u = queries[i][0];
            int v = queries[i][1];
            ans.add(isPre[u][v]);
        }
        return ans;
    }
}
