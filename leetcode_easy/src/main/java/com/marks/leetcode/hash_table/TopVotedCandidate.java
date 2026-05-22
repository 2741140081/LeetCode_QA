package com.marks.leetcode.hash_table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: TopVotedCandidate </p>
 * <p>描述:
 * LeetCode_911. 在线选举
 *
 * 给你两个整数数组 persons 和 times 。在选举中，第 i 张票是在时刻为 times[i] 时投给候选人 persons[i] 的。
 * 对于发生在时刻 t 的每个查询，需要找出在 t 时刻在选举中领先的候选人的编号。
 * 在 t 时刻投出的选票也将被计入我们的查询之中。在平局的情况下，最近获得投票的候选人将会获胜。
 * 实现 TopVotedCandidate 类：
 * TopVotedCandidate(int[] persons, int[] times) 使用 persons 和 times 数组初始化对象。
 * int q(int t) 根据前面描述的规则，返回在时刻 t 在选举中领先的候选人的编号。
 *
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/21 11:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class TopVotedCandidate {
    private List<Integer> top; // 记录 i 时刻领先的p[i]
    private Map<Integer, Integer> map; // 记录每个人的票数
    private int[] times;
    /**
     * @Description:
     * 1. 创建一个类，用于保存选举信息, 即 p[i], count, t[i]
     * 2. 假设处理第 i 张票, p[i] = persons[i], t[i] = times[i]
     * @param: persons
     * @param: times
     * @return
     * @author marks
     * @CreateDate: 2026/05/21 11:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public TopVotedCandidate(int[] persons, int[] times) {
        top = new ArrayList<>();
        map = new HashMap<>();
        int n = persons.length;
        int index = -1; // 记录当前最高票的索引
        map.put(-1, -1);
        for (int i = 0; i < n; i++) {
            int p = persons[i];
            map.merge(p, 1, Integer::sum);
            if (map.get(p) >= map.get(index)) {
                index = p;
            }
            top.add(index);
        }
        this.times = times;
    }

    /**
     * @Description:
     * 二分查找法, 找到 times[i] <= t < times[i + 1]
     * AC: 81ms/71.21MB
     * @param: t
     * @return int
     * @author marks
     * @CreateDate: 2026/05/21 14:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int q(int t) {
        int left = 0, right = times.length - 1;
        int ans = 0;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (times[mid] <= t) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return top.get(ans);
    }
}
