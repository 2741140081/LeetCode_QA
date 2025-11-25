package com.marks.leetcode.daily_question;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1018 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/24 16:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1018 {

    /**
     * @Description:
     * 给定一个二进制数组 nums ( 索引从0开始 )。
     * 我们将xi 定义为其二进制表示形式为子数组 nums[0..i] (从最高有效位到最低有效位)。
     * 例如，如果 nums =[1,0,1] ，那么 x0 = 1, x1 = 2, 和 x2 = 5。
     * 返回布尔值列表 answer，只有当 xi 可以被 5 整除时，答案 answer[i] 为 true，否则为 false。
     * @param: nums
     * @return java.util.List<java.lang.Boolean>
     * @author marks
     * @CreateDate: 2025/11/24 16:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Boolean> prefixesDivBy5(int[] nums) {
        List<Boolean> result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 存在问题,
     * 1. 101 = 5; 1010 = 10; 1111 = 15;
     * 10100 = 20; 11001 = 25; 11110 = 30; 100101 = 35;
     * @param: nums
     * @return java.util.List<java.lang.Boolean>
     * @author marks
     * @CreateDate: 2025/11/24 16:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Boolean> method_01(int[] nums) {
        List<Boolean> ans = new ArrayList<>();
        int sum = 0;
        for (int num : nums) {
            sum = sum << 1;
            sum += num;
            ans.add(sum % 5 == 0);
        }
        return ans;
    }
}
