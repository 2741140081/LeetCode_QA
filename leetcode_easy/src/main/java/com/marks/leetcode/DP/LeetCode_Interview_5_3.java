package com.marks.leetcode.DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/16 16:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_Interview_5_3 {

    /**
     * @Description:
     * 给定一个32位整数 num，你可以将一个数位从0变为1。请编写一个程序，找出你能够获得的最长的一串1的长度。
     * @param num
     * @return int
     * @author marks
     * @CreateDate: 2025/10/16 16:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int reverseBits(int num) {
        int result;
        result = method_01(num);
        return result;
    }

    
    /**
     * @Description:
     * 1. int pre = 0; 记录上一次连续1的个数, int curr = 0; 记录当前连续1的个数
     * 2. 当遇到0, ans = Math.max(ans, curr + pre); pre = curr; curr = 0;
     * AC: 0ms/39.57MB
     * @param num 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/16 16:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int num) {
        int ans = 0;
        int pre = 0;
        int curr = 0;
        String s = Integer.toBinaryString(num);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                ans = Math.max(ans, curr + pre + 1);
                pre = curr;
                curr = 0;
            } else {
                curr++;
            }
        }
        if ((num & 1) == 1) {
            // 假设末位是1
            ans = Math.max(ans, curr + pre + 1);
        }
        return Math.min(ans, 32);
    }

}
