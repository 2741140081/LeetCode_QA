package com.marks.leetcode.all_str;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1147 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/24 10:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1147 {

    /**
     * @Description:
     * 你会得到一个字符串 text 。你应该把它分成 k 个子字符串 (subtext1, subtext2，…， subtextk) ，要求满足:
     *
     * subtexti 是 非空 字符串
     * 所有子字符串的连接等于 text ( 即subtext1 + subtext2 + ... + subtextk == text )
     * 对于所有 i 的有效值( 即 1 <= i <= k ) ，subtexti == subtextk - i + 1 均成立
     * 返回k可能最大值。
     *
     * tips:
     * 1 <= text.length <= 1000
     * text 仅由小写英文字符组成
     * @param: text
     * @return int
     * @author marks
     * @CreateDate: 2026/06/24 10:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestDecomposition(String text) {
        int result;
        result = method_01(text);
        return result;
    }

    /**
     * @Description:
     * 输入：text = "ghi abcdef hello ad am hello abcdef ghi"
     * 输出：7
     * 解释：我们可以把字符串拆分成 "(ghi)(abcdef)(hello)(adam)(hello)(abcdef)(ghi)"。
     * 1. 使用双指针, left 从0 开始遍历, right 指向 n - 1位置处, 找到[0, n/2] 中 t[left] == t[right] 的位置,
     * 计算长度, int len = left + 1; 分别倒序遍历[0, left] 和 倒序遍历 [right - len + 1, right], 如果匹配, 则 k++; 继续匹配下一个;
     * 2. 如果不匹配, 则继续找下一个 left.
     * AC: 0ms/42.05MB
     * @param: text
     * @return int
     * @author marks
     * @CreateDate: 2026/06/24 10:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String text) {
        int n = text.length();
        int k = 0;
        int left = 0, right = n - 1;
        while (left < right) {
            int len = 1;
            while (left + len - 1 < right - len + 1) {
                if (checkValid(text, left, right - len + 1, len)) {
                    k += 2;
                    break;
                }
                len++;
            }
            if (left + len - 1 >= right - len + 1) {
                k++;
            }
            left += len;
            right -= len;
        }


        return k;
    }

    private boolean checkValid(String text, int left, int right, int len) {
        while (len > 0) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
            len--;
        }
        return true;
    }

}
