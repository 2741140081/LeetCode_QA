package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/12 9:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_693 {

    
    /**
     * @Description:
     * 给定一个正整数，检查它的二进制表示是否总是 0、1 交替出现：换句话说，就是二进制表示中相邻两位的数字永不相同。
     * @param n
     * @return boolean
     * @author marks
     * @CreateDate: 2025/9/12 9:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean hasAlternatingBits(int n) {
        boolean result;
        result = method_01(n);
        return result;
    }

    
    /**
     * @Description:
     * AC: 0ms/39.93MB
     * @param n 
     * @return boolean
     * @author marks
     * @CreateDate: 2025/9/12 9:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int n) {
        int pre = n & 1;
        n = n >> 1;
        while (n != 0) {
            int cur = n & 1;
            if (cur == pre) {
                return false;
            }
            pre = cur;
            n = n >> 1;
        }
        return true;
    }

}
