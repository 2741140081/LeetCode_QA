package com.marks.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_969 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/8 9:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_969 {

    /**
     * @Description:
     * 给你一个整数数组 arr ，请使用 煎饼翻转 完成对数组的排序。
     * 一次煎饼翻转的执行过程如下：
     * 选择一个整数 k ，1 <= k <= arr.length
     * 反转子数组 arr[0...k-1]（下标从 0 开始）
     * 例如，arr = [3,2,1,4] ，选择 k = 3 进行一次煎饼翻转，反转子数组 [3,2,1] ，得到 arr = [1,2,3,4] 。
     * 以数组形式返回能使 arr 有序的煎饼翻转操作所对应的 k 值序列。任何将数组排序且翻转次数在 10 * arr.length 范围内的有效答案都将被判断为正确。
     * @param: arr
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/05/08 9:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> pancakeSort(int[] arr) {
        List<Integer> result;
        result = method_01(arr);
        return result;
    }

    /**
     * @Description:
     * E1: 输入：[3,2,4,1]
     * 1. 将 3 -> [4,2,3,1] -> 4 -> [1,3,2,4] -> 2 -> [3,1,2,4] -> 3 -> [2,1,3,4] -> 2 -> [1,2,3,4]; res = [3,4,2,3,2]
     * 2. 这种使用的是暴力模拟, 即每次挑选一个未排序的最大元素, 反转该元素到 0 下标处, 然后反转 k = max - 1; 将该元素翻转到所在下标 max - 1 处完成排序
     * AC: 1ms/43.3MB
     * @param: arr
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/05/08 9:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int[] arr) {
        List<Integer> ans = new ArrayList<>();
        int n = arr.length;
        while (!checkIsOrder(arr)) {
            // 倒序找到下标值不等于arr[i] 的最右侧的下标
            int max = 0;
            for (int i = n - 1; i >= 0; i--) {
                if (arr[i] != i + 1) {
                    max = i;
                    break;
                }
            }
            // 找到 max 所在下标, 翻转到 0 处
            int index = -1;
            for (int i = 0; i < n; i++) {
                if (arr[i] == max + 1) {
                    index = i;
                    break;
                }
            }
            // 翻转 0 ~ index
            reverse(arr, 0, index);
            ans.add(index + 1);
            reverse(arr, 0, max);
            ans.add(max + 1);
        }
        return ans;
    }

    private void reverse(int[] arr, int left, int right) {
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }

    private boolean checkIsOrder(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] != i + 1) {
                return false;
            }
        }
        return true;
    }

}
