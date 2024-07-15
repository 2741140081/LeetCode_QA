package com.marks.leetcode.array;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LeetCode_20 {
    public static void main(String[] args) {
        String s = "]]]]";
        System.out.println(isValid(s));
    }

    private static boolean isValid(String s) {
        int n = s.length();
        if(n % 2 == 1){
            return  false;
        }
        Map<Character, Character> map = new HashMap<Character, Character>() {{
            // 将 })] 作为key
            put('}', '{');
            put(']', '[');
            put(')', '(');
        }};
        // 新建一个栈
        LinkedList<Character> stack = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            // 如果c是 })], 则判断， 否则说明是({[ , 直接入栈
            if(map.containsKey(c)){
                // stack.peek() 获取栈顶元素
                if(stack.isEmpty() || stack.peek() != map.get(c)){
                    return false;
                }
                // 将栈顶移除(先进后出，栈顶是最接近 c 的左括号)
                stack.pop();
            }else{
                // 说明c是({[ , 直接入栈
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }
}
