package com.marks.leetcode.state_machine_DP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/4 15:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1363 {
    /**
     * @Description: [
     * 给你一个整数数组 digits，你可以通过按 任意顺序 连接其中某些数字来形成 3 的倍数，请你返回所能得到的最大的 3 的倍数。
     *
     * 由于答案可能不在整数数据类型范围内，请以字符串形式返回答案。如果无法得到答案，请返回一个空字符串。返回的结果不应包含不必要的前导零。
     *
     * tips:
     * 1 <= digits.length <= 10^4
     * 0 <= digits[i] <= 9
     * ]
     * @param digits
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2024/9/4 15:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String largestMultipleOfThree(int[] digits) {
        String result = "";
        result = method_01(digits);
        result = method_02(digits);
        return result;
    }

    /**
     * @Description: [官方题解:
     * 与我的题解思路类似, 但是官方题解更加优秀
     * 使用cnt[] 存储0~9的数量
     * 使用modulo[]存储 % 3 的数量, 相当于method_01的map_1 和map_2
     * 然后判断是否mod = sum % 3
     * 判断modulo[mod] >= 1, 如果true, 则只需要去除一个该值, 使用left存储mod值，使用rest记录要舍弃的数量
     * for i (0 ~ 9)
     *  如果cnt[i] > 0
     * ]
     * @param digits
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2024/9/9 14:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_02(int[] digits) {
        int[] cnt = new int[10];
        int[] modulo = new int[3];
        int sum = 0;
        for (int d : digits) {
            cnt[d]++;
            modulo[d % 3]++;
            sum += d;
        }

        final int left;
        int rest;
        final int mod = sum % 3;
        if (mod == 0) {
            left = rest = 0;
        } else {
            if (modulo[mod] >= 1) {
                left = mod;
                rest = 1;
            } else {
                // left = ~mod & 0b11;
                left = 2 * mod % 3;
                rest = 2;
            }
        }

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 10; ++i) {
            char ch = (char) (i + '0');
            for (int j = 0; j < cnt[i]; ++j) {
                if (rest > 0 && i % 3 == left) {
                    --rest;
                } else {
                    buffer.append(ch);
                }
            }
        }
        if (buffer.length() > 0 && buffer.charAt(buffer.length() - 1) == '0') {
            buffer = new StringBuffer("0");
        }
        return buffer.reverse().toString();
    }

    /**
     * @Description: [
     * 贪心 + 逆向思维
     * E1
     * digits = [8,6,7,1,0]
     * 排序: [0, 1, 6, 7, 8]
     * sum = 22
     * 22 % 3 = 1
     * 求出sum 如果  0 < sum < 3 return ""
     * sum = 0 return "0"
     * sum % 3 = 0
     *
     * sum % 3 = 1 舍弃余数为1的最小值, 如果不存在则舍弃2个余数为2的最小值
     * sum % 3 = 2 舍弃余数为2的最小值, 如果不存在则舍弃2个余数为1的最小值
     *
     * AC:11ms/44.58MB
     * ]
     * @param digits
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2024/9/4 15:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(int[] digits) {
        int n = digits.length;
        Arrays.sort(digits);
        int sum = 0;
        // list_1, list_2 存放digits[i] % 3 的余数的最小的2个值
        HashMap<Integer, Integer> map_1 = new HashMap<>();
        HashMap<Integer, Integer> map_2 = new HashMap<>();
        for (int i = 0; i < n; i++) {
            sum += digits[i];
            if (digits[i] % 3 == 1 && map_1.size() < 2) {
                map_1.put(i, digits[i]);
            } else if (digits[i] % 3 == 2 && map_2.size() < 2) {
                map_2.put(i, digits[i]);
            }
        }
        if (sum < 3) {
            if(sum != 0 && digits[0] != 0) {
                return "";
            }else{
                return "0";
            }
        }
        StringBuffer sb = new StringBuffer();
        if (sum % 3 == 0) {
            for (int i = 0; i < n; i++) {
                sb.append(digits[i]);
            }
        }else if (sum % 3 == 1) {
            if (map_1.size() > 0) {
                boolean temp_flag = true;
                for (int i = 0; i < n; i++) {
                    if (temp_flag && map_1.containsKey(i)) {
                        temp_flag = false;
                    }else {
                        sb.append(digits[i]);
                    }
                }
            }else if (map_2.size() > 1){
                // list_1 = 0, 舍弃2个list_2的最小值
                int temp_count = 2;
                for (int i = 0; i < n; i++) {
                    if (temp_count > 0 && map_2.containsKey(i)) {
                        temp_count--;
                    }else {
                        sb.append(digits[i]);
                    }
                }
            }
        }else {
            if (map_2.size() > 0) {
                boolean temp_flag = true;
                for (int i = 0; i < n; i++) {
                    if (temp_flag && map_2.containsKey(i)) {
                        temp_flag = false;
                    }else {
                        sb.append(digits[i]);
                    }
                }
            }else if (map_1.size() > 1){
                // list_1 = 0, 舍弃2个list_2的最小值
                int temp_count = 2;
                for (int i = 0; i < n; i++) {
                    if (temp_count > 0 && map_1.containsKey(i)) {
                        temp_count--;
                    }else {
                        sb.append(digits[i]);
                    }
                }
            }
        }

        // sb.reverse() 反转字符串,另外判断sb是否全部由0组成
        String ans = sb.reverse().toString();
        if (ans.length() > 0 && ans.charAt(0) == '0') {
            ans = "0";
        }
        return ans;
    }
}
