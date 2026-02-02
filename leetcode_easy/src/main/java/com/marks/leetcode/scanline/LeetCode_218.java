package com.marks.leetcode.scanline;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_218 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/2 10:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_218 {

    /**
     * @Description:
     * 城市的 天际线 是从远处观看该城市中所有建筑物形成的轮廓的外部轮廓。给你所有建筑物的位置和高度，请返回 由这些建筑物形成的 天际线 。
     * 每个建筑物的几何信息由数组 buildings 表示，其中三元组 buildings[i] = [lefti, righti, heighti] 表示：
     *
     * lefti 是第 i 座建筑物左边缘的 x 坐标。
     * righti 是第 i 座建筑物右边缘的 x 坐标。
     * heighti 是第 i 座建筑物的高度。
     * 你可以假设所有的建筑都是完美的长方形，在高度为 0 的绝对平坦的表面上。
     *
     * 天际线 应该表示为由 “关键点” 组成的列表，格式 [[x1,y1],[x2,y2],...] ，并按 x 坐标 进行 排序 。关键点是水平线段的左端点。
     * 列表中最后一个点是最右侧建筑物的终点，y 坐标始终为 0 ，仅用于标记天际线的终点。此外，任何两个相邻建筑物之间的地面都应被视为天际线轮廓的一部分。
     *
     * 注意：输出天际线中不得有连续的相同高度的水平线。例如 [...[2 3], [4 5], [7 5], [11 5], [12 7]...] 是不正确的答案；三条高度为 5 的线应该在最终输出中合并为一个：[...[2 3], [4 5], [12 7], ...]
     * tips:
     * 1 <= buildings.length <= 10^4
     * 0 <= lefti < righti <= 2^31 - 1
     * 1 <= heighti <= 2^31 - 1
     * buildings 按 lefti 非递减排序
     * @param: buildings
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2026/02/02 10:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> result;
        result = method_02(buildings);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：buildings = [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]
     * 输出：[[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]]
     * 1. 查看官方题解, 使用扫描线 + 优先队列进行处理
     * @param: buildings
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2026/02/02 15:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> method_02(int[][] buildings) {
        // 构建优先队列
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] - a[1]); // 根据 height 降序排序
        List<Integer> boundaries = new ArrayList<>();
        for (int[] building : buildings) {
            boundaries.add(building[0]);
            boundaries.add(building[1]);
        }
        Collections.sort(boundaries);
        // 构建结果集合
        List<List<Integer>> ans = new ArrayList<>();
        int n = buildings.length;
        int idx = 0;
        for (int boundary : boundaries) {
            while (idx < n && buildings[idx][0] <= boundary) { // left <= boundary
                pq.offer(new int[]{buildings[idx][1], buildings[idx][2]}); // [right, height]
                idx++;
            }
            while (!pq.isEmpty() && pq.peek()[0] <= boundary) { // right <= boundary
                pq.poll();
            }
            int maxHeight = pq.isEmpty() ? 0 : pq.peek()[1]; // height
            if (ans.isEmpty() || maxHeight != ans.get(ans.size() - 1).get(1)) {
                ans.add(Arrays.asList(boundary, maxHeight));
            }
        }
        return ans;
    }


}
