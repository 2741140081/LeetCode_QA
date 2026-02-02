package com.marks.leetcode.data_structure.stack;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2751 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/29 17:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2751 {

    /**
     * @Description:
     * 现有 n 个机器人，编号从 1 开始，每个机器人包含在路线上的位置、健康度和移动方向。
     * 给你下标从 0 开始的两个整数数组 positions、healths 和一个字符串 directions（directions[i] 为 'L' 表示 向左 或 'R' 表示 向右）。 positions 中的所有整数 互不相同 。
     * 所有机器人以 相同速度 同时 沿给定方向在路线上移动。如果两个机器人移动到相同位置，则会发生 碰撞 。
     * 如果两个机器人发生碰撞，则将 健康度较低 的机器人从路线中 移除 ，并且另一个机器人的健康度 减少 1 。幸存下来的机器人将会继续沿着与之前 相同 的方向前进。如果两个机器人的健康度相同，则将二者都从路线中移除。
     * 请你确定全部碰撞后幸存下的所有机器人的 健康度 ，并按照原来机器人编号的顺序排列。即机器人 1 （如果幸存）的最终健康度，机器人 2 （如果幸存）的最终健康度等。 如果不存在幸存的机器人，则返回空数组。
     * 在不再发生任何碰撞后，请你以数组形式，返回所有剩余机器人的健康度（按机器人输入中的编号顺序）。
     * 注意：位置  positions 可能是乱序的。
     * @param: positions
     * @param: healths
     * @param: directions
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/01/29 17:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> survivedRobotsHealths(int[] positions, int[] healths, String directions) {
        List<Integer> result;
        result = method_01(positions, healths, directions);
        return result;
    }

    /**
     * @Description: 
     * 1. 栈顶元素是向右移动的,
     * AC: 56ms/125.67MB
     * @param: positions
     * @param: healths
     * @param: directions
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/01/29 17:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int[] positions, int[] healths, String directions) {
        int n = positions.length;
        // 构建栈
        Deque<int[]> stack = new ArrayDeque<>(); // { 剩余健康值, 索引}
        // 对 positions 升序排序, 但是又需要管理索引, 算了不会间接排序, 直接存储吧
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0] = positions[i];
            arr[i][1] = i;
        }
        Arrays.sort(arr, Comparator.comparingInt(a -> a[0])); // 升序
        for (int i = 0; i < n; i++) { 
            boolean isAlive = true; // 判断当前机器人是否存活
            int index = arr[i][1];
            while (isAlive && directions.charAt(index) == 'L'
                    && !stack.isEmpty() && directions.charAt(stack.peek()[1]) == 'R') {
                // 判断 isAlive
                isAlive = stack.peek()[0] < healths[index]; // 当前机器人是否还在
                if (stack.peek()[0] <= healths[index]) {
                    stack.poll();
                    healths[index]--; // 减少健康值
                } else {
                    stack.peek()[0]--; // 减少栈顶的元素健康值
                }
            }
            if (isAlive) {
                stack.push(new int[]{healths[index], index});
            }
        }
        List<Integer> ans = new ArrayList<>();
        int[][] temp = new int[stack.size()][2]; // 还要再次排序, 按照编号升序
        int i = 0;
        while (!stack.isEmpty()) {
            temp[i++] = stack.poll();
        }
        // 对temp 排序
        Arrays.sort(temp, Comparator.comparingInt(a -> a[1]));
        for (int j = 0; j < temp.length; j++) {
            ans.add(temp[j][0]);
        }

        return ans;
    }

}
