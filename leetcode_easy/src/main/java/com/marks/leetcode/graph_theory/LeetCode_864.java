package com.marks.leetcode.graph_theory;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_864 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/15 17:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_864 {

    /**
     * @Description:
     * 给定一个二维网格 grid ，其中：
     * '.' 代表一个空房间
     * '#' 代表一堵墙
     * '@' 是起点
     * 小写字母代表钥匙
     * 大写字母代表锁
     * 我们从起点开始出发，一次移动是指向四个基本方向之一行走一个单位空间。
     * 我们不能在网格外面行走，也无法穿过一堵墙。如果途经一个钥匙，我们就把它捡起来。
     * 除非我们手里有对应的钥匙，否则无法通过锁。
     *
     * 假设 k 为 钥匙/锁 的个数，且满足 1 <= k <= 6，
     * 字母表中的前 k 个字母在网格中都有自己对应的一个小写和一个大写字母。换言之，
     * 每个锁有唯一对应的钥匙，每个钥匙也有唯一对应的锁。另外，代表钥匙和锁的字母互为大小写并按字母顺序排列。
     *
     * 返回获取所有钥匙所需要的移动的最少次数。如果无法获取所有钥匙，返回 -1 。
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2025/12/15 17:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int shortestPathAllKeys(String[] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description:
     * 1. 判断从起始点出发, 判断起始点到每一个钥匙的可达性,假设有x1,x2...xn个钥匙,
     * 2. 通过广度优先搜索, 判断到钥匙的最短距离, 假设起始点位0, 0 到 x1 需要s1步, 0到x2需要x2步
     * x1 到 x2 需要Math.min(s1+s2, s1_2), 使用List<Integer>[] distance, 存储从0到x1, x2, x3...xn的最短距离
     * 3. 构建一个内部类, 存储步数以及携带的钥匙
     * need todo
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2025/12/15 17:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] grid) {
        final int INF = Integer.MAX_VALUE / 2;
        int m = grid.length;
        int n = grid[0].length();
        int count = 0; // 钥匙的个数
        char[][] matrix = new char[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = grid[i].charAt(j);
                if (Character.isLowerCase(matrix[i][j])) {
                    count++;
                }
            }
        }
        // 广度优先搜索, 存储节点直接可达的最小步数

        return 0;
    }

    class Pair {
        private int step = 0;
        private int[] keys = new int[26];

        public Pair() {
        }
    }

}
