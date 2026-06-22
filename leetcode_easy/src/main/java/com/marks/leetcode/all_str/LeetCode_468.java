package com.marks.leetcode.all_str;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_468 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/18 14:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_468 {

    /**
     * @Description:
     * 给定一个字符串 queryIP。如果是有效的 IPv4 地址，返回 "IPv4" ；
     * 如果是有效的 IPv6 地址，返回 "IPv6" ；如果不是上述类型的 IP 地址，返回 "Neither" 。
     * 有效的IPv4地址 是 “x1.x2.x3.x4” 形式的IP地址。 其中 0 <= xi <= 255 且 xi 不能包含 前导零。
     * 例如: “192.168.1.1” 、 “192.168.1.0” 为有效IPv4地址， “192.168.01.1” 为无效IPv4地址; “192.168.1.00” 、 “192.168@1.1” 为无效IPv4地址。
     *
     * 一个有效的IPv6地址 是一个格式为“x1:x2:x3:x4:x5:x6:x7:x8” 的IP地址，其中:
     * 1 <= xi.length <= 4
     * xi 是一个 十六进制字符串 ，可以包含数字、小写英文字母( 'a' 到 'f' )和大写英文字母( 'A' 到 'F' )。
     * 在 xi 中允许前导零。
     * 例如 "2001:0db8:85a3:0000:0000:8a2e:0370:7334" 和 "2001:db8:85a3:0:0:8A2E:0370:7334" 是有效的 IPv6 地址，
     * 而 "2001:0db8:85a3::8A2E:037j:7334" 和 "02001:0db8:85a3:0000:0000:8a2e:0370:7334" 是无效的 IPv6 地址。
     *
     * tips:
     * queryIP 仅由英文字母，数字，字符 '.' 和 ':' 组成。
     * @param: queryIP
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/06/18 14:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String validIPAddress(String queryIP) {
        String result;
        result = method_01(queryIP);
        return result;
    }

    /**
     * @Description:
     * 1. 不考虑ipv6的压缩情况, 将当前查询的地址分为ipv4和ipv6
     * 2. 包含 '.' 的字符串为ipv4, 包含 ':' 的字符串为ipv6, 如果两者都包含或者都不包含，则返回 "Neither"
     * AC: 5ms/42.57MB
     * @param: queryIP
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/06/18 14:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String queryIP) {
        boolean isIpv4 = queryIP.contains(".");
        boolean isIpv6 = queryIP.contains(":");
        if (isIpv4 && !isIpv6) {
            // 判断 ipv4
            String[] ipArray = queryIP.split("\\.", -1);
            if (ipArray.length == 4) {
                for (String ip : ipArray) {
                    if (ip.length() > 3 || ip.isEmpty()) {
                        return "Neither";
                    }
                    for (char c : ip.toCharArray()) {
                        if (!Character.isDigit(c)) {
                            return "Neither";
                        }
                    }
                    // 判断是否包含前导零
                    if (ip.charAt(0) == '0' && ip.length() > 1) {
                        return "Neither";
                    }
                    int ipInt = Integer.parseInt(ip);
                    if (ipInt < 0 || ipInt > 255) {
                        return "Neither";
                    }
                }
                return "IPv4";
            }
        } else if (!isIpv4 && isIpv6) {
            // 判断 ipv6
            String[] ipArray = queryIP.split(":", -1);
            if (ipArray.length == 8) {
                for (String ip : ipArray) {
                    if (ip.length() > 4 || ip.isEmpty()) {
                        return "Neither";
                    }
                    for (char c : ip.toCharArray()) {
                        if (!Character.isDigit(c) && !Character.isLetter(c)) {
                            return "Neither";
                        }
                    }
                    if (!ip.matches("[0-9a-fA-F]+")) {
                        return "Neither";
                    }
                }
                return "IPv6";
            }
        }

        return "Neither";
    }

}
