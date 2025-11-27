package com.marks.leetcode.binary_algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: SnapshotArray </p>
 * <p>描述: LeetCode_1146 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/26 15:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class SnapshotArray {
    private int snap_id = 0;
    private Map<Integer, List<int[]>> map;

    
    
    /**
     * @Description:
     * 实现支持下列接口的「快照数组」- SnapshotArray：
     * - 初始化一个与指定长度相等的 类数组 的数据结构。初始时，每个元素都等于 0。
     * 1. 确定快照数组的map的key是什么? 我觉得是 快照id, 即 snap_id;
     * 2. 不行, 不能用snap_id 作为key, 而是使用index作为map 的 key
     * AC: 62ms/129.31MB
     * @param: length
     * @return 
     * @author marks
     * @CreateDate: 2025/11/26 15:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public SnapshotArray(int length) {
        map = new HashMap<>();
        for (int i = 0; i < length; i++) {
            List<int[]> list = new ArrayList<>();
            map.put(i, list);
        }
    }

    /**
     * @Description:
     * - 会将指定索引 index 处的元素设置为 val。
     * @param: index
     * @param: val
     * @return void
     * @author marks
     * @CreateDate: 2025/11/26 15:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void set(int index, int val) {
        List<int[]> currList = map.get(index);
        currList.add(new int[]{snap_id, val});
    }

    /**
     * @Description:
     *  - 获取该数组的快照，并返回快照的编号 snap_id（快照号是调用 snap() 的总次数减去 1）。
     * @return int
     * @author marks
     * @CreateDate: 2025/11/26 15:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int snap() {
        int prev_snap_id = snap_id;
        snap_id++;
        return prev_snap_id;
    }

    /**
     * @Description:
     *  - 根据指定的 snap_id 选择快照，并返回该快照指定索引 index 的值。
     * @param: index
     * @param: snap_id
     * @return int
     * @author marks
     * @CreateDate: 2025/11/26 15:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int get(int index, int snap_id) {
        List<int[]> currList = map.get(index);
        int target = 0;
        int left = 0, right = currList.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (currList.get(mid)[0] <= snap_id) {
                target = currList.get(mid)[1];
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return target;
    }

}
