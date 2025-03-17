package com.marks.leetcode.data_structure.union_find;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/17 11:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_947 {
    /**
     * @Description:
     * n 块石头放置在二维平面中的一些整数坐标点上。每个坐标点上最多只能有一块石头。
     * 如果一块石头的 同行或者同列 上有其他石头存在，那么就可以移除这块石头。
     *
     * 给你一个长度为 n 的数组 stones ，其中 stones[i] = [xi, yi] 表示第 i 块石头的位置，返回 可以移除的石子 的最大数量。
     * tips:
     * 1 <= stones.length <= 1000
     * 0 <= xi, yi <= 10^4
     * 不会有两块石头放在同一个坐标点上
     * @param stones
     * @return int
     * @author marks
     * @CreateDate: 2025/3/17 11:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int removeStones(int[][] stones) {
        int result;
//        result = method_01(stones);
        result = method_02(stones);
        return result;
    }

    /**
     * @Description:
     * 查看官解后, 使用并查集
     * stones = [[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]
     * 10001 -> 0, 10001 -> 1, 10002 -> 0, 10002 -> 2, 10003 -> 1, 10003 -> 2
     *
     * 先把并查集写一下吧
     * AC: 9ms/43.91MB
     * @param stones
     * @return int
     * @author marks
     * @CreateDate: 2025/3/17 14:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[][] stones) {
        UnionFind uf = new UnionFind();
        int n = 10001;
        for (int[] stone : stones) {
            uf.union(stone[0] + n, stone[1]);
        }
        return stones.length - uf.getCount();
    }

    private static class UnionFind {
        private Map<Integer, Integer> parent;
        private int count;

        public UnionFind() {
            parent = new HashMap<>();
            count = 0;
        }

        public Integer find(int x) {
            if (!parent.containsKey(x)) {
                parent.put(x, x);
                count++;
            }
            if (x != parent.get(x)) {
                parent.put(x, find(parent.get(x)));
            }
            return parent.get(x);
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX != rootY) {
                parent.put(rootX, rootY);
                count--;
            }
        }

        public Integer getCount() {
            return count;
        }
    }

    /**
     * @Description:
     * E1:
     * stones = [[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]
     * [x, x, 0]
     * [x, 0, x]
     * [0, x, x]
     *
     * 1. 经AI查找得知 二维坐标转为一维坐标 int x = i * n + j; (i, j为二维坐标点, n为 二维数组的列数, 即每一行有多少个元素)
     * 2. 将 x 转为二维数组坐标 int i = x / n, j = x % n;
     * 3. 由于数据的数值很大, 不适合用int[m * n] 来进行存储, 因此我们使用 Map<Integer, Integer> 来进行并查集的信息存储
     * 4. 将stones[i][j] 判断是否与 Map(存储节点关联) ? 这个存在一个问题, 如果每个节点都与Map中存在的节点比较, 那么复杂度就是 O(N ^ 2) N 为石头的数量
     *
     * 理解有问题, 这种会存在错误
     * E2: [[0,1],[1,0],[1,1]]
     * [1, 0] 无法被计算到 ans 中
     * @param stones 
     * @return int
     * @author marks
     * @CreateDate: 2025/3/17 11:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] stones) {
        List<Integer> lists = new ArrayList<>();
        int n = 10001;
        int ans = 0;
        for (int[] stone : stones) {
            int x = stone[0];
            int y = stone[1];
            for (Integer value : lists) {
                int pre_x = value / n;
                int pre_y = value % n;
                if (pre_x == x || pre_y == y) {
                    ans++;
                    break;
                }
            }
            lists.add(x * n + y);
        }

        return ans;
    }
}
