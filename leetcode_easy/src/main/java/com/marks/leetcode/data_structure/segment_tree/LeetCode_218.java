package com.marks.leetcode.data_structure.segment_tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        result = method_01(buildings);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：buildings = [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]
     * 输出：[[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]]
     * 1. 先自行手动模拟2个矩阵的合并操作, 看看到底如何得到结果, 这两个矩阵 [15,20,10],[19,24,8] => [15, 10] [20, 10] [19, 8] [24, 8]
     * 2. 算了直接看题解用分治法处理吧有矩阵A, B; 对于AB的合并有几种情况
     * 2.1 A, B 没有交集, 返回 [A_l_h, A_r_0] [B_l_h, B_r_0]
     * 2.2 A, B 存在交集, 分成A_l, A_r, B_l, B_r
     * A. 假设 A_r > B_l && A_r < B_r 然后需要比较高度,
     * a. A_h > B_h, [A_l_hA, A_r_hB, B_r_0] 合并成功
     * b. A_h < B_h, [A_l_hA, B_l_hB, B_r_0]
     * B. 假设 A_r < B_l && A_r > B_r, 即B在A的中间
     * a. A_h < B_h, [A_l_hA, B_l_hB, B_r_hA, A_r_0]
     * b. A_h > B_h, 直接返回A [A_l_hA, A_r_0]
     * AC: 17ms/53.67MB
     * @param: buildings
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2026/02/02 10:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> method_01(int[][] buildings) {
        int n = buildings.length;
        if (n == 0) {
            return new ArrayList<>();
        }
        return divideAndConquer(buildings, 0, n - 1);
    }

    private List<List<Integer>> divideAndConquer(int[][] buildings, int left, int right) {
        List<List<Integer>> ans = new ArrayList<>();
        if (left == right) {
            ans.add(Arrays.asList(buildings[left][0], buildings[left][2]));
            ans.add(Arrays.asList(buildings[left][1], 0));
            return ans;
        }
        int mid = left + (right - left) / 2;
        List<List<Integer>> leftList = divideAndConquer(buildings, left, mid);
        List<List<Integer>> rightList = divideAndConquer(buildings, mid + 1, right);
        // 开始进行合并操作
        int i = 0, j = 0; // i, j 指向 leftList, rightList 的索引
        int n = leftList.size(), m = rightList.size(); // n, m 分别为 leftList, rightList 的长度
        int leftPreHeight = 0, rightPreHeight = 0; // leftPreHeight, rightHeight 分别为 leftList, rightList 的高度
        while (i < n || j < m) {
            if (i >= n) {
                ans.add(rightList.get(j++));
            } else if (j >= m) {
                ans.add(leftList.get(i++));
            } else {
                int leftX = leftList.get(i).get(0);
                int rightX = rightList.get(j).get(0);
                int leftHeight = leftList.get(i).get(1);
                int rightHeight = rightList.get(j).get(1);
                if (leftX < rightX) {
                    // 处理 leftList
                    if (leftHeight > rightPreHeight) {
                        // 可以直接添加
                        ans.add(Arrays.asList(leftX, leftHeight));
                    } else if (leftPreHeight > rightPreHeight) {
                        //
                        ans.add(Arrays.asList(leftX, rightPreHeight));
                    }
                    leftPreHeight = leftHeight;
                    i++;
                } else if (leftX > rightX) {
                    // 处理 rightList
                    if (rightHeight > leftPreHeight) {
                        ans.add(Arrays.asList(rightX, rightHeight));
                    } else if (rightPreHeight > leftPreHeight) {
                        ans.add(Arrays.asList(rightX, leftPreHeight));
                    }
                    j++;
                    rightPreHeight = rightHeight;
                } else {
                    // 处理 leftX == rightX
                    if (leftHeight >= rightHeight && leftHeight != Math.max(leftPreHeight, rightPreHeight)) {
                        ans.add(Arrays.asList(leftX, leftHeight));
                    } else if (leftHeight < rightHeight && rightHeight != Math.max(leftPreHeight, rightPreHeight)){
                        ans.add(Arrays.asList(leftX, rightHeight));
                    }
                    leftPreHeight = leftHeight;
                    rightPreHeight = rightHeight;
                    i++;
                    j++;
                }
            }
        }
        return ans;
    }

}
