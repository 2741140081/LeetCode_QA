package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3531 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/11 11:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3531 {

    /**
     * @Description:
     * 给你一个正整数 n，表示一个 n x n 的城市，
     * 同时给定一个二维数组 buildings，其中 buildings[i] = [x, y] 表示位于坐标 [x, y] 的一个 唯一 建筑。
     * 如果一个建筑在四个方向（左、右、上、下）中每个方向上都至少存在一个建筑，则称该建筑 被覆盖 。
     * 返回 被覆盖 的建筑数量。
     * tips:
     * 2 <= n <= 10^5
     * 1 <= buildings.length <= 10^5
     * buildings[i] = [x, y]
     * 1 <= x, y <= n
     * buildings 中所有坐标均 唯一 。
     * @param: n
     * @param: buildings
     * @return int
     * @author marks
     * @CreateDate: 2025/12/11 11:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countCoveredBuildings(int n, int[][] buildings) {
        int result;
        result = method_01(n, buildings);
        return result;
    }

    /**
     * @Description:
     * 1. 构建一个status 二维数组, 默认值为0,(0:空地 1:建筑 2:已遍历的建筑)
     * 2. 遍历buildings, 构建status 二维数组, 填充1
     * 3. 再次遍历buildings, 填充2, 使用广度优先搜索算法, 填充2, 判断4个方向是否都为大于0
     * 4. 逻辑判断存在问题, 建筑被覆盖的判断是4个方向(不需要相邻)都为1, 才能被覆盖
     * 5. 需要修改思路, 使用前缀和 跟后缀和来判断, 这个时间复杂度是4n^2, 不行时间复杂度太高了
     * 6. List<Integer>[] rowLists, colLists 分别来存储每一行中的两个端点, 列中的两个端点(即最大值和最小值)
     * 7. 可以稍微修改一下, 使用int[n][2] 数组来存储行和列的端点, 初始值设置为 MAX_VALUE 和 MIN_VALUE
     * AC: 42ms/235.91MB
     * @param: n
     * @param: buildings
     * @return int
     * @author marks
     * @CreateDate: 2025/12/11 11:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] buildings) {
        int[][] rowLists = new int[n][2];
        int[][] colLists = new int[n][2];
        // 初始化
        for (int i = 0; i <= n; i++) {
            rowLists[i][0] = Integer.MAX_VALUE;
            rowLists[i][1] = Integer.MIN_VALUE;
            colLists[i][0] = Integer.MAX_VALUE;
            colLists[i][1] = Integer.MIN_VALUE;
        }
        for (int[] building : buildings) {
            int x = building[0];
            int y = building[1];
            rowLists[x][0] = Math.min(rowLists[x][0], y);
            rowLists[x][1] = Math.max(rowLists[x][1], y);
            colLists[y][0] = Math.min(colLists[y][0], x);
            colLists[y][1] = Math.max(colLists[y][1], x);
        }
        int ans = 0;
        // 再次遍历buildings, 判断当前点(x, y) 位于的rowList 和 colList 中是否满足条件,
        // rowList[x][0] < y < rowList[x][1] && colList[y][0] < x < colList[y][1] => 满足则 ans++;
        for (int[] building : buildings) {
            int x = building[0];
            int y = building[1];
            if (rowLists[x][0] < y && y < rowLists[x][1] && colLists[y][0] < x && x < colLists[y][1]) {
                ans++;
            }
        }
        return ans;
    }

}
