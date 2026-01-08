package com.marks.leetcode.graph_theory;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCP_62 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/8 10:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_62 {

    /**
     * @Description:
     * 为了缓解「力扣嘉年华」期间的人流压力，组委会在活动期间开设了一些交通专线。
     * path[i] = [a, b] 表示有一条从地点 a通往地点 b 的 单向 交通专线。
     * 若存在一个地点，满足以下要求，我们则称之为 交通枢纽：
     *
     * 所有地点（除自身外）均有一条 单向 专线 直接 通往该地点；
     * 该地点不存在任何 通往其他地点 的单向专线。
     * 请返回交通专线的 交通枢纽。若不存在，则返回 -1。
     *
     * 注意：
     *
     * 对于任意一个地点，至少被一条专线连通。
     * @param: path
     * @return int
     * @author marks
     * @CreateDate: 2026/01/08 10:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int transportationHub(int[][] path) {
        int result;
        result = method_01(path);
        return result;
    }

    /**
     * @Description:
     * 1. 入度和出度表, inDegree[i] = 0, outDegree[i] = n - 1; 返回 i
     * 2. 需要得到 n 的大小, max
     * AC: 2ms/45.85MB
     * @param: path
     * @return int
     * @author marks
     * @CreateDate: 2026/01/08 10:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] path) {
        // max 存储节点值最大值
        int max = 0;
        for (int i = 0; i < path.length; i++) {
            max = Math.max(max, Math.max(path[i][0], path[i][1]));
        }
        int n = max + 1;
        int[] inDegree = new int[n]; // 入度表
        int[] outDegree = new int[n]; // 出度表
        for (int i = 0; i < path.length; i++) {
            inDegree[path[i][1]]++;
            outDegree[path[i][0]]++;
        }
        int ans = -1; // 初始值为 -1
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == n - 1 && outDegree[i] == 0) {
                ans = i;
                break;
            }
        }
        return ans;
    }


}
