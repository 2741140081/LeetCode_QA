package com.marks.leetcode.graph_theory;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1298 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/29 17:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1298 {

    /**
     * @Description:
     * 给你 n 个盒子，每个盒子的格式为 [status, candies, keys, containedBoxes] ，其中：
     *
     * 状态字 status[i]：整数，如果 box[i] 是开的，那么是 1 ，否则是 0 。
     * 糖果数 candies[i]: 整数，表示 box[i] 中糖果的数目。
     * 钥匙 keys[i]：数组，表示你打开 box[i] 后，可以得到一些盒子的钥匙，每个元素分别为该钥匙对应盒子的下标。
     * 内含的盒子 containedBoxes[i]：整数，表示放在 box[i] 里的盒子所对应的下标。
     * 给你一个整数数组 initialBoxes，包含你最初拥有的盒子。你可以拿走每个 已打开盒子 里的所有糖果，并且可以使用其中的钥匙去开启新的盒子，并且可以使用在其中发现的其他盒子。
     *
     * 请你按照上述规则，返回可以获得糖果的 最大数目 。
     * @param: status
     * @param: candies
     * @param: keys
     * @param: containedBoxes
     * @param: initialBoxes
     * @return int
     * @author marks
     * @CreateDate: 2025/12/29 17:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxCandies(int[] status, int[] candies, int[][] keys, int[][] containedBoxes, int[] initialBoxes) {
        int result;
        result = method_01(status, candies, keys, containedBoxes, initialBoxes);
        return result;
    }

    /**
     * @Description:
     * 1. 广度优先搜索, 获取所有可打开的盒子, 并且需要一个队列存储持有的盒子(可能时锁住也可能开着)
     * 2. 然后在里面在建一个 待打开的盒子
     * AC: 9ms/66.27MB
     * @param: status
     * @param: candies
     * @param: keys
     * @param: containedBoxes
     * @param: initialBoxes
     * @return int
     * @author marks
     * @CreateDate: 2025/12/29 17:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] status, int[] candies, int[][] keys, int[][] containedBoxes, int[] initialBoxes) {
        int n = status.length;
        // 创建一个队列, 存储已持有的盒子
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < initialBoxes.length; i++) {
            queue.offer(initialBoxes[i]);
        }
        boolean[] visited = new boolean[n]; // 创建一个数组, 存储已访问的盒子
        int ans = 0;
        // 创建一个队列, 存储待打开的盒子(status[i] == 1)
        Queue<Integer> openBoxes = new LinkedList<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int box = queue.poll();
                if (status[box] == 1 && !visited[box]) {
                    openBoxes.offer(box);
                    visited[box] = true;
                } else if (status[box] == 0) {
                    queue.offer(box); // 添加锁住的盒子, 从新入队
                }
            }
            if (openBoxes.isEmpty()) {
                // 跳出循环
                break;
            } else {
                while (!openBoxes.isEmpty()) {
                    int box = openBoxes.poll();
                    // 添加糖果
                    ans += candies[box];
                    // 添加钥匙
                    for (int key : keys[box]) {
                        status[key] = 1;
                    }
                    // 添加盒子
                    for (int containedBox : containedBoxes[box]) {
                        queue.offer(containedBox);
                    }
                }
            }
        }
        return ans;
    }

}
