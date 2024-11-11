package com.marks.leetcode.double_pointer.single_sequence;

import java.util.ArrayList;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/8 17:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2570 {
    ArrayList<ArrayList<Integer>> list = new ArrayList<>();
    /**
     * @Description: [
     * 给你两个 二维 整数数组 nums1 和 nums2.
     *
     * nums1[i] = [idi, vali] 表示编号为 idi 的数字对应的值等于 vali 。
     * nums2[i] = [idi, vali] 表示编号为 idi 的数字对应的值等于 vali 。
     * 每个数组都包含 互不相同 的 id ，并按 id 以 递增 顺序排列。
     *
     * 请你将两个数组合并为一个按 id 以递增顺序排列的数组，并符合下述条件：
     *
     * 只有在两个数组中至少出现过一次的 id 才能包含在结果数组内。
     * 每个 id 在结果数组中 只能出现一次 ，并且其对应的值等于两个数组中该 id 所对应的值求和。如果某个数组中不存在该 id ，则认为其对应的值等于 0 。
     * 返回结果数组。返回的数组需要按 id 以递增顺序排列。
     * ]
     * @param nums1 
     * @param nums2
     * @return int[][]
     * @author marks
     * @CreateDate: 2024/11/11 10:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] mergeArrays(int[][] nums1, int[][] nums2) {
        int[][] result;
        result = method_01(nums1, nums2);
        result = method_02(nums1, nums2);
        return result;
    }

    /**
     * @Description: [
     * 查看题解: 归并排序
     * AC:1ms/44.34MB
     * ]
     * @param nums1
     * @param nums2
     * @return int[][]
     * @author marks
     * @CreateDate: 2024/11/11 10:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_02(int[][] nums1, int[][] nums2) {
        ArrayList<int[]> arrayList = new ArrayList<>();
        for (int i = 0, j = 0; i < nums1.length || j < nums2.length;) {
            if (i >= nums1.length) {
                // nums2 存在剩余
                arrayList.add(nums2[j++]);
            } else if (j >= nums2.length) {
                // nums1 存在剩余
                arrayList.add(nums1[i++]);
            } else if (nums1[i][0] > nums2[j][0]) {
                arrayList.add(nums2[j++]);
            } else if (nums1[i][0] < nums2[j][0]) {
                arrayList.add(nums1[i++]);
            } else if (nums1[i][0] == nums2[j][0]) {
                arrayList.add(new int[]{nums1[i][0], nums1[i][1] + nums2[j][1]});
                i++;
                j++;
            }
        }
        int[][] res = arrayList.toArray(new int[arrayList.size()][2]);

        return res;
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums1 = [[1,2],[2,3],[4,5]], nums2 = [[1,4],[3,2],[4,1]]
     * 输出：[[1,6],[2,3],[3,2],[4,6]]
     *
     * AC:5ms/44.20MB
     * ]
     * @param nums1
     * @param nums2
     * @return int[][]
     * @author marks
     * @CreateDate: 2024/11/8 17:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_01(int[][] nums1, int[][] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        int left = 0;
        int right = 0;
        while (left < n && right < m) {
            if (nums1[left][0] == nums2[right][0]) {
                addList(new int[]{nums1[left][0], nums1[left][1] + nums2[right][1]});
                left++;
                right++;
            } else if (nums1[left][0] > nums2[right][0]) {
                addList(nums2[right]);
                right++;
            } else {
                addList(nums1[left]);
                left++;
            }
        }

        while (left < n) {
            addList(nums1[left]);
            left++;
        }

        while (right < m) {
            addList(nums2[right]);
            right++;
        }

        int size = list.size();
        int[][] ans = new int[size][2];
        for (int i = 0; i < size; i++) {
            ArrayList<Integer> arrayList = list.get(i);
            ans[i] = arrayList.stream().mapToInt(Integer::intValue).toArray();
        }
        return ans;
    }

    private void addList(int[] arr) {
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(arr[0]);
        temp.add(arr[1]);
        list.add(temp);
    }
}
