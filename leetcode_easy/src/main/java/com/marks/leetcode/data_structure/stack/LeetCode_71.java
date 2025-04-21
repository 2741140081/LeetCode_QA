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
 * @date 2025/1/15 14:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_71 {
    /**
     * @Description:
     *
     * @param path
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/15 14:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String simplifyPath(String path) {
        String result;
        result = method_01(path);
        result = method_02(path);
        return result;
    }

    /**
     * @Description:
     * AC:3ms/42.66MB
     * @param path
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/15 15:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_02(String path) {
        String[] names = path.split("/");
        Deque<String> stack = new ArrayDeque<>();

        for (String name : names) {
            if ("..".equals(name)) {
                if (!stack.isEmpty()) {
                    stack.poll(); // 弹出当前的目录
                }
            }else if (name.length() > 0 && !".".equals(name)) {
                stack.push(name);
            }
        }
        StringBuilder ans = new StringBuilder();
        if (stack.isEmpty()) {
            ans.append("/"); // 添加根目录
        } else {
            while (!stack.isEmpty()) {
                ans.append("/");
                ans.append(stack.pollLast());
            }
        }
        return ans.toString();
    }

    /**
     * @Description:
     * 0, 1, 2, 3, 4
     * / a / / b
     * i = 2, i+1 true, i = 3, 3 + 1 != '/', i = 3, i++, i = 4
     * WA: 248 / 262 , 不太想继续分类下去了, 看了下官解, 直接String.split("/") 更简单
     * @param path
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/15 14:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String path) {
        Deque<Character> stack = new ArrayDeque<>();
        int n = path.length();
        for (int i = 0; i < n; i++) {
            char curr_ch = path.charAt(i);
            switch (curr_ch) {
                case '/':
                    if (!stack.isEmpty() && stack.peek() == '/') {
                        break;
                    }
                    stack.push(curr_ch);
                    break;
                case '.':
                    int count = 1;
                    int index = i + 1;
                    while (index < n && path.charAt(index) == '.') {
                        index++;
                        count++;
                    }
                    if (count == 1) {
                        if (stack.size() > 1) {
                            stack.poll(); // 弹出 '/', 但是不能弹出根目录
                        }
                    } else if (count == 2) {
                        if (stack.size() > 1) {
                            stack.poll(); // 弹出 '/', 但是不能弹出根目录
                        }
                        // 需要弹到上一层目录/a/b/.. , 我们需要先弹出/, 再弹b, 最后再弹 /, 注意不要弹出根目录
                        while (stack.peek() != '/') {
                            stack.poll();
                        }
                        if (stack.size() > 1) {
                            stack.poll(); // 弹出 '/', 但是不能弹出根目录
                        }
                    } else {
                        for (int j = 0; j < count; j++) {
                            stack.push('.');
                        }
                    }
                    i += count - 1;
                    break;
                default:
                    stack.push(curr_ch);
                    break;
            }
        }

        StringBuilder ans = new StringBuilder();
        while (stack.size() > 1 && stack.peek() == '/') {
            stack.poll();
        }
        while (!stack.isEmpty()) {
            ans.append(stack.poll());
        }
        return ans.reverse().toString();
    }
}
