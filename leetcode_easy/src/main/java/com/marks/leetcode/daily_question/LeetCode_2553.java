package com.marks.leetcode.daily_question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2553 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/11 11:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2553 {

    /**
     * @Description:
     * 给你一个正整数数组 nums ，请你返回一个数组 answer ，你需要将 nums 中每个整数进行数位分割后，按照 nums 中出现的 相同顺序 放入答案数组中。
     * 对一个整数进行数位分割，指的是将整数各个数位按原本出现的顺序排列成数组。
     * 比方说，整数 10921 ，分割它的各个数位得到 [1,0,9,2,1] 。
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2026/05/11 11:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] separateDigits(int[] nums) {
        int[] result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * AC: 8ms/46.33MB
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2026/05/11 11:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums) {
        List<Integer> ans = new ArrayList<>();
        for (int num : nums) {
            List<Integer> temp = splitNum(num);
            ans.addAll(temp);
        }
        return ans.stream().mapToInt(Integer::intValue).toArray();
    }

    private List<Integer> splitNum(int num) {
        char[] chars = String.valueOf(num).toCharArray();
        List<Integer> list = new ArrayList<>();
        for (char aChar : chars) {
            list.add(aChar - '0');
        }
        return list;
    }

}
