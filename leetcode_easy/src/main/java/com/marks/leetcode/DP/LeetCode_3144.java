package com.marks.leetcode.DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/16 15:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3144 {

    /**
     * @Description:
     * 给你一个字符串 s ，你需要将它分割成一个或者更多的 平衡 子字符串。
     * 比方说，s == "ababcc" 那么 ("abab", "c", "c") ，("ab", "abc", "c") 和 ("ababcc") 都是合法分割，
     * 但是 ("a", "bab", "cc") ，("aba", "bc", "c") 和 ("ab", "abcc") 不是，不平衡的子字符串用粗体表示。
     *
     * 请你返回 s 最少 能分割成多少个平衡子字符串。
     *
     * 注意：一个 平衡 字符串指的是字符串中所有字符出现的次数都相同。
     *
     * tips:
     * 1 <= s.length <= 1000
     * s 只包含小写英文字母。
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/10/16 15:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumSubstringsInPartition(String s) {
        int result;
        result = method_01(s);
        return result;
    }

    
    /**
     * @Description:
     * 想不到怎么处理, 先试一试暴力
     * 1. int n = s.length(); 切割n - 1次, 可以将剩余的字符串分成n段, 并且每一段都是平衡的
     * need todo
     * @param s 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/16 15:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        
        return 0;
    }

}
