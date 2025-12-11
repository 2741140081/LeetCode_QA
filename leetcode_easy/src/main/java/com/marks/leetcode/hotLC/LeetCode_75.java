package com.marks.leetcode.hotLC;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_75 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/11 14:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_75 {


    /**
     * @Description:
     * 给定一个包含红色、白色和蓝色、共 n 个元素的数组 nums ，
     * 原地 对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
     * 我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
     * 必须在不使用库内置的 sort 函数的情况下解决这个问题
     * 。
     * @param: nums
     * @return void
     * @author marks
     * @CreateDate: 2025/12/11 14:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void sortColors(int[] nums) {
        method_01(nums);
    }

    /**
     * @Description:
     * 1. 这个问题就是一个排序问题, 但是要求不能使用库内置的排序函数
     * 2. 好久没用过归并排序, 来尝试下, 归并排序需要创建新的数组, 创建新的数组会占用额外的空间, 所以时间复杂度会高
     * 3. 修改使用堆排序, 堆排序时间复杂度是O(nlogn), 空间复杂度是O(1),
     * 4. 构建一个小根堆(与大根堆类似, 只不过大根堆放在nums[0], 小根堆放在 nums[n - 1])
     * 5. 使用大根堆构建二维数组, 然后将栈顶元素交换到末尾, 并且栈的大小减1
     * AC: 1ms/43MB
     * @param: nums
     * @return void
     * @author marks
     * @CreateDate: 2025/12/11 14:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void method_01(int[] nums) {
        int n = nums.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            maxHeapify(nums, i, n);
        }
        for (int i = n - 1; i >= 0; i--) {
            swap(nums, 0, i);
            maxHeapify(nums, 0, i);
        }
    }

    private void maxHeapify(int[] nums, int i, int heapSize) {
        int left = i * 2 + 1, right = i * 2 + 2, smallest = i;
        if (left < heapSize && nums[left] > nums[smallest]) {
            smallest = left;
        }
        if (right < heapSize && nums[right] > nums[smallest]) {
            smallest = right;
        }
        if (smallest != i) {
            swap(nums, i, smallest);
            maxHeapify(nums, smallest, heapSize);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}
