package com.marks.leetcode.data_structure.heap_advance;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/12 17:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_33 {

    /**
     * @Description:
     * 给定 N 个无限容量且初始均空的水缸，每个水缸配有一个水桶用来打水，第 i 个水缸配备的水桶容量记作 bucket[i]。小扣有以下两种操作：
     *
     * 升级水桶：选择任意一个水桶，使其容量增加为 bucket[i]+1
     * 蓄水：将全部水桶接满水，倒入各自对应的水缸
     * 每个水缸对应最低蓄水量记作 vat[i]，返回小扣至少需要多少次操作可以完成所有水缸蓄水要求。
     *
     * 注意：实际蓄水量 达到或超过 最低蓄水量，即完成蓄水要求。
     * @param bucket
     * @param vat
     * @return int
     * @author marks
     * @CreateDate: 2025/2/12 17:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int storeWater(int[] bucket, int[] vat) {
        int result;
        result = method_01(bucket, vat);
        return result;
    }

    private int method_01(int[] bucket, int[] vat) {
        return 0;
    }
}
