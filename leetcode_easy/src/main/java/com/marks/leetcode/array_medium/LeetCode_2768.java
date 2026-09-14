package com.marks.leetcode.array_medium;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2768 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/11 10:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2768 {

    /**
     * @Description:
     * 给你两个整数 m 和 n ，表示一个下标从 0 开始的 m x n 的网格图。
     * 给你一个下标从 0 开始的二维整数矩阵 coordinates ，
     * 其中 coordinates[i] = [x, y] 表示坐标为 [x, y] 的格子是 黑色的 ，所有没出现在 coordinates 中的格子都是 白色的。
     * 一个块定义为网格图中 2 x 2 的一个子矩阵。
     * 更正式的，对于左上角格子为 [x, y] 的块，其中 0 <= x < m - 1 且 0 <= y < n - 1 ，包含坐标为 [x, y] ，[x + 1, y] ，[x, y + 1] 和 [x + 1, y + 1] 的格子。
     * 请你返回一个下标从 0 开始长度为 5 的整数数组 arr ，arr[i] 表示恰好包含 i 个 黑色 格子的块的数目。
     *
     * tips:
     * 2 <= m <= 10^5
     * 2 <= n <= 10^5
     * 0 <= coordinates.length <= 10^4
     * coordinates[i].length == 2
     * 0 <= coordinates[i][0] < m
     * 0 <= coordinates[i][1] < n
     * coordinates 中的坐标对两两互不相同。
     * @param: m
     * @param: n
     * @param: coordinates
     * @return long[]
     * @author marks
     * @CreateDate: 2026/09/11 10:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long[] countBlackBlocks(int m, int n, int[][] coordinates) {
        long[] result;
        result = method_01(m, n, coordinates);
        return result;
    }

    /**
     * @Description:
     * 1. 直接遍历然后统计, 由于m * n = 10^10, 所以无法直接构建二维数组, 也无法直接遍历 m * n 来进行统计
     * 2. 需要通过遍历 coordinates , 通过贡献法来统计每个块的黑色格子数, 总计有 m - 1 * n - 1 个合法的块
     * 3. 假设 c[i] = [x, y], 即点(x, y) 是一个黑色格子, 那么它只会对4个块产生贡献, 即本身块 (x, y), 同一行的块(x, y - 1)
     * 上一行: (x - 1, y), 对角: (x - 1, y - 1)
     * 4. 需要判断已上述坐标为左上角是否合法, x - 1 >= 0, y - 1 >= 0, x < m - 1, y < n - 1
     * 5. 由于空间复杂度过高, 所以需要对 coordinates 进行排序, 即先处理同一行, 然后在处理下一行
     * 6. 由于点只会影响当前行和上一行, 所以可以使用滚动数组来记录当前行和上一行, 即使用两个数组来记录格子的贡献值
     * 7. 还是将二维坐标转换为一维坐标来处理
     * AC: 117ms/70.58MB
     * @param: m
     * @param: n
     * @param: coordinates
     * @return long[]
     * @author marks
     * @CreateDate: 2026/09/11 10:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long[] method_01(int m, int n, int[][] coordinates) {
        Map<Long, Integer> ans = new HashMap<>();
        long OFFSET = 100001L;
        // 遍历 coordinates
        int[][] dirs = {{0, 0}, {0, -1}, {-1, 0}, {-1, -1}};
        for (int[] coordinate : coordinates) {
            int x = coordinate[0];
            int y = coordinate[1];
            for (int[] dir : dirs) {
                int nx = x + dir[0];
                int ny = y + dir[1];
                if (nx >= 0 && ny >= 0 && nx < m && ny < n) {
                    // 构建唯一key
                    long key = ((long) nx) * OFFSET + ny;
                    ans.merge(key, 1, Integer::sum);
                }
            }
        }
        long[] res = new long[5]; // 先处理 1 ~ 4
        // 遍历 map
        for (int count : ans.values()) {
            res[count] += 1;
        }
        long sum = Arrays.stream(res).sum();
        // 总计有 (m - 1) * (n - 1) 个合法块
        res[0] = (long) (m - 1) * (n - 1) - sum;
        return res;
    }

}
