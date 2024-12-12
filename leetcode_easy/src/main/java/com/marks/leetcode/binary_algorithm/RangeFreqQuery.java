package com.marks.leetcode.binary_algorithm;

import java.util.*;

/**
 * <p>项目名称: LeetCode_2080 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/18 10:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class RangeFreqQuery {
    List<List<Integer>> all = new ArrayList<>();

    public RangeFreqQuery(int[] arr) {
        for (int i = 0; i <= 10000; i++) {
            all.add(new ArrayList<>());
        }
        for (int i = 0; i < arr.length; i++) {
            // 下标是按顺序加入的，所以是有序的，所以后面可以直接进行二分查找
            all.get(arr[i]).add(i);
        }
    }

    public int query(int left, int right, int value) {
        if (all.get(value).size() == 0) return 0;
        // 当前值对应的下标集合
        List<Integer> now = all.get(value);
        // 第一次二分找左端点下标
        int a = binarySearch(now, 0, now.size() - 1, left);
        // 不存在这样的左端点
        if (now.get(a) > right || now.get(a) < left) return 0;

        // 第二次二分，找右端点的下标
        int b = binarySearch(now, a, now.size() - 1, right);
        if (now.get(b) > right) {
            b--;
        }
        return b - a + 1;
    }

    // 找到大于等于target的第一个位置
    public int binarySearch(List<Integer> nums, int l , int r, int target) {
        while (l < r) {
            int mid = (r - l) / 2 + l;
            if (nums.get(mid) < target) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return l;
    }
}
