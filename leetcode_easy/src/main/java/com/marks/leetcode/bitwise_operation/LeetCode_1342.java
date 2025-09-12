package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/11 15:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1342 {

    
    /**
     * @Description:
     * 给你一个非负整数 num ，请你返回将它变成 0 所需要的步数。 如果当前数字是偶数，你需要把它除以 2 ；否则，减去 1 。
     *
     * tips:
     * 0 <= num <= 10^6
     * @param num
     * @return int
     * @author marks
     * @CreateDate: 2025/9/11 15:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numberOfSteps(int num) {
        int result;
        result = method_01(num);
        return result;
    }

    
    /**
     * @Description:
     * E1:
     * 输入：num = 14
     * 输出：6
     * 1. 1110 => 1110 >> 1 = 111(7)
     * 2. 位运算, 判断当前是否是偶数还是奇数 num & 1 == 0, 是偶数, 否则是奇数
     * 2. 偶数 >> 1 = 7, 奇数 -1, 减一操作是用位运算还是直接减1, 算了直接减1
     * AC: 0ms/39.76MB
     * @param num 
     * @return int
     * @author marks
     * @CreateDate: 2025/9/11 15:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int num) {
        int ans = 0;
        while (num != 0) {
            if ((num & 1) == 0) {
                // 偶数
                num >>= 1;
            } else {
                num--;
            }
            ans++;
        }
        return ans;
    }

}
