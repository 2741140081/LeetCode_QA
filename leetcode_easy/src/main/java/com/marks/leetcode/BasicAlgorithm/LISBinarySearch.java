package com.marks.leetcode.BasicAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LISBinarySearch </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/2 16:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LISBinarySearch {
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // tails[i] 存放长度为 i+1 的递增子序列的最小尾元素
        List<Integer> tails = new ArrayList<>();

        for (int num : nums) {
            // 使用二分查找找到 num 在 tails 中的插入位置
            // 我们要找的是第一个 >= num 的位置
            int pos = binarySearch(tails, num);

            if (pos == tails.size()) {
                // 如果 num 比 tails 中所有元素都大，追加到末尾
                tails.add(num);
            } else {
                // 否则，替换掉该位置的元素，保持 tails[pos] 尽可能小
                tails.set(pos, num);
            }
        }

        return tails.size();
    }

    /**
     * 在 list 中查找第一个 >= target 的索引
     * 如果所有元素都 < target，返回 list.size()
     */
    private int binarySearch(List<Integer> list, int target) {
        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (list.get(mid) < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

}
