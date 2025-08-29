package com.marks.leetcode.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/22 18:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_93 {
    
    /**
     * @Description:
     * 有效 IP 地址 正好由四个整数（每个整数位于 0 到 255 之间组成，且不能含有前导 0），整数之间用 '.' 分隔。
     *
     * 例如："0.1.2.201" 和 "192.168.1.1" 是 有效 IP 地址，但是 "0.011.255.245"、"192.168.1.312" 和 "192.168@1.1" 是 无效 IP 地址。
     * 给定一个只包含数字的字符串 s ，用以表示一个 IP 地址，返回所有可能的有效 IP 地址，这些地址可以通过在 s 中插入 '.' 来形成。你 不能 重新排序或删除 s 中的任何数字。你可以按 任何 顺序返回答案。
     *
     * tips:
     * 1 <= s.length <= 20
     * s 仅由数字组成
     * @param s
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2025/8/22 18:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<String> restoreIpAddresses(String s) {
        List<String> result;
        result = method_01(s);
        return result;
    }
    
    

    /**
     * @Description:
     * E1:
     * 输入：s = "101023"
     * 输出：["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
     * 1. 构建的ip地址段不能有前导0, 例如"01,0,0,0"; 地址范围：0-255; 只能切割为4段
     * 2. 每次切割点是 0, 1, 2。 并且需要 index == n, list.size() == 4
     *
     * AC: 2ms(74.24%)/42.28MB(22.85%)
     * @param s 
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2025/8/22 18:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<String> ans;
    private int n;
    private List<String> method_01(String s) {
        ans = new ArrayList<>();
        n = s.length();

        if (s.length() < 4 || s.length() > 12) {
            // 不合法的ip地址
            return ans;
        }
        List<String> list = new ArrayList<>();

        backtrack(s, list, 0);
        return ans;
    }

    private void backtrack(String s, List<String> list, int index) {
        if (index == n && list.size() == 4) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i));
                if (i != list.size() - 1) {
                    sb.append(".");
                }
            }
            ans.add(sb.toString());
            return;
        }
        for (int i = 1; i <= 3 && index + i <= n; i++) {
            String substring = s.substring(index, index + i);
            if (isValid(substring)) {
                list.add(substring);
                backtrack(s, list, index + i);
                list.remove(list.size() - 1);
            }
        }
    }

    private boolean isValid(String str) {
        if (str.charAt(0) == '0' && str.length() > 1) {
            return false;
        } else if (Integer.parseInt(str) > 255) {
            return false;
        } else {
            return true;
        }
    }

}
