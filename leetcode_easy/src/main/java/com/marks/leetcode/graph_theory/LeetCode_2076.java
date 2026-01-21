package com.marks.leetcode.graph_theory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2076 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/15 10:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2076 {

    /**
     * @Description:
     * 给你一个整数 n ，表示网络上的用户数目。每个用户按从 0 到 n - 1 进行编号。
     * 给你一个下标从 0 开始的二维整数数组 restrictions ，
     * 其中 restrictions[i] = [xi, yi] 意味着用户 xi 和用户 yi 不能 成为 朋友 ，不管是 直接 还是通过其他用户 间接 。
     *
     * 最初，用户里没有人是其他用户的朋友。给你一个下标从 0 开始的二维整数数组 requests 表示好友请求的列表，
     * 其中 requests[j] = [uj, vj] 是用户 uj 和用户 vj 之间的一条好友请求。
     * 如果 uj 和 vj 可以成为 朋友 ，那么好友请求将会 成功 。每个好友请求都会按列表中给出的顺序进行处理（即，requests[j] 会在 requests[j + 1] 前）。
     * 一旦请求成功，那么对所有未来的好友请求而言， uj 和 vj 将会 成为直接朋友 。
     *
     * 返回一个 布尔数组 result ，其中元素遵循此规则：如果第 j 个好友请求 成功 ，那么 result[j] 就是 true ；否则，为 false 。
     * 注意：如果 uj 和 vj 已经是直接朋友，那么他们之间的请求将仍然 成功 。
     * tips:
     * 2 <= n <= 1000
     * 0 <= restrictions.length <= 1000
     * restrictions[i].length == 2
     * 0 <= xi, yi <= n - 1
     * xi != yi
     * 1 <= requests.length <= 1000
     * requests[j].length == 2
     * 0 <= uj, vj <= n - 1
     * uj != vj
     * @param: n
     * @param: restrictions
     * @param: requests
     * @return boolean[]
     * @author marks
     * @CreateDate: 2026/01/15 10:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean[] friendRequests(int n, int[][] restrictions, int[][] requests) {
        boolean[] result;
        result = method_01(n, restrictions, requests);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：n = 5, restrictions = [[0,1],[1,2],[2,3]], requests = [[0,4],[1,2],[3,1],[3,4]]
     * 输出：[true,false,true,false]
     * 1. 这是问题是关于图的连通性问题, 所以可以使用并查集来处理
     * 2. 将 restrictions 转换成并查集，将 restrictions[i] = [xi, yi] 添加到并查集
     * 3. uf.isConnected(x, y) 判断 x 和 y 是否连通, true 表示连通(好友请求无法通过), false 表示不连通(请求可以通过)
     * 4. 由于 requests 是按顺序处理的, 所以处理 request[i] = [ui, vi] 时, uf.isConnected(ui, vi) 返回false. 此时表示申请朋友成功,
     * 既然成功, 那么 ui 和 vi 就是朋友, 需要将 ui 和 vi 添加到并查集中, 并且需要另外一个并查集存储ui 和 vi 的关系, ufIsFriend(ui, vi),
     * 否则后续判断会有误, 例如 r[i + 10] = [ui, vi], 因为之前已经是朋友状态, 所以申请必定是成功的, 但是 uf.isConnected(ui, vi) 返回 true.
     * 5. 需要两个并查集, 差不多分析完成, 写代码试试看
     * 6. 存在问题, 1-2, 2-3 不能成为朋友, 但是 1 - 3 可以成为朋友
     * 7. 查看官解, 直接遍历 restrictions, 来判断 当前的  requests[i] 是否成立
     * AC: 55ms/46.16MB
     * @param: n
     * @param: restrictions
     * @param: requests
     * @return boolean[]
     * @author marks
     * @CreateDate: 2026/01/15 10:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean[] method_01(int n, int[][] restrictions, int[][] requests) {
        UnionFind uf = new UnionFind(n); // already make friends, 已经是朋友
        int m = requests.length;
        boolean[] result = new boolean[m];
        for (int i = 0; i < m; i++) {
            int x = uf.find(requests[i][0]);
            int y = uf.find(requests[i][1]);
            if (x != y) {
                boolean flag = true;
                // 1. x != y 表明当前 x 和 y 还不能朋友, 还在申请状态中
                for (int[] item : restrictions) {
                    int xx = uf.find(item[0]);
                    int yy = uf.find(item[1]);
                    // 2. 什么情况下不能相连, xx - yy 是不能交朋友, 那么当 x = xx 并且 y = yy 或者 x = yy 并且 y = xx. 此时申请失败
                    if ((x == xx && y == yy) || (x == yy && y == xx)) {
                        result[i] = false;
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    // 3. flag == true, 表示申请成功, 需要将 x 和 y 添加到并查集中
                    uf.union(requests[i][0], requests[i][1]);
                    result[i] = true;
                }

            } else {
                // x == y
                result[i] = true;
            }

        }
        return result;
    }

    // 构建并查集
    class UnionFind {
        private int[] root;
        private int[] rank;
        private int count;
        public UnionFind(int n) {
            this.count = n;
            this.root = new int[n];
            this.rank = new int[n];
            for (int i = 0; i < n; i++) {
                root[i] = i;
                rank[i] = 1;
            }
        }

        public int find(int x) {
            if (root[x] == x) {
                return x;
            }
            return root[x] = find(root[x]);
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    root[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                    root[rootX] = rootY;
                } else {
                    root[rootY] = rootX;
                    rank[rootX] += 1;
                }
                count--;
            }
        }

        public boolean isConnected(int x, int y) {
            return find(x) == find(y);
        }
    }

}
