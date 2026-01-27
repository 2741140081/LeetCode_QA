package com.marks.leetcode.daily_question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1200 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/26 16:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1200 {

    /**
     * @Description:
     * 给你个整数数组 arr，其中每个元素都 不相同。
     * 请你找到所有具有最小绝对差的元素对，并且按升序的顺序返回。
     * 每对元素对 [a,b] 如下：
     * a , b 均为数组 arr 中的元素
     * a < b
     * b - a 等于 arr 中任意两个元素的最小绝对差
     * @param: arr
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2026/01/26 16:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        List<List<Integer>> result;
        result = method_01(arr);
        return result;
    }

    /**
     * @Description:
     * 1. 找到最小绝对差
     * 2. 判断b - a是否等于最小绝对差
     * 排序
     * AC: 20ms/62.96MB
     * @param: arr
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2026/01/26 16:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> method_01(int[] arr) {
        int n = arr.length;
        Arrays.sort(arr);
        int sub = Integer.MAX_VALUE;
        for (int i = 0; i < n - 1; i++) {
            sub = Math.min(sub, arr[i + 1] - arr[i]);
        }
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < n - 1; i++) {
            if (arr[i + 1] - arr[i] == sub) {
                List<Integer> list = new ArrayList<>();
                list.add(arr[i]);
                list.add(arr[i + 1]);
                ans.add(list);
            }
        }
        return ans;
    }

}
