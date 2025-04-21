package com.marks.leetcode.greedy_algorithm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/7 10:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_870 {
    
    /**
     * @Description:
     * 给定两个长度相等的数组 nums1 和 nums2，nums1 相对于 nums2 的优势可以用满足 nums1[i] > nums2[i] 的索引 i 的数目来描述。
     *
     * 返回 nums1 的 任意 排列，使其相对于 nums2 的优势最大化。
     * @param nums1 
     * @param nums2
     * @return int[]
     * @author marks
     * @CreateDate: 2025/4/7 10:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] advantageCount(int[] nums1, int[] nums2) {
        int[] result;
        result = method_01(nums1, nums2);
        return result;
    }

    /**
     * @Description:
     * 还是排序 + 双指针
     * 1. 需要对 nums2[] 进行特殊处理, 需要给 nums2[] 添加额外的 index 数据
     * 2. 排序, 并且双指针比较
     * 3. 当 right >= 0 时, 表示存在元素不符合条件 nums1[i] > nums2[i], 需要将这些额外的数据添加到最终数组 ans[]
     * 4. 我们使用Set 集合来进行记录[0~n) 的坐标信息, 如果坐标使用了, 那么 set.remove(index), 最后使用增强for 遍历 set 集合
     *
     * AC: 104ms/62.89MB
     * @param nums1 
     * @param nums2 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/4/7 10:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[][] pre = new int[n][2];
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            pre[i][0] = nums2[i];
            pre[i][1] = i;
            set.add(i);
        }
        Arrays.sort(pre, Comparator.comparingInt(o -> o[0]));
        Arrays.sort(nums1);

        int[] ans = new int[n];
        int left = n - 1;
        int right = n - 1;
        while (left >= 0) {
            // nums1[i] > pre[i][0]
            if (nums1[right] > pre[left][0]) {
                ans[pre[left][1]] = nums1[right];
                set.remove(pre[left][1]);
                right--;
            }
            left--;
        }

        // nums1[] 中可能存在剩余未赋值的下标
        for (int index : set) {
            ans[index] = nums1[right--];
        }

        return ans;
    }
}
