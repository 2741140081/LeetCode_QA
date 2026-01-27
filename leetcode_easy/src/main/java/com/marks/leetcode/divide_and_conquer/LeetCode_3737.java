package com.marks.leetcode.divide_and_conquer;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3737 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/27 11:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3737 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和一个整数 target。
     * 返回数组 nums 中满足 target 是 主要元素 的 子数组 的数目。
     * 一个子数组的 主要元素 是指该元素在该子数组中出现的次数 严格大于 其长度的 一半 。
     * 子数组 是数组中的一段连续且 非空 的元素序列。
     * tips:
     * 1 <= nums.length <= 1000
     * 1 <= nums[i] <= 10^9
     * 1 <= target <= 10^9
     * @param: nums
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2026/01/27 11:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countMajoritySubarrays(int[] nums, int target) {
        int result;
//        result = method_01(nums, target);
        result = method_02(nums, target);
        return result;
    }

    /**
     * @Description:
     * 1. 查看题解, 使用归并排序
     * @param: nums
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2026/01/27 14:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums, int target) {
        int n = nums.length;
        int[] count = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            count[i] = count[i - 1] + (nums[i - 1] == target ? 1 : -1);
        }
        return mergeSortAndCount(count, 0, n);
    }

    private int mergeSortAndCount(int[] count, int left, int right) {
        if (left >= right) {
            return 0;
        }
        int mid = (right - left) / 2 + left;
        int leftCount = mergeSortAndCount(count, left, mid);
        int rightCount = mergeSortAndCount(count, mid + 1, right);
        int i = left, j = mid + 1;
        int ans = leftCount + rightCount;
        while (i <= mid && j <= right) {
            if (count[i] < count[j]) {
                ans += mid - i + 1;
                j++;
            } else {
                i++;
            }
        }
        merge(count, left, mid, right);
        return ans;
    }

    private void merge(int[] count, int left, int mid, int right) {
        int len = right - left + 1;
        int[] temp = new int[len];
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) {
            if (count[i] >= count[j]) {
                temp[k++] = count[i++];
            } else {
                temp[k++] = count[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = count[i++];
        }
        while (j <= right) {
            temp[k++] = count[j++];
        }
        System.arraycopy(temp, 0, count, left, len);
    }

    /**
     * @Description:
     * 输入: nums = [1,2,2,3], target = 2
     * 输出: 5
     * 1. 这题怎么用分治法处理?
     * 2. 单个元素 nums[i] = target 时, 那么此时的 子数组是一个有效子数组
     * 3. 通过前缀和存储 count[i], 然后判断 [i, j] 的长度 len = j - i + 1 是否满足 int t = count[j] - count[i - 1]
     * 2 * t > len
     * 4. n <= 1000, 所以 O(n^2) 的时间复杂度可以接受.
     * AC: 36ms/46.22MB
     * @param: nums
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2026/01/27 11:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int target) {
        int n = nums.length;
        int[] count = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            count[i] = count[i - 1] + (nums[i - 1] == target ? 1 : 0);
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int t = count[j + 1] - count[i];
                if (2 * t > j - i + 1) {
                    ans++;
                }
            }
        }
        return ans;
    }

}
