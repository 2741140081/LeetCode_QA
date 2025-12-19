package com.marks.leetcode.daily_question;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2092 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/19 16:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2092 {

    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        List<Integer> result;
        result = method_01(n, meetings, firstPerson);
        return result;
    }

    /**
     * @Description:
     * 并查集来获取人员是否知晓密码
     * 1. 并查集, 需要及时重置节点的信息, reset(x)
     * 2. 对同一个时间戳的会议进行并查集
     * AC: 83ms/193.31MB
     * @param: n
     * @param: meetings
     * @param: firstPerson
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/12/19 16:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int n, int[][] meetings, int firstPerson) {
        Set<Integer> secret = new HashSet<>(); // 知晓秘密的人
        secret.add(0);
        secret.add(firstPerson);
        // 构建一个并查集
        UnionFind uf = new UnionFind(n);
        uf.union(0, firstPerson);
        // 对meetings 进行排序
        Arrays.sort(meetings, (a, b) -> a[2] - b[2]); // 时间的升序排序
        for (int i = 0; i < meetings.length; i++) {
            // 将同一个时间戳的会议进行并查集
            int currTime = meetings[i][2];
            Set<Integer> sameTime = new HashSet<>(); // 同一个时间戳的人员名单
            while (i < meetings.length && meetings[i][2] == currTime) {
                uf.union(meetings[i][0], meetings[i][1]);
                sameTime.add(meetings[i][0]);
                sameTime.add(meetings[i][1]);
                i++;
            }
            // 根据 sameTime 和 并查集 进行 secret 的更新
            for (int person : sameTime) {
                if (uf.find(person) == uf.find(0)) {
                    secret.add(person);
                } else {
                    // 需要把uf中的节点重置, 因为这些人的信息已经不需要了
                    uf.reset(person);
                }
            }
            i--; // 回退一个位置, 因为i++
        }
        // 将secret 转换成 list
        return secret.stream().toList();
    }

    private static class UnionFind {
        private int[] parent;

        public UnionFind(int size) {
            parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] == x) {
                return parent[x];
            } else {
                x = parent[x];
                return find(x);
            }
        }

        public void reset(int x) {
            parent[x] = x;
        }

        public void union(int x, int y) {
            int parentX = find(x);
            int parentY = find(y);
            if (parentX != parentY) {
                parent[Math.max(parentX, parentY)] = Math.min(parentX, parentY);
            }
        }
    }

}
