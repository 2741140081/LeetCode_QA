package com.marks.leetcode.graph_theory;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_997 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/17 10:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_997 {

    /**
     * @Description:
     * 小镇里有 n 个人，按从 1 到 n 的顺序编号。传言称，这些人中有一个暗地里是小镇法官。
     * 如果小镇法官真的存在，那么：
     * 小镇法官不会信任任何人。
     * 每个人（除了小镇法官）都信任这位小镇法官。
     * 只有一个人同时满足属性 1 和属性 2 。
     * 给你一个数组 trust ，其中 trust[i] = [ai, bi] 表示编号为 ai 的人信任编号为 bi 的人。
     *
     * 如果小镇法官存在并且可以确定他的身份，请返回该法官的编号；否则，返回 -1 。
     * @param: n
     * @param: trust
     * @return int
     * @author marks
     * @CreateDate: 2025/12/17 10:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findJudge(int n, int[][] trust) {
        int result;
        result = method_01(n, trust);
        return result;
    }

    /**
     * @Description:
     * 1. 如果存在法官, 那么假设法官编号为 i, 法官 i 不信任任何人, 所以法官 i 的出度为0
     * 2. 所有人(不包括i) 都信任法官, 所以法官 i 的入度为 n - 1
     * 3. 构建一个入度和出度表, 遍历 trust, 构建入度和出度表
     * AC: 2ms/52.45MB
     * @param: n
     * @param: trust
     * @return int
     * @author marks
     * @CreateDate: 2025/12/17 10:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] trust) {
        int[] inDegree = new int[n + 1];
        int[] outDegree = new int[n + 1];
        for (int[] edge : trust) {
            outDegree[edge[0]]++;
            inDegree[edge[1]]++;
        }
        int ans = -1;
        for (int i = 1; i <= n; i++) {
            if (outDegree[i] == 0 && inDegree[i] == n - 1) {
                ans = i;
                break;
            }
        }
        return ans;
    }

}
