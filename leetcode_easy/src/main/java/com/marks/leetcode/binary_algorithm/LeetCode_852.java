package com.marks.leetcode.binary_algorithm;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_852 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/27 14:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_852 {

    /**
     * @Description:
     * 给定一个长度为 n 的整数 山脉 数组 arr ，其中的值递增到一个 峰值元素 然后递减。
     * 返回峰值元素的下标。
     * 你必须设计并实现时间复杂度为 O(log(n)) 的解决方案。
     * @param: arr
     * @return int
     * @author marks
     * @CreateDate: 2025/11/27 14:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int peakIndexInMountainArray(int[] arr) {
        int result;
        result = method_01(arr);
        return result;
    }

    /**
     * @Description:
     * AC: 0ms/78.77MB
     * @param: arr
     * @return int
     * @author marks
     * @CreateDate: 2025/11/27 14:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr) {
        int n = arr.length;
        int left = 0, right = n - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] > arr[mid + 1]) {
                // 右侧的下降区, 峰顶在mid左侧
                right = mid;
            } else {
                // 左侧的上升区, 峰顶在mid右侧
                left = mid + 1;
            }
        }
        return left;
    }

}
