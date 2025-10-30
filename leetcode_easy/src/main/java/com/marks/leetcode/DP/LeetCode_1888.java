package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1888 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/29 17:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1888 {

    /***
     * @Description:
     * 给你一个二进制字符串 s 。你可以按任意顺序执行以下两种操作任意次：
     *
     * 类型 1 ：删除 字符串 s 的第一个字符并将它 添加 到字符串结尾。
     * 类型 2 ：选择 字符串 s 中任意一个字符并将该字符 反转 ，也就是如果值为 '0' ，则反转得到 '1' ，反之亦然。
     * 请你返回使 s 变成 交替 字符串的前提下， 类型 2 的 最少 操作次数 。
     * 我们称一个字符串是 交替 的，需要满足任意相邻字符都不同。
     * 比方说，字符串 "010" 和 "1010" 都是交替的，但是字符串 "0100" 不是。
     * tips:
     * 1 <= s.length <= 10^5
     * s[i] 要么是 '0' ，要么是 '1' 。
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2025/10/29 17:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minFlips(String s) {
        int result;
        result = method_01(s);
        return result;
    }


    /***
     * @Description: [方法描述]
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2025/10/29 17:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {

        return 0;
    }

}
