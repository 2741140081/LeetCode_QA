package com.marks.leetcode.array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_229 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/27 14:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_229 {

    /**
     * @Description:
     * 给定一个大小为 n 的整数数组，找出其中所有出现超过 ⌊n / 3⌋ 次的元素。
     * @param: nums
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/07/27 14:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> majorityElement(int[] nums) {
        List<Integer> list = null;
        list = method_01(nums);
        return list;
    }

    /**
     * @Description:
     * AC: 10ms/50.59MB
     * @param: nums
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/07/27 14:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.merge(num, 1, Integer::sum);
        }
        List<Integer> ans = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > (n / 3)) {
                ans.add(entry.getKey());
            }
        }

        return ans;
    }

}
