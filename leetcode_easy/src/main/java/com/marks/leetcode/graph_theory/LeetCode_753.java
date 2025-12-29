package com.marks.leetcode.graph_theory;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_753 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/29 14:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_753 {

    /**
     * @Description:
     * 有一个需要密码才能打开的保险箱。密码是 n 位数, 密码的每一位都是范围 [0, k - 1] 中的一个数字。
     * 保险箱有一种特殊的密码校验方法，你可以随意输入密码序列，保险箱会自动记住 最后 n 位输入 ，如果匹配，则能够打开保险箱。
     * 例如，正确的密码是 "345" ，并且你输入的是 "012345" ：
     * 输入 0 之后，最后 3 位输入是 "0" ，不正确。
     * 输入 1 之后，最后 3 位输入是 "01" ，不正确。
     * 输入 2 之后，最后 3 位输入是 "012" ，不正确。
     * 输入 3 之后，最后 3 位输入是 "123" ，不正确。
     * 输入 4 之后，最后 3 位输入是 "234" ，不正确。
     * 输入 5 之后，最后 3 位输入是 "345" ，正确，打开保险箱。
     * 在只知道密码位数 n 和范围边界 k 的前提下，请你找出并返回确保在输入的 某个时刻 能够打开保险箱的任一 最短 密码序列 。
     *
     * tips:
     * 1 <= n <= 4
     * 1 <= k <= 10
     * 1 <= kn <= 4096
     * @param: n
     * @param: k
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/12/29 14:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String crackSafe(int n, int k) {
        String result;
        result = method_01(n, k);
        return result;
    }

    /**
     * @Description:
     * 1. n = 3, k = 3; 有多少种情况 3^3 = 27;
     * 000, 001, 002, 010, 011, 012, 020, 021, 022,
     * 100, 101, 102, 110, 111, 112, 120, 121, 122,
     * 200, 201, 202, 210, 211, 212, 220, 221, 222;
     * 2. 给出几个猜测, 无论从那个初始字符串开始都能得到最短结果, 并且最短结果长度为 (n - 1) + n ^ k
     * 3. 基于2的条件, 可以使用一个StringBuilder ans, 存储当前结果, 通过状态压缩判断是否已经遍历过.
     * 4. 创建一个字典树, 存储所有可能的密码, 方便查找下一个密码
     * 5. 通过深度优先搜索 + 回溯, 找到最短密码 结束条件是 ans.length() == n ^ k + n - 1
     * AC: 7ms/45.59MB
     * @param: n
     * @param: k
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/12/29 14:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private int len;
    private String ans = null;
    private int n;
    private String method_01(int n, int k) {
        this.n = n;
        this.len = (int) Math.pow(k, n) + n - 1;
        Set<String> memo = new HashSet<>(); // 存储所有密码
        // 推测从任意一个初始字符串开始, 找到最短结果
        StringBuilder init = new StringBuilder();
        int index = 0;
        for (int i = 0; i < n; i++) {
            init.append(index % k);
            index++;
        }
        memo.add(init.toString());
        // 深度优先搜索 + 回溯
        dfsAndBackTrack(init, memo, k);
        return ans;
    }

    private void dfsAndBackTrack(StringBuilder curr, Set<String> memo, int k) {
        if (ans != null) {
            // 找到了最短结果, 提前结束
            return;
        }
        if (curr.length() == len) {
            ans = String.valueOf(curr);
            return;
        }
        // 进行深度优先搜索
        for (int i = k - 1; i >= 0; i--) {
            curr.append(i);
            // 截取密码
            String sub = curr.substring(curr.length() - n, curr.length());
            if (!memo.contains(sub)) {
                memo.add(sub);
                dfsAndBackTrack(curr, memo, k);
                if (ans != null) {
                    return;
                }
                memo.remove(sub);
            }
            curr.deleteCharAt(curr.length() - 1);
        }
    }

}
