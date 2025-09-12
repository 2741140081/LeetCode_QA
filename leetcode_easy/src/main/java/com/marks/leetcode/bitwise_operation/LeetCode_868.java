package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/11 16:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_868 {

    
    /**
     * @Description:
     * 给定一个正整数 n，找到并返回 n 的二进制表示中两个 相邻 1 之间的 最长距离 。如果不存在两个相邻的 1，返回 0 。
     *
     * 如果只有 0 将两个 1 分隔开（可能不存在 0 ），则认为这两个 1 彼此 相邻 。
     * 两个 1 之间的距离是它们的二进制表示中位置的绝对差。例如，"1001" 中的两个 1 的距离为 3 。
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2025/9/11 16:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int binaryGap(int n) {
        int result;
        result = method_01(n);
        result = method_02(n);
        return result;
    }


    /**
     * @Description:
     * 使用位运算
     *
     * AC: 0ms/39.73MB
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2025/9/11 16:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int n) {
        int ans = 0;
        int last = -1;
        int index = 0;
        while (n > 0) {
            if ((n & 1) == 1) {
                if (last != -1) {
                    ans = Math.max(ans, index - last);
                }
                last = index;
            }
            n >>= 1; // 右移一位
            index++;
        }
        return ans;
    }


    /**
     * @Description:
     * E1:
     * 输入：n = 22
     * 输出：2
     * 1. 按照普通的方式计算吧, 就是吧 n 直接转为 二进制的 String str, 通过前后指针进行计算绝对差值
     * AC: 1ms/39.77MB
     * @param n 
     * @return int
     * @author marks
     * @CreateDate: 2025/9/11 16:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        int ans = 0;
        if (Integer.bitCount(n) <= 1) {
            return ans;
        }
        String str = Integer.toBinaryString(n);
        int left = -1;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '1') {
                left = i;
                break;
            }
        }
        for (int i = left + 1; i < str.length(); i++) {
            if (str.charAt(i) == '1') {
                ans = Math.max(ans, i - left);
                left = i;
            }
        }

        return ans;
    }

}
