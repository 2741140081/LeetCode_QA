package com.marks.leetcode.DP;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3661 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/7 10:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3661 {

    /**
     * @Description:
     * 一条无限长的直线上分布着一些机器人和墙壁。给你整数数组 robots ，distance 和 walls：
     * robots[i] 是第 i 个机器人的位置。
     * distance[i] 是第 i 个机器人的子弹可以行进的 最大 距离。
     * walls[j] 是第 j 堵墙的位置。
     * 每个机器人有 一颗 子弹，可以向左或向右发射，最远距离为 distance[i] 米。
     * 子弹会摧毁其射程内路径上的每一堵墙。机器人是固定的障碍物：如果子弹在到达墙壁前击中另一个机器人，它会 立即 在该机器人处停止，无法继续前进。
     * 返回机器人可以摧毁墙壁的 最大 数量。
     * 注意：
     * 墙壁和机器人可能在同一位置；该位置的墙壁可以被该位置的机器人摧毁。
     * 机器人不会被子弹摧毁。
     *
     * tips:
     * 1 <= robots.length == distance.length <= 10^5
     * 1 <= walls.length <= 10^5
     * 1 <= robots[i], walls[j] <= 10^9
     * 1 <= distance[i] <= 10^5
     * robots 中的所有值都是 互不相同 的
     * walls 中的所有值都是 互不相同 的
     * @param: robots
     * @param: distance
     * @param: walls
     * @return int
     * @author marks
     * @CreateDate: 2026/04/07 10:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxWalls(int[] robots, int[] distance, int[] walls) {
        int result;
        result = method_01(robots, distance, walls);
        result = method_02(robots, distance, walls);
        return result;
    }

    /**
     * @Description:
     * need todo: 当前只是查看官方题解, 需要自行理解, 并且将method_01方法修改正确
     * 1.
     * @param: robots
     * @param: distance
     * @param: walls
     * @return int
     * @author marks
     * @CreateDate: 2026/04/07 16:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] robots, int[] distance, int[] walls) {
        int n = robots.length;
        int[] left = new int[n];
        int[] right = new int[n];
        int[] num = new int[n];
        Map<Integer, Integer> robotsToDistance = new HashMap<>();

        for (int i = 0; i < n; i++) {
            robotsToDistance.put(robots[i], distance[i]);
        }

        Arrays.sort(robots);
        Arrays.sort(walls);

        for (int i = 0; i < n; i++) {
            int pos1 = upperBound(walls, robots[i]);

            int leftPos;
            if (i >= 1) {
                int leftBound = Math.max(robots[i] - robotsToDistance.get(robots[i]), robots[i - 1] + 1);
                leftPos = lowerBound(walls, leftBound);
            } else {
                leftPos = lowerBound(walls, robots[i] - robotsToDistance.get(robots[i]));
            }
            left[i] = pos1 - leftPos;

            int rightPos;
            if (i < n - 1) {
                int rightBound = Math.min(robots[i] + robotsToDistance.get(robots[i]), robots[i + 1] - 1);
                rightPos = upperBound(walls, rightBound);
            } else {
                rightPos = upperBound(walls, robots[i] + robotsToDistance.get(robots[i]));
            }
            int pos2 = lowerBound(walls, robots[i]);
            right[i] = rightPos - pos2;

            if (i == 0) {
                continue;
            }
            int pos3 = lowerBound(walls, robots[i - 1]);
            num[i] = pos1 - pos3;
        }

        int subLeft = left[0], subRight = right[0];
        for (int i = 1; i < n; i++) {
            int currentLeft = Math.max(subLeft + left[i], subRight - right[i - 1] + Math.min(left[i] + right[i - 1], num[i]));
            int currentRight = Math.max(subLeft + right[i], subRight + right[i]);
            subLeft = currentLeft;
            subRight = currentRight;
        }

        return Math.max(subLeft, subRight);
    }

    private int lowerBound(int[] arr, int target) {
        int left = 0, right = arr.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private int upperBound(int[] arr, int target) {
        int left = 0, right = arr.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    /**
     * @Description:
     * 1. 动态规划
     * 2. int[][] dp = new int[n][2]; dp[i][0] 表示前 i 个机器人并且第 i 个机器人向左射击能摧毁的墙最大数目; dp[i][1] 表示前 i 个机器人并且第 i 个机器人向右射击能摧毁的墙最大数目;
     * 3. 对 walls 进行特殊处理, 例如使用前缀和进行处理. 当我们计算第i个机器人能摧毁墙的数量时, 已知参数, int prevRobot = robots[i - 1]; int dist = distance[i];
     * 那么可以判断子弹能否到达的最远距离是, 并且得到子弹最远距离的下标值, int endIndex = i - dist + 1; 判断 endIndex 与 prevRobot, 取两者的较大值最为终点 int end = Math.max(prevRobot, endIndex);
     * 4. 计算摧毁的墙数目: 前缀和 int count = prevSuffix[i] - prevSuffix[end]; 得到状态转移方程 dp[i][0] = count + Math.max(dp[i - 1][0], dp[i - 1][1]); 并且同理可得向右射击的结果.
     * 5. 由于 robots[] 和 walls[] 不是一个有序的数组, 所以需要进行排序, 并且还得保留 distance[] 的索引关系, 所以使用二维的数组进行存储 robotInfo[][]
     * 6. 好像不能使用前缀和, 由于walls[i] 的数据范围是 10^9, 这回导致浪费大量的空间, 由于 walls[] 排序后是一个有序数组, 所以使用二分查找法来替代前缀和处理, 这样不会导致空间复杂度过高.
     * 7. 发现问题, 有的墙被摧毁了多次, 导致最终结果会有重复, 所以需要去重, 例如 i - 1 向右射击, i 向左射击, 两者的射击可能存在重复区域, 并且重复区域如果有墙的存在, 则导致同一面墙被摧毁两次, 这样得到的结果就会发生错误.
     * 8. 添加一个 int prevMaxDist 记录上一次向右射击的最远下标位置
     * 9. 需要处理下标无法在 walls[] 中找到的情况
     * 10. 看看官方题解
     * @param: robots
     * @param: distance
     * @param: walls
     * @return int
     * @author marks
     * @CreateDate: 2026/04/07 10:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] robots, int[] distance, int[] walls) {
        int n = robots.length;
        int[][] robotInfo = new int[n][2];
        for (int i = 0; i < n; i++) {
            robotInfo[i][0] = robots[i];
            robotInfo[i][1] = distance[i];
        }
        // 排序, 根据 robots[], 升序排序
        Arrays.sort(robotInfo, (a, b) -> a[0] - b[0]);
        // 在对walls[] 也升序排序
        Arrays.sort(walls);
        // 创建dp数组
        int[][] dp = new int[n][2];
        // 向左射击, 处理dp[0][0], 下标是 robotInfo[0][0], 向左射击的最远下标是 Math.max(robotInfo[0][0] - robotInfo[0][1], 1), 因为 1 <= robots[i], walls[j]
        int leftIndex = Math.max(robotInfo[0][0] - robotInfo[0][1], 1);
        // 通过二分查找, 找到在 walls[] 中的下标
        int left = binarySearchLeft(walls, leftIndex);
        // 在找当前机器人坐标在walls[] 中的下标
        int robotIndex = robotInfo[0][0];
        // 找到小于等于 robotIndex 的最大值
        int currMin = binarySearchRight(walls, robotIndex);
        // 处理 left = -1 的情况
        dp[0][0] = (currMin == -1 || left == -1) ? 0 : currMin - left + 1; // 计算摧毁的墙数目
        // 向左射击 end

        // 向右射击, 处理 dp[0][1], 判断向右射击的结果, 到达最远下标或者在下一个机器人处停止, 这里 1 也有可能越界, 所以要判断
        int maxDist = 1 == n ? walls[walls.length - 1] : robotInfo[1][0]; // 如果没有下一个机器人, 则取墙的最大值
        int rightIndex = Math.min(robotInfo[0][0] + robotInfo[0][1], maxDist);
        // 二分法查找 walls[] 中的下标, right = -1 时, 表示无法摧毁任意一堵墙
        int right = binarySearchRight(walls, rightIndex);
        int prevMaxIndex = right; // 上一次向右射击的最远下标位置
        // 找到当前机器人的坐标 walls[] 中的下标
        int currMax = binarySearchLeft(walls, robotIndex);
        dp[0][1] = (right == -1 || currMax == -1) ? 0 : right - currMax + 1;
        // 进行动态规划
        for (int i = 1; i < n; i++) {
            int prevRobot = robotInfo[i - 1][0] + 1; // 因为 robotInfo[i - 1] 处的墙必定会被前一个机器人给摧毁
            int dist = robotInfo[i][1];
            int endIndex = robotInfo[i][0] - dist;
            int end = Math.max(prevRobot, endIndex);
            // 使用二分法获取 walls[] 的下标
            left = binarySearchLeft(walls, end);
            // 查找当前机器人的坐标 walls[] 中的下标, 向左射击时, 可能会重复摧毁墙, 需要判断是否存在重复区域
            currMin = binarySearchRight(walls, robotInfo[i][0]);
            // 特殊处理向左射击, 如果上一次是向左射击, 则不影响当前射击结果
            dp[i][0] = ((currMin == -1 || left == -1) ? 0 : currMin - left + 1) + dp[i - 1][0];
            // 如果上一次是向右射击, 判断是否有重复区域.
            left = Math.max(prevMaxIndex + 1, left);
            // 更新dp[i][0]
            dp[i][0] = Math.max(dp[i][0], ((currMin == -1 || left == -1) ? 0 : currMin - left + 1) + dp[i - 1][1]);
            // 向左射击end

            // 需要处理越界 i + 1 的情况, 最远是达到最后一堵墙的位置, 否则是下一个机器人的位置
            maxDist = i + 1 >= n ? walls[walls.length - 1] : robotInfo[i][0];
            rightIndex = Math.min(robotInfo[i][0] + robotInfo[i][1], maxDist);
            right = binarySearchRight(walls, rightIndex);
            currMax = binarySearchLeft(walls, robotInfo[i][0]);
            // 处理 currMax 没有找到的情况
            dp[i][1] = ((right == -1 || currMax == -1) ? 0 : right - currMax + 1) + Math.max(dp[i - 1][0], dp[i - 1][1]);
            // 更新 prevMaxIndex
            prevMaxIndex = Math.max(prevMaxIndex, right);
        }

        return Math.max(dp[n - 1][0], dp[n - 1][1]);
    }

    /**
     * @Description:
     * 向左射击, 找到下标大于 leftIndex 的 walls[] 的最小值
     * 1. 如果 walls[n - 1] < leftIndex, 这表示无法找到, 返回 n
     * @param: walls
     * @param: leftIndex
     * @return int
     * @author marks
     * @CreateDate: 2026/04/07 10:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int binarySearchLeft(int[] walls, int leftIndex) {
        int n = walls.length;
        int left = 0, right = n - 1;
        int result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (walls[mid] >= leftIndex) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return result;
    }

    /**
     * @Description:
     * 向右射击, 找到下标小于 rightIndex的 walls[] 的最大值
     * 1. 如果 walls[0] > rightIndex, 这表示无法找到, 返回 -1
     * @param: walls
     * @param: leftIndex
     * @return int
     * @author marks
     * @CreateDate: 2026/04/07 10:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int binarySearchRight(int[] walls, int rightIndex) {
        int n = walls.length;
        int left = 0, right = n - 1;
        int result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (walls[mid] <= rightIndex) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

}
