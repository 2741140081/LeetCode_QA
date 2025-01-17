package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/17 16:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2211 {
    public int countCollisions(String directions) {
        int result;
        result = method_01(directions);
        return result;
    }

    class CarInfo {
        private char ch;
        private int count;

        public CarInfo(char ch, int count) {
            this.ch = ch;
            this.count = count;
        }
    }

    /**
     * @Description:
     * L, R, S
     * E1:
     * directions = "RLRSLL"
     * AC:32ms/44.99MB
     * @param directions
     * @return int
     * @author marks
     * @CreateDate: 2025/1/17 16:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String directions) {
        Deque<CarInfo> stack = new ArrayDeque<>();
        int n = directions.length();
        int ans = 0;
        int left = 0;
        int right = n - 1;
        while (left < n && directions.charAt(left) == 'L' ) {
            left++;
        }
        while (right >= 0 && directions.charAt(right) == 'R') {
            right--;
        }
        if (left < right) {
            stack.push(new CarInfo(directions.charAt(left), 1));
        } else {
            return 0;
        }

        /*
        stack 中初始存储的是 R / S
        d.charAt(right) 值为 L / S
         */
        for (int i = left + 1; i <= right; i++) {
            char c = directions.charAt(i);
            switch (c) {
                case 'R':
                    if (stack.peek().ch == c) {
                        stack.peek().count++;
                    } else {
                        stack.push(new CarInfo(c, 1));
                    }
                    break;
                case 'L':
                    ans++;
                    if (stack.peek().ch == 'R') {
                        ans += stack.poll().count;
                        stack.push(new CarInfo('S', 1));
                    }
                    break;
                case 'S':
                    if (stack.peek().ch == 'R') {
                        ans += stack.poll().count;
                        stack.push(new CarInfo('S', 1));
                    }
                    break;
            }
        }
        return ans;
    }
}
