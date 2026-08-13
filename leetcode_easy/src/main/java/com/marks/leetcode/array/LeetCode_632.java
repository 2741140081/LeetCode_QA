package com.marks.leetcode.array;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_632 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/24 10:47
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_632 {

    /**
     * @Description:
     * 你有 k 个 非递减排列 的整数列表。找到一个 最小 区间，使得 k 个列表中的每个列表至少有一个数包含在其中。
     * 我们定义如果 b-a < d-c 或者在 b-a == d-c 时 a < c，则区间 [a,b] 比 [c,d] 小。
     *
     * tips:
     * nums.length == k
     * 1 <= k <= 3500
     * 1 <= nums[i].length <= 50
     * -10^5 <= nums[i][j] <= 10^5
     * nums[i] 按非递减顺序排列
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2026/07/24 10:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] smallestRange(List<List<Integer>> nums) {
        int[] result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 规则要求, 先按照区间大小, 区间范围更小的优先, 如果区间范围一致, 则更小的区间 a 更优.
     * 2. int target = nums[0][j], 然后就可以将 target 看着是左侧起始点或者右侧的终点, 得到两个结果集合
     * [left, target] 和 [target, right], 使得每一行都有一个数在范围内
     * 3. 并且 left 和 right 需要分别求, 然后就可以得到区间范围 int len = Math.min(target - left + 1, right - target + 1)
     * 4. 不应该单纯使用第一行的数据作为终点, 而是将所有数据提取出来, 这个就会超时,
     * 5. 查看题解, 使用贪心 + 优先队列处理, 优先队列中存储每列的下标值, 并且比较时, 使用 next[] 数组存储下一个坐标值
     * AC: 315ms/84.65MB
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2026/07/24 10:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(List<List<Integer>> nums) {
        // 初始化区间左右边界，初始右边界设为最大整数值
        int rangeLeft = 0, rangeRight = Integer.MAX_VALUE;
        // 初始化最小区间范围，初始值为最大整数值
        int minRange = rangeRight - rangeLeft;
        // 初始化最大值变量，用于记录当前遍历中的最大值
        int max = Integer.MIN_VALUE;
        // 获取列表的大小
        int size = nums.size();
        // 创建一个数组next，用于记录每个列表当前遍历到的位置
        int[] next = new int[size];
        // 创建一个优先队列，用于按照当前值的大小进行排序
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>(new Comparator<Integer>() {
            public int compare(Integer index1, Integer index2) {
                // 比较两个列表中当前指针位置的值
                return nums.get(index1).get(next[index1]) - nums.get(index2).get(next[index2]);
            }
        });
        // 初始化优先队列，将每个列表的索引加入队列，并更新最大值
        for (int i = 0; i < size; i++) {
            priorityQueue.offer(i);
            // 更新当前遍历中的最大值
            max = Math.max(max, nums.get(i).get(0));
        }
        // 开始循环，寻找最小覆盖区间
        while (true) {
            // 从优先队列中取出当前值最小的索引
            int minIndex = priorityQueue.poll();
            // 计算当前区间范围
            int curRange = max - nums.get(minIndex).get(next[minIndex]);
            // 如果当前区间范围小于最小区间范围，则更新最小区间范围和左右边界
            if (curRange < minRange) {
                minRange = curRange;
                rangeLeft = nums.get(minIndex).get(next[minIndex]);
                rangeRight = max;
            }
            // 移动当前列表的指针到下一个位置
            next[minIndex]++;
            // 如果当前列表已经遍历完，则退出循环
            if (next[minIndex] == nums.get(minIndex).size()) {
                break;
            }
            // 将当前列表的索引重新加入优先队列
            priorityQueue.offer(minIndex);
            // 更新当前遍历中的最大值
            max = Math.max(max, nums.get(minIndex).get(next[minIndex]));
        }
        // 返回找到的最小覆盖区间
        return new int[]{rangeLeft, rangeRight};
    }

}
