package com.marks.leetcode.all_str;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_165 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/17 16:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_165 {

    /**
     * @Description:
     * 给你两个 版本号字符串 version1 和 version2 ，请你比较它们。
     * 版本号由被点 '.' 分开的修订号组成。修订号的值 是它 转换为整数 并忽略前导零。
     * 比较版本号时，请按 从左到右的顺序 依次比较它们的修订号。如果其中一个版本字符串的修订号较少，则将缺失的修订号视为 0。
     * 返回规则如下：
     * 如果 version1 < version2 返回 -1，
     * 如果 version1 > version2 返回 1，
     * 除此之外返回 0。
     *
     * tips:
     * 1 <= version1.length, version2.length <= 500
     * version1 和 version2 仅包含数字和 '.'
     * version1 和 version2 都是 有效版本号
     * version1 和 version2 的所有修订号都可以存储在 32 位整数 中
     * @param: version1
     * @param: version2
     * @return int
     * @author marks
     * @CreateDate: 2026/06/17 16:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int compareVersion(String version1, String version2) {
        int result;
        result = method_01(version1, version2);
        return result;
    }

    /**
     * @Description:
     * 1. 先将 version1 和 version2 分割成数组
     * AC: 1ms/42.16MB
     * @param: version1
     * @param: version2
     * @return int
     * @author marks
     * @CreateDate: 2026/06/17 16:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String version1, String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        for (int i = 0; i < Math.max(v1.length, v2.length); i++) {
            int a = i < v1.length ? Integer.parseInt(v1[i]) : 0;
            int b = i < v2.length ? Integer.parseInt(v2[i]) : 0;
            if (a > b) {
                return 1;
            } else if (a < b) {
                return -1;
            }
        }
        return 0;
    }

}
