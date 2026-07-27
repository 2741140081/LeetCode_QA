package com.marks.leetcode.array;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_719 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/23 16:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_719 {

    /**
     * @Description:
     * 数对 (a,b) 由整数 a 和 b 组成，其数对距离定义为 a 和 b 的绝对差值。
     * 给你一个整数数组 nums 和一个整数 k ，数对由 nums[i] 和 nums[j] 组成且满足 0 <= i < j < nums.length 。
     * 返回 所有数对距离中 第 k 小的数对距离。
     *
     * tips:
     * n == nums.length
     * 2 <= n <= 10^4
     * 0 <= nums[i] <= 10^6
     * 1 <= k <= n * (n - 1) / 2
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/07/23 16:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int smallestDistancePair(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        result = method_02(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1. 查看官方题解, 使用二分法查找第 k 小的绝对值 mid, 并且通过二分法查找目标值 target = nums[j] - mid
     * 2. 统计在绝对值小于 mid 的个数 cnt, 然后根据 cnt 与 k 的关系, 进行二分法处理左右边界
     * AC: 23ms/46.08MB
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/07/24 10:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums, int k) {
        Arrays.sort(nums);
        // 最小绝对值是0, 最大绝对值是 nums[n - 1] - nums[0]
        int n = nums.length, left = 0, right = nums[n - 1] - nums[0];
        while (left <= right) {
            int mid = (left + right) / 2;
            int cnt = 0;
            for (int j = 0; j < n; j++) {
                // 每一个 j 对 cnt 的共享度
                int i = binarySearch(nums, j, nums[j] - mid);
                // 有[i, i+ 1, ~ j - 1] 组成的范围集合, 使得 nums[j] - nums[i] <= nums[j] - mid
                cnt += j - i;
            }
            if (cnt >= k) {
                // 缩小绝对值
                right = mid - 1;
            } else {
                // 扩大绝对值
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     * 二分查找算法实现
     * 在有序数组nums中查找目标值target的位置
     * @param nums 有序数组
     * @param end 查找的结束位置（数组索引）
     * @param target 要查找的目标值
     * @return 目标值在数组中的位置索引，如果不存在则返回应该插入的位置
     */
    public int binarySearch(int[] nums, int end, int target) {
        // 初始化查找范围的左右边界
        int left = 0, right = end;
        // 当左边界小于右边界时继续查找
        while (left < right) {
            // 计算中间位置
            int mid = (left + right) / 2;
            // 如果中间位置的值小于目标值，说明目标在右半部分
            if (nums[mid] < target) {
                left = mid + 1;  // 调整左边界
            } else {
                right = mid;     // 调整右边界
            }
        }
        // 返回查找结果位置
        return left;
    }


    /**
     * @Description:
     * 1. 对 nums 进行升序排序
     * 2. 然后将 i, i + 1, 使用优先队列对 nums[i + 1] - nums[i] 进行排序,
     * 那么就会得到 i 和 i + 1 取出的两个下标, 然后将 i - 1 和 i + 1添加, 以及 i 和 i + 2 进行添加, 并且 k--
     * 3. 当 k == 0 时, 返回 nums[i + 1] - nums[i]
     * 4. 由于可能添加重复元素
     * 超时: 20/21
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/07/23 16:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        boolean[][] visited = new boolean[n][n];
        // 创建优先队列
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> nums[a[1]] - nums[a[0]] - (nums[b[1]] - nums[b[0]]));
        // 添加 n - 1 个初始元素
        for (int i = 0; i < n - 1; i++) {
            pq.offer(new int[]{i, i + 1});
            visited[i][i + 1] = true;
        }
        // 取出 k 个元素
        while (k > 1) {
            int[] pair = pq.poll();
            int i = pair[0], j = pair[1];
            if (i > 0 && !visited[i - 1][j]) {
                pq.offer(new int[]{i - 1, j});
                visited[i - 1][j] = true;
            }
            if (j < n - 1 && !visited[i][j + 1]) {
                pq.offer(new int[]{i, j + 1});
                visited[i][j + 1] = true;
            }
            k--;
        }


        return nums[pq.peek()[1]] - nums[pq.peek()[0]];
    }

}
