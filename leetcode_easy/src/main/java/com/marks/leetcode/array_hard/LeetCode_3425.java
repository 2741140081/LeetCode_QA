package com.marks.leetcode.array_hard;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3425 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/4 17:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3425 {

    /**
     * @Description:
     * 给你一棵根节点为节点 0 的无向树，树中有 n 个节点，编号为 0 到 n - 1 ，
     * 这棵树通过一个长度为 n - 1 的二维数组 edges 表示，其中
     * edges[i] = [ui, vi, lengthi] 表示节点 ui 和 vi 之间有一条长度为 lengthi 的边。
     * 同时给你一个整数数组 nums ，其中 nums[i] 表示节点 i 的值。
     *
     * 特殊路径 指的是树中一条从祖先节点 往下 到后代节点且经过节点的值 互不相同 的路径。
     * 注意 ，一条路径可以开始和结束于同一节点。
     * 请你返回一个长度为 2 的数组 result ，其中 result[0] 是 最长 特殊路径的 长度 ，result[1] 是所有 最长特殊路径中的 最少 节点数目。
     *
     * tips:
     * 2 <= n <= 5 * 10^4
     * edges.length == n - 1
     * edges[i].length == 3
     * 0 <= ui, vi < n
     * 1 <= lengthi <= 10^3
     * nums.length == n
     * 0 <= nums[i] <= 5 * 10^4
     * 输入保证 edges 表示一棵合法的树。
     * @param: edges
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2026/09/04 17:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] longestSpecialPath(int[][] edges, int[] nums) {
        int[] result;
        result = method_01(edges, nums);
        return result;
    }

    /**
     * @Description:
     * 1. 树的定义为无环图, 并且每个节点只连接一个父节点, 除根节点外
     * 2. 由于特殊路径的定义是, 祖先节点到后代节点的路径, 并且路径上节点值不同, 所以需要存储节点的值。
     * 3. 由于可以是部分节点来构成特殊路径，length_i > 0 的数, 所以 特殊路径的子路径必定不是最长的特殊路径
     * 4. 当在查找特殊路径的过程中, 如果添加节点 i 之后, 此时路径中存在重复值, 需要通过滑动窗口的形式, 缩小
     * 窗口, 使得特殊路径中不包含重复元素, 并且记录此时的长度和节点数目
     * 5. 采用深度优先搜索, 并且在处理节点时, 需要将剔除的节点存储起来, 以便后续恢复
     * todo
     * @param: edges
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2026/09/04 17:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[][] edges, int[] nums) {

        return null;
    }

}
