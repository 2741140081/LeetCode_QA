package com.marks.leetcode.array_hard;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCP_53 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/10 16:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_53 {

    /**
     * @Description:
     * 各位勇者请注意，力扣太空城发布陨石雨红色预警。
     * 太空城中的一些舱室将要受到陨石雨的冲击，这些舱室按照编号 0 ~ N 的顺序依次排列。
     * 为了阻挡陨石损毁舱室，太空城可以使用能量展开防护屏障，具体消耗如下：
     * 选择一个舱室开启屏障，能量消耗为 2
     * 选择相邻两个舱室开启联合屏障，能量消耗为 3
     * 对于已开启的一个屏障，多维持一时刻，能量消耗为 1
     * 已知陨石雨的影响范围和到达时刻，time[i] 和 position[i] 分别表示该陨石的到达时刻和冲击位置。
     * 请返回太空舱能够守护所有舱室所需要的最少能量。
     * 注意：
     * 同一时间，一个舱室不能被多个屏障覆盖
     * 陨石雨仅在到达时刻对冲击位置处的舱室有影响
     * tips:
     * 1 <= time.length == position.length <= 500
     * 1 <= time[i] <= 5
     * 0 <= position[i] <= 100
     * @param: time
     * @param: position
     * @return int
     * @author marks
     * @CreateDate: 2026/09/10 16:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int defendSpaceCity(int[] time, int[] position) {
        int result;
        result = method_01(time, position);
        return result;
    }

    /**
     * @Description:
     * 1. 感觉上应该是动态规划
     * todo
     * @param: time
     * @param: position
     * @return int
     * @author marks
     * @CreateDate: 2026/09/10 16:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] time, int[] position) {

        return 0;
    }

}
