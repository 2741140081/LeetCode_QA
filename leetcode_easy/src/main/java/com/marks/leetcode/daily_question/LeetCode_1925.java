package com.marks.leetcode.daily_question;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1925 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/8 10:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1925 {

    /**
     * @Description:
     * 一个 平方和三元组 (a,b,c) 指的是满足 a^2 + b^2 = c^2 的 整数 三元组 a，b 和 c 。
     * 给你一个整数 n ，请你返回满足 1 <= a, b, c <= n 的 平方和三元组 的数目。
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/12/08 10:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countTriples(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * 1. 直接暴力吧, 没什么好的思路
     * AC: 26ms/45.58MB
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/12/08 10:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            map.put(i * i, i);
        }
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                int reminder = i * i + j * j;
                if (map.containsKey(reminder)) {
                    ans++;
                }
            }
        }
        return ans;
    }

}
