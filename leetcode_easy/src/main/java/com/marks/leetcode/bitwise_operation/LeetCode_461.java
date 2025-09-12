package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/11 14:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_461 {

    /**
     * @Description:
     * 两个整数之间的 汉明距离 指的是这两个数字对应二进制位不同的位置的数目。
     *
     * 给你两个整数 x 和 y，计算并返回它们之间的汉明距离。
     * @param x 
     * @param y
     * @return int
     * @author marks
     * @CreateDate: 2025/9/11 14:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int hammingDistance(int x, int y) {
        int result;
        result = method_01(x, y);
        return result;
    }

    /**
     * @Description:
     * 异或运算 x ^ y, 然后计算结果中1的数量
     * AC: 0ms/39.67MB
     * just call the Integer.bitCount(), don't do it by yourself(it's repeat wheels)
     * @param x 
     * @param y 
     * @return int
     * @author marks
     * @CreateDate: 2025/9/11 14:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int x, int y) {
        return Integer.bitCount(x ^ y);
    }

}
