package com.marks.leetcode.DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/10 16:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_87 {

    /**
     * @Description:
     * 使用下面描述的算法可以扰乱字符串 s 得到字符串 t ：
     * 如果字符串的长度为 1 ，算法停止
     * 如果字符串的长度 > 1 ，执行下述步骤：
     * 在一个随机下标处将字符串分割成两个非空的子字符串。即，如果已知字符串 s ，则可以将其分成两个子字符串 x 和 y ，且满足 s = x + y 。
     * 随机 决定是要「交换两个子字符串」还是要「保持这两个子字符串的顺序不变」。即，在执行这一步骤之后，s 可能是 s = x + y 或者 s = y + x 。
     * 在 x 和 y 这两个子字符串上继续从步骤 1 开始递归执行此算法。
     * 给你两个 长度相等 的字符串 s1 和 s2，判断 s2 是否是 s1 的扰乱字符串。如果是，返回 true ；否则，返回 false 。
     * @param s1 
     * @param s2
     * @return boolean
     * @author marks
     * @CreateDate: 2025/10/10 16:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isScramble(String s1, String s2) {
        boolean result;
        result = method_01(s1, s2);
        return result;
    }

    
    /**
     * @Description:
     * s1 = "great", s2 = "rgeat"
     * 1. 递归的的是已经拆分的x 和 y,
     * need todo
     * @param s1 
     * @param s2 
     * @return boolean
     * @author marks
     * @CreateDate: 2025/10/10 16:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String s1, String s2) {
        
        return false;
    }

}
