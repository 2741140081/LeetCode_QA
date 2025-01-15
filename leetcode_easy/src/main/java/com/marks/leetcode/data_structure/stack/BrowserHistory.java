package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: 设计浏览器历史记录 </p>
 * <p>文件名称: LeetCode_1472 </p>
 * <p>描述:
 * 你有一个只支持单个标签页的 浏览器 ，最开始你浏览的网页是 homepage ，你可以访问其他的网站 url ，也可以在浏览历史中后退 steps 步或前进 steps 步。
 * 请你实现 BrowserHistory 类：
 * BrowserHistory(string homepage) ，用 homepage 初始化浏览器类。
 * void visit(string url) 从当前页跳转访问 url 对应的页面  。执行此操作会把浏览历史前进的记录全部删除。
 * string back(int steps) 在浏览历史中后退 steps 步。如果你只能在浏览历史中后退至多 x 步且 steps > x ，那么你只后退 x 步。请返回后退 至多 steps 步以后的 url 。
 * string forward(int steps) 在浏览历史中前进 steps 步。如果你只能在浏览历史中前进至多 x 步且 steps > x ，那么你只前进 x 步。请返回前进 至多 steps步以后的 url 。 </p>
 * <p>AC: 63ms/50.68MB</p>
 * @author marks
 * @version v1.0
 * @date 2025/1/15 10:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class BrowserHistory {
    private Deque<String> curr_stack = new ArrayDeque<>();
    private Deque<String> forword_stack = new ArrayDeque<>();

    public BrowserHistory(String homepage) {
        curr_stack.push(homepage);
        forword_stack.clear();
    }

    public void visit(String url) {
        curr_stack.push(url);
        forword_stack.clear();
    }

    public String back(int steps) {
        while (curr_stack.size() > 1 && steps > 0) {
            steps--;
            String webSite = curr_stack.poll();
            forword_stack.push(webSite);
        }
        String ans = curr_stack.peek();
        return ans;
    }

    public String forward(int steps) {
        while (!forword_stack.isEmpty() && steps > 0) {
            steps--;
            String webSite = forword_stack.poll();
            curr_stack.push(webSite);
        }
        String ans = curr_stack.peek();
        return ans;
    }
}
