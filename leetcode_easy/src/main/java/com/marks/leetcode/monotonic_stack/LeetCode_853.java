package com.marks.leetcode.monotonic_stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/28 16:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_853 {
    /**
     * @Description: [
     * 在一条单行道上，有 n 辆车开往同一目的地。目的地是几英里以外的 target 。
     * 给定两个整数数组 position 和 speed ，长度都是 n ，其中 position[i] 是第 i 辆车的位置， speed[i] 是第 i 辆车的速度(单位是英里/小时)。
     *
     * 一辆车永远不会超过前面的另一辆车，但它可以追上去，并以较慢车的速度在另一辆车旁边行驶。
     * 车队 是指并排行驶的一辆或几辆汽车。车队的速度是车队中 最慢 的车的速度。
     * 即便一辆车在 target 才赶上了一个车队，它们仍然会被视作是同一个车队。
     *
     * 返回到达目的地的车队数量 。
     *
     * tips:
     * n == position.length == speed.length
     * 1 <= n <= 10^5
     * 0 < target <= 10^6
     * 0 <= position[i] < target
     * position 中每个值都 不同
     * 0 < speed[i] <= 10^6
     * ]
     * @param target
     * @param position
     * @param speed
     * @return int
     * @author marks
     * @CreateDate: 2024/11/28 16:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int carFleet(int target, int[] position, int[] speed) {
        int result;
//        result = method_01(target, position, speed);
        result = method_02(target, position, speed);
        return result;
    }

    /**
     * @Description: [排序
     * AC:78ms/57.04MB
     * ]
     * @param target
     * @param position
     * @param speed
     * @return int
     * @author marks
     * @CreateDate: 2024/11/29 9:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int target, int[] position, int[] speed) {
        int n = position.length;
        if (n == 1) {
            return n;
        }
        int[][] array = new int[n][2];
        for (int i = 0; i < n; i++) {
            array[i][0] = position[i];
            array[i][1] = speed[i];
        }
        Arrays.sort(array, (o1, o2) -> o2[0] - o1[0]);
        int ans = 1;
        for (int i = 1; i < n; i++) {
            double time1 = (target - array[i][0]) / ((double) array[i][1]);
            double time2 = (target - array[i - 1][0]) / ((double) array[i - 1][1]);
            if (time1 > time2) {
                // time2是一个新车队
                ans++;
            } else {
                // 组成一个车队, 需要将需要将车头的位置和速度赋值给当前车
                array[i][0] = array[i - 1][0];
                array[i][1] = array[i - 1][1];
            }
        }
        return ans;
    }

    /**
     * @Description: [
     * 输入：target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]
     * time:[1,1,12,7,3]
     * i = 0, 10 -> 入栈,
     * i = 1, 10 > 8
     * 单调栈
     *
     * AC:47ms/57.63MB
     * ]
     * @param target
     * @param position
     * @param speed
     * @return int
     * @author marks
     * @CreateDate: 2024/11/28 16:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int target, int[] position, int[] speed) {
        int n = position.length;
        if (n == 1) {
            return n;
        }
        double[] time = new double[target]; // 存储第 i 个位置出发的车辆到达 target 花费的时间
        for (int i = 0; i < n; i++) {
            time[position[i]] = (target - position[i]) / (double)(speed[i]);
        }

        Deque<Double> stack = new ArrayDeque<>();
        for (int i = 0; i < target; i++) {
            if (time[i] > 0) {
                while (!stack.isEmpty() && time[i] >= stack.peek()) {
                    stack.poll();
                }
                stack.push(time[i]);
            }
        }
        return stack.size();
    }
}
