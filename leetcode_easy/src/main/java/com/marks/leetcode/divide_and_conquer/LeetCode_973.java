package com.marks.leetcode.divide_and_conquer;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_973 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/28 10:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_973 {

    /**
     * @Description:
     * 给定一个数组 points ，其中 points[i] = [xi, yi] 表示 X-Y 平面上的一个点，并且是一个整数 k ，返回离原点 (0,0) 最近的 k 个点。
     * 这里，平面上两点之间的距离是 欧几里德距离（ √(x1 - x2)2 + (y1 - y2)2 ）。
     * 你可以按 任何顺序 返回答案。除了点坐标的顺序之外，答案 确保 是 唯一 的。
     * tips:
     * 1 <= k <= points.length <= 10^4
     * -10^4 < xi, yi < 10^4
     * @param: points
     * @param: k
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/01/28 10:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] kClosest(int[][] points, int k) {
        int[][] result;
        result = method_01(points, k);
        result = method_02(points, k);
        return result;
    }

    /**
     * @Description:
     * 1. 使用基于分治法的归并排序
     * AC: 68ms/59.14MB
     * @param: points
     * @param: k
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/01/28 10:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_02(int[][] points, int k) {
        int n = points.length;
        // 将 points 数组转换成 arr = {dist, i} 然后根据 dist 进行归并排序
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i] = new int[]{points[i][0] * points[i][0] + points[i][1] * points[i][1], i};
        }
        // 开始归并排序
        mergeSort(arr, 0, n - 1);
        int[][] ans = new int[k][2]; // 取前k个元素
        for (int i = 0; i < k; i++) {
            int index = arr[i][1];
            ans[i] = new int[]{points[index][0], points[index][1]};
        }
        return ans;
    }

    private void mergeSort(int[][] arr, int left, int right) {
        // 判断 left right 是否有效
        if (left >= right) {
            return;
        }
        int mid = (right - left) / 2 + left;
        // 归并左侧
        mergeSort(arr, left, mid);
        // 归并右侧
        mergeSort(arr, mid + 1, right);
        // 归并
        merge(arr, left, mid, right);
    }

    private void merge(int[][] arr, int left, int mid, int right) {
        int len = right - left + 1;
        int[][] temp = new int[len][2];
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) {
            if (arr[i][0] < arr[j][0]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        // 左侧剩余元素
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        // 右侧剩余元素
        while (j <= right) {
            temp[k++] = arr[j++];
        }
        // 将 temp 赋给 arr
        System.arraycopy(temp, 0, arr, left, len);
    }

    /**
     * @Description:
     * 1. 直接将坐标点转换成欧几里得距离(不要开根号), 用int 可以存储 < 10^9 的数
     * 2. 使用优先队列存储, int[] arr = {dist, i} dist = (x1^2 + y1^2), i 表示坐标点在 points 中的索引
     * AC: 31ms/53.76MB
     * @param: points
     * @param: k
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/01/28 10:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_01(int[][] points, int k) {
        int n = points.length;
        // 构建优先队列
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0])); // 升序排序(小根堆)
        for (int i = 0; i < n; i++) {
            int dist = points[i][0] * points[i][0] + points[i][1] * points[i][1];
            pq.offer(new int[]{dist, i});
        }
        int[][] ans = new int[k][2];
        for (int i = 0; i < k; i++) {
            int[] cur = pq.poll();
            ans[i] = new int[]{points[cur[1]][0], points[cur[1]][1]};
        }
        return ans;
    }

}
