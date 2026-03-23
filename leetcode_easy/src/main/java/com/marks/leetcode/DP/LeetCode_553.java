package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_553 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/23 10:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_553 {

    /**
     * @Description:
     * 给定一正整数数组 nums，nums 中的相邻整数将进行浮点除法。
     * 例如，nums = [2,3,4]，我们将求表达式的值 "2/3/4"。
     * 但是，你可以在任意位置添加任意数目的括号，来改变算数的优先级。你需要找出怎么添加括号，以便计算后的表达式的值为最大值。
     * 以字符串格式返回具有最大值的对应表达式。
     * 注意：你的表达式不应该包含多余的括号。
     *
     * tips:
     * 1 <= nums.length <= 10
     * 2 <= nums[i] <= 1000
     * 对于给定的输入只有一种最优除法。
     * @param: nums
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/03/23 10:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String optimalDivision(int[] nums) {
        String result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 计算两个数相除的最大值, 假设 n > 2, x0 / (x1 / x2 / x3 / ... / xn)
     * 2. 所以只需要在第一个数后面添加 /(, 然后在最后一个数后面添加 ), 这就可以了
     * AC: 1ms/42.25MB
     * @param: nums
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/03/23 10:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(int[] nums) {
        // 构建一个StringBuilder对象
        int n = nums.length;
        StringBuilder sb = new StringBuilder();
        // 处理第0个数
        sb.append(nums[0]);
        if (n == 1) {
            return sb.toString();
        } else if (n == 2) {
            sb.append("/").append(nums[1]);
        } else {
            sb.append("/(");
            for (int i = 1; i < n; i++) {
                sb.append(nums[i]);
                if (i != n - 1) {
                    sb.append("/");
                }
            }
            sb.append(")");
        }

        return sb.toString();
    }

}
