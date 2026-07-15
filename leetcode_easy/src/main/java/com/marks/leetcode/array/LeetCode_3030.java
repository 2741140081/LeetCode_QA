package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3030 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/14 15:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3030 {

    /**
     * @Description:
     * 给你一个下标从 0 开始、大小为 m x n 的网格 image ，表示一个灰度图像，
     * 其中 image[i][j] 表示在范围 [0..255] 内的某个像素强度。另给你一个 非负 整数 threshold 。
     * 如果 image[a][b] 和 image[c][d] 满足 |a - c| + |b - d| == 1 ，则称这两个像素是 相邻像素 。
     * 区域 是一个 3 x 3 的子网格，且满足区域中任意两个 相邻 像素之间，像素强度的 绝对差 小于或等于 threshold 。
     * 区域 内的所有像素都认为属于该区域，而一个像素 可以 属于 多个 区域。
     * 你需要计算一个下标从 0 开始、大小为 m x n 的网格 result ，
     * 其中 result[i][j] 是 image[i][j] 所属区域的 平均 强度，向下取整 到最接近的整数。
     * 如果 image[i][j] 属于多个区域，result[i][j] 是这些区域的 “取整后的平均强度” 的 平均值，也 向下取整 到最接近的整数。
     * 如果 image[i][j] 不属于任何区域，则 result[i][j] 等于 image[i][j] 。
     * 返回网格 result 。
     *
     * tips:
     * 3 <= n, m <= 500
     * 0 <= image[i][j] <= 255
     * 0 <= threshold <= 255
     * @param: image
     * @param: threshold
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/07/14 15:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] resultGrid(int[][] image, int threshold) {
        int[][] result;
        result = method_01(image, threshold);
        return result;
    }

    /**
     * @Description:
     * 1. 构建一个在左上角的初始区域 3 * 3 大小, 可以通过仅4个点来确定当前区域是否满足条件(相邻绝对差值小于等于 threshold).
     * 2. 分别记录左上角落(i, j) 和右下角坐标 (i + 2, j + 2), 分别得到 4 个边的中心的坐标:
     * (i + 1, j), (i, j + 1), (i + 2, j + 1), (i + 1, j + 2), 并且根据4个点坐标判断区域满足条件.
     * 3. 如果区域满足条件, 则遍历当前区域, 得到 avg 的平均值, 并且赋值给区域内的每一个点
     * 4. avg 对于区域内点的共享是 {avg, 1}, 即需要一个3维数组 int[m][n][2] sharedness,
     * 其中 sharedness[i][j] = {sum, cnt}, sum 为总数, cnt 为个数, avg 对于 i,j 的共享是 sum += avg, cnt += 1, 方便最后计算平均值
     * 5. 这种暴力解法感觉时间复杂度有点超了. 先想一想. 枚举所有的区域, 总计有 m * n 个区域, 判断区域和赋值区域是一个常数, 所以暴力的解法复杂度是O(m * n)
     * 6. 时间复杂度可以接受, 空间复杂度也是 O(m * n), 所以采用暴力的方式, 以区域的左上角坐标进行遍历, 范围是 0 ~ n - 3, 0 ~ m - 3
     * AC: 208ms/154.95MB
     * @param: image
     * @param: threshold
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/07/14 15:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_01(int[][] image, int threshold) {
        int m = image.length, n = image[0].length;
        // 创建一3维数组, 存储区域平均值和区域个数
        int[][][] sharedness = new int[m][n][2];
        // 遍历左上角坐标
        for (int i = 0; i < m - 2; i++) {
            for (int j = 0; j < n - 2; j++) {
                int res = getAvg(image, i, j, threshold);
                // 给区域内的所有坐标赋值
                if (res != -1) {
                    for (int x = i; x <= i + 2; x++) {
                        for (int y = j; y <= j + 2; y++) {
                            sharedness[x][y][0] += res;
                            sharedness[x][y][1] += 1;
                        }
                    }
                }
            }
        }
        int[][] ans = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (sharedness[i][j][1] != 0) {
                    ans[i][j] = sharedness[i][j][0] / sharedness[i][j][1];
                } else {
                    ans[i][j] = image[i][j];
                }
            }
        }

        return ans;
    }

    private int getAvg(int[][] image, int i, int j, int threshold) {
        int ans = 0;
        // 构建4条边上的中心点坐标
        int[][] center = {{1,0}, {0,1}, {1,2}, {2,1}};
        // 相邻坐标
        int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        for (int[] c : center) {
            int x = i + c[0], y = j + c[1];
            for (int[] d : dirs) {
                int dx = x + d[0], dy = y + d[1];
                // 判断 dx, dy, 是否在 (i, j) 和 (i + 2, j + 2) 的区间内
                if (dx >= i && dx <= i + 2 && dy >= j && dy <= j + 2) {
                    if (Math.abs(image[dx][dy] - image[x][y]) > threshold) {
                        // 不满足条件, 返回空数组
                        return -1;
                    }
                }
            }
        }
        // 获取区域平均值
        for (int x = i; x <= i + 2; x++) {
            for (int y = j; y <= j + 2; y++) {
                ans += image[x][y];
            }
        }

        return ans / 9; // 向下取整
    }

}
