package com.marks.leetcode.array;

import java.util.Arrays;

public class LeetCode_453 {
    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6,7,8,9};
        System.out.println(minMove(nums));
    }

    /**
     * 给你一个长度为 n 的整数数组，每次操作将会使 n - 1 个元素增加 1 。返回让数组所有元素相等的最小操作次数。
     * 输入：nums = [1,2,3]
     * 输出：3
     * 解释：
     * 只需要3次操作（注意每次操作会增加两个元素的值）：
     * [1,2,3]  =>  [2,3,3]  =>  [3,4,3]  =>  [4,4,4]
     * [1,2,3,4] => [2,3,4,4] => [3,4,5,4] => [4,5,5,5] => [5,5,6,6] => [6,6,6,7] => [7,7,7,7]
     *
     * 思考：
     * 1.对数组进行排序，给length - 1的元素值+1
     * 2.再次对数组进行排序，如果nums[0] == nums[length-1] ==> 返回结果
     * 3.递归执行
     * -- 结果超时
     *
     * 另寻他法：
     * num = {1,2,3,4,5,6,······,n-1,n}
     * min = 1, max = n, length = n, sum = (n+1)*n/2
     * 令最小操作数 = ans, 即经过ans次后, 使得数组元素值为 numT {t,······,t};
     * 此时可以构建一个联系即：t = ans + min;
     * numT的sumT = n*t
     * 因为每次增加的值为 n-1, 增加了ans次
     * 得到第二个等式: n*t = sum + (n-1) * ans
     * 替换t
     * n * (ans + min) = sum + (n-1) * ans
     * ans = sum - n * min
     * 所以这是个数学问题, 将所有元素减去最小值的和即为ans
     *
     * @param nums
     * @return
     */
//    private static int minMove(int[] nums) {
//        int len = nums.length -1;
//        int count = 0;
//        Arrays.sort(nums);
//        if (nums[0] == nums[len]) {
//            return count;
//        }
//
//        while (nums[0] != nums[len]) {
//            for (int i = 0; i < nums.length-1; i++) {
//                nums[i] += 1;
//            }
//            Arrays.sort(nums);
//            count++;
//        }
//        System.out.println(nums[0]);
//        return count;
//    }

    private static int minMove(int[] nums) {
        Arrays.sort(nums);
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            ans += nums[i] - nums[0];
        }
        return ans;
    }
}
