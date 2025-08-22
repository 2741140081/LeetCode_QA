package com.marks.leetcode.backtrack;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/20 10:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1601 {

    
    /**
     * @Description:
     * 我们有 n 栋楼，编号从 0 到 n - 1 。每栋楼有若干员工。由于现在是换楼的季节，部分员工想要换一栋楼居住。
     * 给你一个数组 requests ，其中 requests[i] = [fromi, toi] ，表示一个员工请求从编号为 fromi 的楼搬到编号为 toi 的楼。
     *
     * 一开始 所有楼都是满的，所以从请求列表中选出的若干个请求是可行的需要满足 每栋楼员工净变化为 0 。
     * 意思是每栋楼 离开 的员工数目 等于 该楼 搬入 的员工数数目。
     * 比方说 n = 3 且两个员工要离开楼 0 ，一个员工要离开楼 1 ，一个员工要离开楼 2 ，如果该请求列表可行，应该要有两个员工搬入楼 0 ，一个员工搬入楼 1 ，一个员工搬入楼 2 。
     *
     * 请你从原请求列表中选出若干个请求，使得它们是一个可行的请求列表，并返回所有可行列表中最大请求数目。
     *
     * tips:
     * 1 <= n <= 20
     * 1 <= requests.length <= 16
     * requests[i].length == 2
     * 0 <= fromi, toi < n
     * @param n 
     * @param requests
     * @return int
     * @author marks
     * @CreateDate: 2025/8/20 10:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumRequests(int n, int[][] requests) {
        int result;
        result = method_01(n, requests);
        return result;
    }

    
    /**
     * @Description:
     * E1:
     * 输入：n = 5, requests = [[0,1],[1,0],[0,1],[1,2],[2,0],[3,4]]
     * 输出：5
     * 1. int[] degree = new int[n]; 存储每栋楼员工净变化, 当 index == n 时, 如果 degree[i] 全部为 0, 则 ans = Math.max(ans, sum);
     * sum 为请求数目。
     * 2. 对于 from = request[index][0], to = request[index][1], degree[from]--, degree[to]++;
     * 3. 回溯时, degree[from]++, degree[to]--;
     *
     * AC: 21ms(53.33%)/40.57MB(53.33%)
     * @param n 
     * @param requests 
     * @return int
     * @author marks
     * @CreateDate: 2025/8/20 10:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int ans;
    private int len;
    private int method_01(int n, int[][] requests) {
        ans = 0;
        len = requests.length;
        int[] degree = new int[n]; // 每栋楼员工净变化, 初始值为0
        backtrack(requests, 0, degree, 0);
        return ans;
    }

    private void backtrack(int[][] requests, int index, int[] degree, int sum) {
        if (index == len) {
            boolean flag = true;
            for (int i = 0; i < degree.length; i++) {
                if (degree[i] != 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                ans = Math.max(ans, sum);
            }
            return;
        }
        int from = requests[index][0];
        int to = requests[index][1];
        degree[from]--;
        degree[to]++;
        backtrack(requests, index + 1, degree, sum + 1);
        degree[from]++;
        degree[to]--;
        backtrack(requests, index + 1, degree, sum);
    }
}
