package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_179 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/29 11:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_179 {

    /**
     * @Description:
     * 给定一组非负整数 nums，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。
     * 注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数。
     *
     * tips:
     * 1 <= nums.length <= 100
     * 0 <= nums[i] <= 10^9
     * @param: nums
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/04/29 11:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String largestNumber(int[] nums) {
        String result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 本质是对数组进行排序, 但是不是一般的形式的排序方式, 而从左往右比较数字的大小
     * 2. 为了方便进行比较, 将 int[] 数组转换为String[] 数组
     * 3. 排序方案使用冒泡排序
     * 4. 冒泡排序, 找到最小的一个数字, 放到数组的末尾
     * 5. 怎么比较两个字符串的大小? 从第0位开始比较, 如果第0位相同, 则比较第1位, 以此类推, 直到比较完所有的位 或没有下一位
     * 6. [111311,1113] [12311, 123] 12312311, 12311123; [12300,123] => 12312300, 12300123
     * 1113111113, 1113111311;
     * AC: 14ms/46.58MB
     * @param: nums
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/04/29 11:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(int[] nums) {
        int n = nums.length;
        String[] strs = new String[n];
        for (int i = 0; i < n; i++) {
            strs[i] = String.valueOf(nums[i]);
        }
        // 冒泡排序
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (!compare(strs[j], strs[j + 1])) {
                    // swap
                    String temp = strs[j];
                    strs[j] = strs[j + 1];
                    strs[j + 1] = temp;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str);
        }
        // 特殊情况处理 "0000"

        String ans = sb.toString();
        return ans.startsWith("0") ? "0" : ans;
    }

    /**
     * @Description:
     * 1. s1 >= s2 才返回 true
     * 2. 如果 s1 < s2, 则返回 false
     * @param: s1
     * @param: s2
     * @return boolean
     * @author marks
     * @CreateDate: 2026/04/29 14:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean compare(String s1, String s2) {
        return (s1 + s2).compareTo(s2 + s1) >= 0;
    }

}
