package com.marks.leetcode.array;

import com.marks.utils.SparseTable2D;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3933 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/7 9:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3933 {

    /**
     * @Description:
     * 给你一个 n x m 的整数矩阵 matrix ，所有元素均为非负整数。
     * 一个 非零 单元格 (row, col) 会按如下方式检查其附近的单元格：
     * 令 x = matrix[row][col] 。
     * 考虑在 (row, col) 的 x 行和 x 列范围内的每个单元格。
     * 忽略矩阵外的单元格。
     * 忽略行距离和列距离都恰好等于 x 的 单元格。
     * 如果单元格 (row, col) 是 非零 的，并且所有考虑的单元格中没有一个值 大于 x ，那么该单元格就是一个 局部最大值 。
     * 返回一个整数，表示 matrix 中 局部最大值 的数量。
     * tips:
     * 1 <= n == matrix.length <= 200
     * 1 <= m == matrix[i].length <= 200
     * 0 <= matrix[i][j] <= 200
     * @param: matrix
     * @return int
     * @author marks
     * @CreateDate: 2026/07/07 9:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countLocalMaximums(int[][] matrix) {
        int result;
//        result = method_01(matrix);
        result = method_02(matrix);
        return result;
    }

    /**
     * @Description:
     * 1. 使用模板 ST 表来查询矩阵中的局部最大值
     * 2. 由于需要删除4个角落元素, 可以用行 - 2 * 列得到 子矩阵1的最大值, 列 - 2 * 行 得到子矩阵2的最大值, max = max(max1, max2);
     * 然后 max 与 x = matrix[row][col] 比较, 如果max > x 则该点不是一个局部最大值, 否则是局部最大值, ans++;
     * 3. 中间两个矩阵, 中心点位置 (i, j), x = matrix[i][j]; 4个角落的坐标分别是:
     * 左上角: (i - x, j - x); 右上角: (i - x, j + x); 左下角: (i + x, j - x); 右下角: (i + x, j + x);
     * 3.1 将左上角和左下角坐标右移一个单位, 得到 (i -x, j - x + 1) 和 (i + x, j - x + 1);
     * 将右上角 和右下角坐标左移一个单位, 得到 (i - x, j + x - 1) 和 (i + x, j + x - 1); 新得到的4个坐标构成一个矩形, 然后计算该矩形的最大值
     * 3.2 同理, 将左上角和右上角下移, 以及将左下角和右下角上移, 构成第二个矩形, 计算该矩形的最大值. 然后与 x 进行比较即可判断该点是否是局部最大值.
     * 4. 由于点的范围可能会发生越界情况, 所以需要判断坐标是否越界, 最小为0, 最大为 m 或者 n
     * 5. 在使用 ST 模板表时, 需要构建一个 第 0 行和 第 0 列为 0 的扩展矩阵, 大小为 (m + 1) * (n + 1), 完成扩展后,
     * 才能使用 ST 来查询矩阵中的局部最大值, 并且查询范围时 (1, 1) ~ (m, n)
     * AC: 319ms/223.3MB
     * @param: matrix
     * @return int
     * @author marks
     * @CreateDate: 2026/07/07 11:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;

        int[][] paddedMatrix = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                paddedMatrix[i][j] = matrix[i - 1][j - 1];
            }
        }

        SparseTable2D st2D = new SparseTable2D(paddedMatrix);

        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int x = matrix[i][j];

                if (x == 0) {
                    continue;
                }

                int row = i + 1, col = j + 1;
                // 构建左上角坐标和右下角坐标
                int leftTopX = row - x, leftTopY = col - x;
                int rightBottomX = row + x, rightBottomY = col + x;
                // 构建矩阵1, 将 leftTop 右移一个单元格, 将 rightBottom 左移一个单元格, 并且最大值和最小值 m/n 和 1
                int leftTopX1 = Math.max(1, leftTopX), leftTopY1 = Math.max(leftTopY + 1, 1);
                int rightBottomX1 = Math.min(rightBottomX, m), rightBottomY1 = Math.min(rightBottomY - 1, n);
                int max1 = st2D.queryMax(leftTopX1, leftTopY1, rightBottomX1, rightBottomY1);
                // 构建矩阵2, 将 leftTop 下移一个单元格, 将 rightBottom 上移一个单元格, 构建矩阵2, 最大值和最小值 m/n 和 1
                int leftTopX2 = Math.max(1, leftTopX + 1), leftTopY2 = Math.max(leftTopY, 1);
                int rightBottomX2 = Math.min(rightBottomX - 1, m), rightBottomY2 = Math.min(rightBottomY, n);
                int max2 = st2D.queryMax(leftTopX2, leftTopY2, rightBottomX2, rightBottomY2);
                int maxVal = Math.max(max1, max2);

                if (maxVal <= x) {
                    ans++;
                }
            }
        }

        return ans;
    }

    /**
     * @Description:
     * 1. x = matrix[row][col], 考虑 x 行 和 x 列, 这会构成一个正方形, 中心点是 (row, col), 并且需要删除4个角落的单元格,
     * 因为行距离和列距离都等于 x.
     * 2. 由于数据范围不是很大, 考虑遍历矩阵中每一个元素, 并且将非零元素添加到优先队列中, 优先队列为大根堆, 排序方式是 x = matrix[row][col] 降序排序
     * 3. 这是因为栈顶元素是整个矩阵中的最大元素, 那么其它元素必定是小于等于栈顶元素的, 所以栈顶元素是一个局部最大值.
     * 4. 将已经弹出的坐标放入一个 List<int[]> 中, 取栈顶元素, 与 List 进行比较, 从前往后比较, 判断 list.get(i) 与 pq.peek() 的关系:
     * 4.1 list.get(i) > pq.peek(), 即 x_i > x_peek, 判断 i 是否在 pq.peek 的范围内(不包含4个角落), 比较两者的坐标差值的绝对值不都为 x_peek,
     * 则证明在范围内, 则该点不是一个局部最大值, 添加该元素到 list 中; 如果不在 范围内, 则处理下一个 list 中的元素 i + 1, 直到 list(j) <= x_peek 结束
     * 4.2 list.get(i) <= pq.peek(), 直接结束, 该点是一个最大值, ans++;
     * 5. 点A = (a, b) B = (c, d), 并且 x = matrix[a][b], 判断 B 是否在 A的范围内 int subX = Math.abs(c - a); int subY = Math.abs(d - b);
     * if (subX == x && subY == x) || (subX > x || subY > x) {B不在 A 的范围内} else {B 在 A 的范围内}
     * 6. 超时, 通过的测试用例 694/706
     * 7. 问题应该在 List 上, 即 pq 与 list 中元素对比, 此时时间复杂度是 m^2 * n^2, 回归原始,
     * 直接遍历以 (i, j) 为中心点, x为半径, 判断点是否为局部最大值
     * AC: 359ms/57.63MB
     * @param: matrix
     * @return int
     * @author marks
     * @CreateDate: 2026/07/07 9:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        // 创建优先队列, 大根堆 {x, i, j}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        // 创建 List<int[]> list; {i, j}
        List<int[]> list = new ArrayList<>();
        // 遍历矩阵, 添加非零元素到 pq 中
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] != 0) {
                    pq.offer(new int[]{matrix[i][j], i, j});
                }
            }
        }
        int ans = 0;
        while (!pq.isEmpty()) {
            // 遍历优先队列, 判断栈顶元素与 list 中元素关系
            int[] curr = pq.poll();
            int x = curr[0], row = curr[1], col = curr[2];

            boolean isLocalMax = true;
            int size = list.size();

            if (size <= (2 * x + 1) * (2 * x + 1)) {
                for (int[] prev : list) {
                    int prevX = prev[0], prevRow = prev[1], prevCol = prev[2];

                    if (prevX <= x) {
                        break;
                    }

                    int subRow = Math.abs(row - prevRow);
                    int subCol = Math.abs(col - prevCol);

                    if ((subRow <= x && subCol <= x) && (subRow != x || subCol != x)) {
                        isLocalMax = false;
                        break;
                    }
                }
            } else {
                int startRow = Math.max(0, row - x);
                int endRow = Math.min(m - 1, row + x);
                int startCol = Math.max(0, col - x);
                int endCol = Math.min(n - 1, col + x);

                for (int i = startRow; i <= endRow; i++) {
                    for (int j = startCol; j <= endCol; j++) {
                        if (i == row && j == col) {
                            continue;
                        }

                        int subRow = Math.abs(i - row);
                        int subCol = Math.abs(j - col);
                        if (subRow == x && subCol == x) {
                            continue;
                        }

                        if (matrix[i][j] > x) {
                            isLocalMax = false;
                            break;
                        }
                    }
                    if (!isLocalMax) {
                        break;
                    }
                }
            }

            if (isLocalMax) {
                ans++;
            }

            list.add(curr);
        }
        return ans;
    }

}
