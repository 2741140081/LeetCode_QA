package com.marks.leetcode.daily_question;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3289 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/31 16:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3289 {

    /**
     * @Description:
     * 数字小镇 Digitville 中，存在一个数字列表 nums，其中包含从 0 到 n - 1 的整数。
     * 每个数字本应 只出现一次，然而，有 两个 顽皮的数字额外多出现了一次，使得列表变得比正常情况下更长。
     *
     * 为了恢复 Digitville 的和平，作为小镇中的名侦探，请你找出这两个顽皮的数字。
     *
     * 返回一个长度为 2 的数组，包含这两个数字（顺序任意）。
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2025/10/31 16:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] getSneakyNumbers(int[] nums) {
        int[] result;
        result = method_01(nums);
        return result;
    }


    /**
     * @Description: [方法描述]
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2025/10/31 16:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums) {
        int n = nums.length - 2;
        boolean[] visited = new boolean[n];
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            if (visited[num]) {
                list.add(num);
            } else {
                visited[num] = true;
            }
        }

        return list.stream().mapToInt(Integer::intValue).toArray();
    }

}
