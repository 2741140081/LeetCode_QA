package com.marks.leetcode.array_medium;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_949 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/21 10:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_949 {

    /**
     * @Description:
     * 给定一个由 4 位数字组成的数组，返回可以设置的符合 24 小时制的最大时间。
     * 24 小时格式为 "HH:MM" ，其中 HH 在 00 到 23 之间，MM 在 00 到 59 之间。
     * 最小的 24 小时制时间是 00:00 ，而最大的是 23:59 。从 00:00 （午夜）开始算起，过得越久，时间越大。
     * 以长度为 5 的字符串，按 "HH:MM" 格式返回答案。如果不能确定有效时间，则返回空字符串。
     *
     * tips:
     * arr.length == 4
     * 0 <= arr[i] <= 9
     * @param: arr
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/09/21 10:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String largestTimeFromDigits(int[] arr) {
        String result;
        result = method_01(arr);
        return result;
    }

    /**
     * @Description:
     * 1. 由于len(arr) == 4，因此可以使用全排列的方法，生成所有可能的时间组合
     * 2. 然后枚举所有可能的时间组合，判断是否符合 24 小时制，如果符合则更新结果，最后返回结果
     * AC: 14ms/44.07MB
     * @param: arr
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/09/21 10:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(int[] arr) {
        int n = arr.length;
        String ans = "";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    for (int l = 0; l < n; l++) {
                        if (i != j && i != k && i != l && j != k && j != l && k != l) {
                            int hour = arr[i] * 10 + arr[j];
                            int minute = arr[k] * 10 + arr[l];
                            if (hour < 24 && minute < 60) {
                                ans = getMaxTime(ans, hour, minute);
                            }
                        }
                    }
                }
            }
        }

        return ans;
    }

    private String getMaxTime(String ans, int hour, int minute) {
        return ans.compareTo(String.format("%02d:%02d", hour, minute)) > 0 ? ans : String.format("%02d:%02d", hour, minute);
    }
}
