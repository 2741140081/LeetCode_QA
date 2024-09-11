package com.marks.leetcode.state_machine_DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称: 秋叶收藏集 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/11 15:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_19 {
    /**
     * @Description: [
     * 小扣出去秋游，途中收集了一些红叶和黄叶，他利用这些叶子初步整理了一份秋叶收藏集 leaves， 字符串 leaves 仅包含小写字符 r 和 y，
     * 其中字符 r 表示一片红叶，字符 y 表示一片黄叶。 出于美观整齐的考虑，小扣想要将收藏集中树叶的排列调整成「红、黄、红」三部分。
     * 每部分树叶数量可以不相等，但均需大于等于 1。每次调整操作，小扣可以将一片红叶替换成黄叶或者将一片黄叶替换成红叶。
     * 请问小扣最少需要多少次调整操作才能将秋叶收藏集调整完毕。
     *
     * tips:
     * 3 <= leaves.length <= 10^5
     * leaves 中只包含字符 'r' 和字符 'y'
     * ]
     * @param leaves
     * @return int
     * @author marks
     * @CreateDate: 2024/9/11 15:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumOperations(String leaves) {
        int result = 0;
        result = method_01(leaves);
        return result;
    }

    /**
     * @Description: [
     * 需要调整成 R - Y - R 模式
     * l[0] == 'r'
     * l[n - 1] == 'r'
     * l[1 ~ n - 2] contain 'y'
     * E1: "rrryyyrryyyrr"
     * 从左往右遍历
     * for i 1 ~ n - 1
     * dpy = [0,0,1,2,3,0,0,1,2,3,0]
     * dpr = [1,2,0,0,0,1,2,0,0,0,1]
     * dp[i] =
     * ]
     * @param leaves
     * @return int
     * @author marks
     * @CreateDate: 2024/9/11 15:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String leaves) {
        int n = leaves.length();
        int cNum = leaves.charAt(0) == 'r' ? 0 : 1 + leaves.charAt(n - 1) == 'r' ? 0 : 1;
        int left = leaves.indexOf('y');
        int right = leaves.lastIndexOf('y');

        if (left == -1) {
            // 1. 如果left = right = -1, 即不存在'y'
            return cNum + 1;
        }else if (left == right) {
            // 2. left = right && left != -1, 即只存在一个'y'
            return cNum;
        }


        return 0;
    }
}
