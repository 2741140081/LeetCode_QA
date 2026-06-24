package com.marks.leetcode.all_str;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1419 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/24 11:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1419 {

    /**
     * @Description:
     * 给你一个字符串 croakOfFrogs，它表示不同青蛙发出的蛙鸣声（字符串 "croak" ）的组合。
     * 由于同一时间可以有多只青蛙呱呱作响，所以 croakOfFrogs 中会混合多个 “croak” 。
     * 请你返回模拟字符串中所有蛙鸣所需不同青蛙的最少数目。
     * 要想发出蛙鸣 "croak"，青蛙必须 依序 输出 ‘c’, ’r’, ’o’, ’a’, ’k’ 这 5 个字母。如果没有输出全部五个字母，那么它就不会发出声音。
     * 如果字符串 croakOfFrogs 不是由若干有效的 "croak" 字符混合而成，请返回 -1 。
     * @param: croakOfFrogs
     * @return int
     * @author marks
     * @CreateDate: 2026/06/24 11:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minNumberOfFrogs(String croakOfFrogs) {
        int result;
        result = method_01(croakOfFrogs);
        return result;
    }

    /**
     * @Description:
     * 1. 每次遇到 'k', 表示一个蛙呱呱的结束，将当前蛙呱呱的数目减1, 并且 c,r,o,a 是一个递减的序列.
     * 2. 使用 map 分别记录 c, r, o, a 字符的频数
     * AC: 12ms/46.03MB
     * @param: croakOfFrogs
     * @return int
     * @author marks
     * @CreateDate: 2026/06/24 11:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String croakOfFrogs) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('c', 0);
        map.put('r', 1);
        map.put('o', 2);
        map.put('a', 3);
        int[] cnt = new int[4];
        int n = croakOfFrogs.length();
        int ans = 0;
        for (int i = 0; i < n; i++) {
            char c = croakOfFrogs.charAt(i);
            if (c == 'k') {
                // 需要取出一个蛙呱呱
                for (int j = 0; j < 4; j++) {
                    if (cnt[j] > 0) {
                        cnt[j]--;
                    } else {
                        // 缺少蛙呱呱
                        return -1;
                    }
                }
            } else {
                int idx = map.get(c);
                cnt[idx]++;
                if (idx > 0 && cnt[idx] > cnt[idx - 1]) {
                    return -1;
                }
                ans = Math.max(ans, cnt[idx]);
            }
        }
        // 判断是否有剩余的字符
        for (int j = 0; j < 4; j++) {
            if (cnt[j] > 0) {
                return -1;
            }
        }

        return ans;
    }

}
