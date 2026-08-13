package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3348 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/7 14:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3348 {

    /**
     * @Description:
     * 给你一个字符串 num ，表示一个 正 整数，同时给你一个整数 t 。
     * 如果一个整数 没有 任何数位是 0 ，那么我们称这个整数是 无零 数字。
     * 请你返回一个字符串，这个字符串对应的整数是大于等于 num 的 最小无零 整数，且 各数位之积 能被 t 整除。如果不存在这样的数字，请你返回 "-1" 。
     * @param: num
     * @param: t
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/08/07 14:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String smallestNumber(String num, long t) {
        String result;
        result = method_01(num, t);
        result = method_02(num, t);
        return result;
    }

/**
 * 该方法用于找到一个数字字符串，使得该数字能被给定的数t整除，且尽可能接近原始输入的数字num
 * @param num 输入的数字字符串
 * @param t 需要整除的目标数
 * @return 返回符合条件的数字字符串，如果无法找到则返回"-1"
 */
    private String method_02(String num, long t) {
        long temp = t; // 创建t的临时副本
        // 检查t是否可以分解为2到9的因子
        for (int i = 2; i <= 9; i++) {
            while (temp % i == 0) {
                temp /= i; // 不断除以i，直到不能整除
            }
        }
        // 如果分解后temp大于1，说明t包含大于9的因子，无法用单个数字表示
        if (temp > 1) {
            return "-1";
        }

        int n = num.length(); // 获取输入数字的长度
        long[] rem = new long[n + 1]; // 创建余数数组，记录每一步的剩余需要整除的数
        rem[0] = t; // 初始余数为t
        int pos = n - 1; // 记录第一个0的位置

        char[] numChars = num.toCharArray(); // 将数字字符串转换为字符数组
        // 遍历数字字符串，计算每个位置的余数
        for (int i = 0; i < n; i++) {
            if (numChars[i] == '0') { // 如果遇到0，记录位置
                pos = i;
                break;
            }
            // 计算当前位置的余数，使用最大公约数来优化
            rem[i + 1] = rem[i] / gcd(rem[i], numChars[i] - '0');
        }

        // 如果最后余数为1，说明原始数字已经满足条件
        if (rem[n] == 1) {
            return num;
        }

        // 从第一个0的位置开始向前处理
        for (int i = pos; i >= 0; i--) {
            // 尝试增加当前数字
            while (++numChars[i] <= '9') {
                // 计算新的余数
                long tNow = rem[i] / gcd(rem[i], numChars[i] - '0');
                int k = 9; // 从最大的数字9开始尝试

                // 从后向前填充数字
                for (int j = n - 1; j > i; j--) {
                    // 找到能整除当前余数的最大数字
                    while (tNow % k != 0) {
                        k--;
                    }
                    tNow /= k; // 更新余数
                    numChars[j] = (char)('0' + k); // 设置当前位的数字
                }

                // 如果余数为1，说明找到了符合条件的数字
                if (tNow == 1) {
                    return new String(numChars);
                }
            }
        }

        // 如果无法找到符合条件的数字，构造一个新的数字
        StringBuilder ans = new StringBuilder();
        long originalT = t;
        // 从9到2分解t，构造尽可能大的数字
        for (int i = 9; i > 1; i--) {
            while (originalT % i == 0) {
                ans.append((char)('0' + i)); // 添加数字
                originalT /= i; // 更新t
            }
        }

        // 计算需要填充的1的数量
        int padding = Math.max(n + 1 - ans.length(), 0);
        for (int i = 0; i < padding; i++) {
            ans.append('1'); // 填充1
        }

        // 反转字符串并返回
        return ans.reverse().toString();
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }


    /**
     * @Description:
     * 1. 得到一个结论是 (a * b) % c == 0, 成立的条件是 b(min) = c / gcd(a, c).
     * 2. a 可以由[0, i] 位的前缀乘积得到, b 已知值, 但是[i + 1, n - 1] 怎么样组合可以得到乘积为 b,
     * 可以用类似于辗转相除法, 从[2 ~ 9] 中选择一个数 num, 判断 b % num == 0, 此时得到一个数 num 可以安排在 [i + 1, n - 1] 的某个位上,
     * 直到 b = 1 或者 b % [2 ~ 9] != 0. b = 1 [i + 1, n - 1] 的组合成立, 其它情况不成立.
     * 3. 现在有两个数组, 长度都是 m, list1 和 list2, 现在需要将 list2 重新组合, 使得 list2 的数字大于 list1, 并且返回 list2 的最小值
     * 4. 其实真实的 list2 是将 t 进行分解, 从[2 ~ 9]
     * @param: num
     * @param: t
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/08/07 14:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String num, long t) {

        return null;
    }

}
