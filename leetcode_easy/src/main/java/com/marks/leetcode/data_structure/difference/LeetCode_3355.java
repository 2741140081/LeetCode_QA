package com.marks.leetcode.data_structure.difference;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/18 10:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3355 {
    
    /**
     * @Description:
     * 给定一个长度为 n 的整数数组 nums 和一个二维数组 queries，其中 queries[i] = [li, ri]。
     *
     * 对于每个查询 queries[i]：
     * 在 nums 的下标范围 [li, ri] 内选择一个下标子集。
     * 将选中的每个下标对应的元素值减 1。
     * 零数组 是指所有元素都等于 0 的数组。
     *
     * 如果在按顺序处理所有查询后，可以将 nums 转换为 零数组 ，则返回 true，否则返回 false。
     *
     * @param nums 
     * @param queries 
     * @return boolean
     * @author marks
     * @CreateDate: 2025/2/18 10:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isZeroArray(int[] nums, int[][] queries) {
        boolean result;
        result = method_01(nums, queries);
        return result;
    }

    /**
     * @Description:
     * 对queries[][] 数组进行差分操作, 比较 nums[] 与diff[] , 如果存在 nums[i] > diff[i], return false, 否则 true
     * AC: 3ms/92.98MB
     * @param nums 
     * @param queries 
     * @return boolean
     * @author marks
     * @CreateDate: 2025/2/18 10:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums, int[][] queries) {
        int[] diff = new int[nums.length + 1];
        for (int[] query : queries) {
            int left = query[0], right = query[1];
            diff[left]++;
            diff[right + 1]--;
        }
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            count += diff[i];
            if (count < nums[i]) {
                return false;
            }
        }

        return true;
    }
}
