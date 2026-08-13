package com.marks.leetcode.all_str;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_831 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/22 15:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_831 {

    /**
     * @Description:
     * 给你一条个人信息字符串 s ，可能表示一个 邮箱地址 ，也可能表示一串 电话号码 。
     * 返回按如下规则 隐藏 个人信息后的结果：
     * 电子邮件地址：
     * 一个电子邮件地址由以下部分组成：
     * 一个 名字 ，由大小写英文字母组成，后面跟着
     * 一个 '@' 字符，后面跟着
     * 一个 域名 ，由大小写英文字母和一个位于中间的 '.' 字符组成。'.' 不会是域名的第一个或者最后一个字符。
     * 要想隐藏电子邮件地址中的个人信息：
     * 名字 和 域名 部分的大写英文字母应当转换成小写英文字母。
     * 名字 中间的字母（即，除第一个和最后一个字母外）必须用 5 个 "*****" 替换。
     * 电话号码：
     * 一个电话号码应当按下述格式组成：
     * 电话号码可以由 10-13 位数字组成
     * 后 10 位构成 本地号码
     * 前面剩下的 0-3 位，构成 国家代码
     * 利用 {'+', '-', '(', ')', ' '} 这些 分隔字符 按某种形式对上述数字进行分隔
     * 要想隐藏电话号码中的个人信息：
     * 移除所有 分隔字符
     * 隐藏个人信息后的电话号码应该遵从这种格式：
     * "***-***-XXXX" 如果国家代码为 0 位数字
     * "+*-***-***-XXXX" 如果国家代码为 1 位数字
     * "+**-***-***-XXXX" 如果国家代码为 2 位数字
     * "+***-***-***-XXXX" 如果国家代码为 3 位数字
     * "XXXX" 是最后 4 位 本地号码
     *
     * tips:
     * s 是一个 有效 的电子邮件或者电话号码
     * 如果 s 是一个电子邮件：
     * 8 <= s.length <= 40
     * s 是由大小写英文字母，恰好一个 '@' 字符，以及 '.' 字符组成
     * 如果 s 是一个电话号码：
     * 10 <= s.length <= 20
     * s 是由数字、空格、字符 '('、')'、'-' 和 '+' 组成
     * @param: s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/06/22 15:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String maskPII(String s) {
        String result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * 1. 判断是邮件地址还是电话号码
     * AC: 6ms/48.46MB
     * @param: s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/06/22 15:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s) {
        if (s.indexOf('@') == -1) {
            // 电话号码;
            return dealPhoneNumber(s);
        } else {
            // 邮件地址;
            return dealEmail(s);
        }
    }

    private String dealEmail(String s) {
        // 将邮件地址进行分割
        String[] split = s.split("@");
        String name = split[0];
        // 将name转换为小写字母
        name = name.toLowerCase();
        String domain = split[1];
        domain = domain.toLowerCase();
        return name.charAt(0) +
                // 添加 5 个 *
                "*".repeat(5) +
                // 添加name 的最后一个字母
                name.charAt(name.length() - 1) +
                '@' +
                domain;
    }

    private String dealPhoneNumber(String s) {
        // 移除所有分隔符, 保留数字
        s = s.replaceAll("[^0-9]", "");
        // 获取最后四位
        String lastFour = s.substring(s.length() - 4);
        // 后缀是 -*** 重复两次 + lastFour
        String suffix = "***-".repeat(2) + lastFour;
        // 构建一个数组, 存储 10 ~ 13 4 种不同的前缀
        String[] prefix = {"", "+*-", "+**-", "+***-"};
        return prefix[s.length() - 10] + suffix;
    }

}
