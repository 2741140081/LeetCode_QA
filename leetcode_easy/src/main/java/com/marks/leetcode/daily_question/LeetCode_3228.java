package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3228 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/13 15:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3228 {

    /**
     * @Description:
     * 给你一个 二进制字符串 s。
     * 你可以对这个字符串执行 任意次 下述操作：
     * 选择字符串中的任一下标 i（ i + 1 < s.length ），该下标满足 s[i] == '1' 且 s[i + 1] == '0'。
     * 将字符 s[i] 向 右移 直到它到达字符串的末端或另一个 '1'。例如，对于 s = "010010"，如果我们选择 i = 1，结果字符串将会是 s = "000110"。
     * 返回你能执行的 最大 操作次数。
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2025/11/13 15:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxOperations(String s) {
        int result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * 输入： s = "1001101"
     * 输出： 4
     * 1. 思考是, 找到最右侧的1, 并且将他移动到字符串末端, 等等, 不太对, 需要找到的是最大的移动次数而不是最小的移动次数
     * 2. 按照例子来看, 应该是先将最左侧的1向右侧移动, 直到找到下一个1, 然后将两者作为一个整体进行移动, 假设这个整体有x个1, 如果下一个s[i] = '0'
     * 计算这个0(这个0是到下一个1的所有0的集合体, 因为只要整体1向右移动, 指挥停止在下一个1之前而不是一个0一个0的停止)
     * 3. 计算 ans += x; ans = 0, x = 0; s[0] = '1', x++; right = 3, s[right] = '1'; ans += x, x = 1;
     * 4. x++, right = 4; x++, right = 5;
     * AC: 8ms/46.77MB
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2025/11/13 15:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        s += '1';
        int n = s.length();
        int ans = 0;
        int left = 0;
        while (left < n && s.charAt(left) == '0') {
            left++;
        }
        int count = 1;
        int zeroCount = 0;
        for (int i = left + 1; i < n; i++) {
            if (s.charAt(i) == '1') {
                if (zeroCount > 0) {
                    ans += count;
                }
                count++;
                zeroCount = 0;
            } else {
                zeroCount++;
            }
        }
        return ans;
    }

}
