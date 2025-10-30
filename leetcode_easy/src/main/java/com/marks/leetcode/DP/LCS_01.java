package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCS_01 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/29 16:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCS_01 {

    /***
     * @Description:
     * 小扣打算给自己的 VS code 安装使用插件，初始状态下带宽每分钟可以完成 1 个插件的下载。假定每分钟选择以下两种策略之一:
     *
     * 使用当前带宽下载插件
     * 将带宽加倍（下载插件数量随之加倍）
     * 请返回小扣完成下载 n 个插件最少需要多少分钟。
     *
     * 注意：实际的下载的插件数量可以超过 n 个
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/10/29 16:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int leastMinutes(int n) {
        int result;
        result = method_01(n);
        return result;
    }


    /***
     * @Description:
     * 1. 2^x > n => x 为最大的分钟数
     * AC: 2ms/39.48MB
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/10/29 16:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        int x = 0;
        while (Math.pow(2, x) <= n) {
            x++;
        }
        int ans = x + 1;
        int sum = 0;
        while (x >= 0) {
            int count = 1 << x;
            int t = x;
            sum = 0;
            while (sum < n) {
                sum += count;
                t++;
            }
            ans = Math.min(ans, t);
            x--;
        }
        return ans;
    }
}
