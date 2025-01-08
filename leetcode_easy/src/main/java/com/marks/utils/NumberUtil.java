package com.marks.utils;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/7 17:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class NumberUtil {

    /**
     * @Description: 两数的最大公约数: Calculate the greatest common divisor of x and y
     * @param x
     * @param y
     * @return int
     * @author marks
     * @CreateDate: 2025/1/7 17:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static int gcd(int x, int y) {
        return y == 0 ? y : gcd(y, x % y);
    }

    /**
     * @Description: 最小公倍数: Calculate the least common multiple of x and y
     * @param x
     * @param y
     * @return int
     * @author marks
     * @CreateDate: 2025/1/7 17:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static int lcm(int x, int y) {
        return x * y / gcd(x, y);
    }

    /**
     * @Description: 获取数位最大值: Get digital maximum value
     * @param num
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 10:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static int getMaxDigital(int num) {
        int max = 0;
        int mod = 10;
        while (num > 0) {
            max = Math.max(max, num % mod);
            num = num / 10;
        }
        return max;
    }

    /**
     * @Description: 获取数位和: Get digital sum
     * @param num
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 10:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static int getDigitalSum(int num) {
        int sum = 0;
        int mod = 10;
        while (num > 0) {
            sum = sum += num % mod;
            num = num / 10;
        }
        return sum;
    }

    /**
     * @Description: 通过字符串reverse()方法的反转int num, 并删除前导0
     * @param num
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 17:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static int revByString(int num) {
        String str = Integer.toString(num);
        StringBuilder str_rev = new StringBuilder(str).reverse();
        while (str_rev.length() > 1 && str_rev.charAt(0) == '0') {
            str_rev.deleteCharAt(0);
        }
        return Integer.parseInt(String.valueOf(str_rev));
    }

    /**
     * @Description: 反转int num
     * @param num
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 17:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private static int rev(int num) {
        int rev = 0;
        while (num > 0) {
            rev = rev * 10 + num % 10;
            num /= 10;
        }
        return rev;
    }
}
