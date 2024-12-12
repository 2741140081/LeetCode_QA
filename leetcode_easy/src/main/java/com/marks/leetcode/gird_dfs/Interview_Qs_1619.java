package com.marks.leetcode.gird_dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/9 10:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class Interview_Qs_1619 {
    /**
     * @Description: [
     * 你有一个用于表示一片土地的整数矩阵land，该矩阵中每个点的值代表对应地点的海拔高度。
     * 若值为0则表示水域。由垂直、水平或对角连接的水域为池塘。池塘的大小是指相连接的水域的个数。
     * 编写一个方法来计算矩阵中所有池塘的大小，返回值需要从小到大排序。
     *
     * tips:
     * 0 < len(land) <= 1000
     * 0 < len(land[i]) <= 1000
     * ]
     * @param land
     * @return int[]
     * @author marks
     * @CreateDate: 2024/12/9 10:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] pondSizes(int[][] land) {
        int[] result;
        result = method_01(land);
        return result;
    }

    /**
     * @Description: [
     * 关键点在于除了水平, 竖直方向, 还需要考虑对角线方向, 因此是8个方向的dfs
     * AC:10ms/73.74MB
     * ]
     * @param land
     * @return int[]
     * @author marks
     * @CreateDate: 2024/12/9 10:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[][] land) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < land.length; i++) {
            for (int j = 0; j < land[0].length; j++) {
                if (land[i][j] == 0) {
                    list.add(dfsMaxArea(land, i, j));
                }
            }
        }

        int[] ans = list.stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(ans);
        return ans;
    }

    /**
     * @Description: [
     * int count = 1 + dfsMaxArea(land, i + 1, j) + dfsMaxArea(land, i + 1, j + 1)
     *                         + dfsMaxArea(land, i, j + 1) + dfsMaxArea(land, i - 1, j + 1)
     *                         + dfsMaxArea(land, i - 1, j) + dfsMaxArea(land, i - 1, j - 1)
     *                         + dfsMaxArea(land, i, j - 1) + dfsMaxArea(land, i + 1, j - 1);
     *
     * 遍历8个方向可以写的更好看,
     * 这种8个有点难看, 看官解优化一下
     * 双重for, 好处是
     * 1. 不需要自己考虑那些遍历了, 那些没有遍历, for循环不会漏算和错算, 自己手写有概率出问题
     * 2. 更美观
     * ]
     * @param land
     * @param i
     * @param j
     * @return int
     * @author marks
     * @CreateDate: 2024/12/9 10:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int dfsMaxArea(int[][] land, int i, int j) {
        if (!inArea(land, i, j)) {
            return 0;
        } else {
            if (land[i][j] != 0) {
                return 0;
            } else {
                land[i][j] = -1; // 标记已经被遍历
                int count = 1;
                // 双重for循环遍历8个方向
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (dx == 0 && dy == 0) {
                            continue;
                        }
                        count += dfsMaxArea(land, i + dx, j + dy);
                    }
                }
                return count;
            }
        }
    }

    private boolean inArea(int[][] land, int i, int j) {
        if (i >= 0 && j >= 0 && i < land.length && j < land[0].length) {
            return true;
        }
        return false;
    }
}
