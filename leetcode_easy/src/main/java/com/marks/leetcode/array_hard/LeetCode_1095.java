package com.marks.leetcode.array_hard;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1095 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/5 16:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1095 {

    /**
     * @Description:
     * 你可以将一个数组 arr 称为 山脉数组 当且仅当：
     * arr.length >= 3
     * 存在一些 0 < i < arr.length - 1 的 i 使得：
     * arr[0] < arr[1] < ... < arr[i - 1] < arr[i]
     * arr[i] > arr[i + 1] > ... > arr[arr.length - 1]
     * 给定一个山脉数组 mountainArr ，返回 最小 的 index 使得 mountainArr.get(index) == target。
     * 如果不存在这样的 index，返回 -1 。
     * 你无法直接访问山脉数组。你只能使用 MountainArray 接口来访问数组：
     * MountainArray.get(k) 返回数组中下标为 k 的元素（从 0 开始）。
     * MountainArray.length() 返回数组的长度。
     * 调用 MountainArray.get 超过 100 次的提交会被判定为错误答案。
     * 此外，任何试图绕过在线评测的解决方案都将导致取消资格。
     *
     * tips:
     * 3 <= mountainArr.length() <= 10^4
     * 0 <= target <= 10^9
     * 0 <= mountainArr.get(index) <= 10^9
     * @param: target
     * @param: mountainArr
     * @return int
     * @author marks
     * @CreateDate: 2026/08/05 16:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findInMountainArray(int target, MountainArray mountainArr) {
        int result;
        result = method_01(target, mountainArr);
        return result;
    }

    /**
     * @Description:
     * 1. 获取 n, 然后获取山顶坐标, 通过二分查找, 此时最多调用 28 次 get() 2 * log(n)
     * 2. 将山形数组分割成两个部分, 分别查找目标值, 此时总计会调用 14 次 get, log(n)
     * AC: 0ms/45.73MB
     * @param: target
     * @param: mountainArr
     * @return int
     * @author marks
     * @CreateDate: 2026/08/05 16:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int target, MountainArray mountainArr) {
        int n = mountainArr.length();
        // 求得 high 的坐标点, 山顶的坐标
        int left = 0, right = n - 1;
        // 最多执行 14 * 2 = 28 次调用 get
        while (left < right) {
            int mid = (right - left) / 2 + left;
            if (mountainArr.get(mid) < mountainArr.get(mid + 1)) {
                // 位于递增阶段, 山顶在右侧
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        // 此时 left 是山顶坐标
        // 在 [0 ~ top] 升序序列 通过二分查找找到 mountainArr.get(index) == target, 如果找到直接返回
        int low = 0, high = left;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int midVal = mountainArr.get(mid);
            if (midVal == target) {
                return mid;
            } else if (midVal < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        // 在 [top + 1 ~ n - 1] 降序序列 通过二分查找 mountainArr.get(index) == target, 如果找到直接返回
        low = left + 1;
        high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int midVal = mountainArr.get(mid);
            if (midVal == target) {
                return mid;
            } else if (midVal < target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return -1;
    }
}
