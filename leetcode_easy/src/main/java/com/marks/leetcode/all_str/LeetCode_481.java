package com.marks.leetcode.all_str;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_481 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/22 14:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_481 {

    /**
     * @Description:
     * 神奇字符串 s 仅由 '1' 和 '2' 组成，并需要遵守下面的规则：
     * 将连续相同字符组 '1' 和 '2' 长度的序列连接起来会生成字符串 s 本身。
     * s 的前几个元素是 s = "1221121221221121122……" 。
     * 如果将 s 中连续的若干 1 和 2 进行分组，可以得到 "1 22 11 2 1 22 1 22 11 2 11 22 ......" 。
     * 每组中 1 或者 2 的出现次数分别是 "1 2 2 1 1 2 1 2 2 1 2 2 ......" 。
     * 上面的出现次数正是 s 自身。
     *
     * 给你一个整数 n ，返回在神奇字符串 s 的前 n 个数字中 1 的数目。
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/06/22 14:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int magicalString(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * 1. 初始是 122, 下一个是 12211
     * 2. 根据上一个序列构建下一个序列, 例如 上一个序列 122, 构建下一个序列, 序列生成使用1,2 交替, 数量是上一个序列的值, 1个1, 2个2, 2个1, 得到 12211
     * 3. 下一个序列是 1221121,
     * AC: 3ms/42.27MB
     * 感觉关键点在于找到字符串的生成规律
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/06/22 14:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        if (n < 4) {
            return 1;
        }
        char[] str = new char[n];
        str[0] = '1';
        str[1] = '2';
        str[2] = '2';
        int left = 2; // 位于 str[2] 位置
        int right = 3; // 新增位置
        int curr = 1;
        int ans = 1; // 1 的数量
        while (right < n) {
            int count = str[left] - '0'; // 需要增加的数量
            for (int i = 0; i < count && right < n; i++) {
                str[right] = curr == 1 ? '1' : '2';
                if (str[right] == '1') {
                    ans++;
                }
                right++;
            }
            left++;
            curr = curr == 1 ? 2 : 1;
        }

        return ans;
    }

}
