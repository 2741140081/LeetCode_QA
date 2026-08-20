package com.marks.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3069 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/20 16:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3069 {

    /**
     * @Description:
     * 给你一个下标从 1 开始、包含 不同 整数的数组 nums ，数组长度为 n 。
     * 你需要通过 n 次操作，将 nums 中的所有元素分配到两个数组 arr1 和 arr2 中。
     * 在第一次操作中，将 nums[1] 追加到 arr1 。
     * 在第二次操作中，将 nums[2] 追加到 arr2 。之后，在第 i 次操作中：
     * 如果 arr1 的最后一个元素 大于 arr2 的最后一个元素，就将 nums[i] 追加到 arr1 。
     * 否则，将 nums[i] 追加到 arr2 。
     * 通过连接数组 arr1 和 arr2 形成数组 result 。
     * 例如，如果 arr1 == [1,2,3] 且 arr2 == [4,5,6] ，那么 result = [1,2,3,4,5,6] 。
     * 返回数组 result 。
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2026/08/20 16:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    public int[] resultArray(int[] nums) {
        int[] result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 模拟
     * AC: 1ms/46.06MB
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2026/08/20 16:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums) {
        int n = nums.length;
        int[] arr1 = new int[n];
        List<Integer> arr2 = new ArrayList<>();
        arr1[0] = nums[0];
        arr2.add(nums[1]);
        int idx = 1;
        for (int i = 2; i < n; i++) {
            if (arr1[idx - 1] > arr2.get(arr2.size() - 1)) {
                arr1[idx] = nums[i];
                idx++;
            } else {
                arr2.add(nums[i]);
            }
        }
        int right = 0;
        while (idx < n) {
            arr1[idx] = arr2.get(right);
            idx++;
            right++;
        }

        return arr1;
    }

}
