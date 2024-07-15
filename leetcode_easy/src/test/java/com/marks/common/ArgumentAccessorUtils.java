package com.marks.common;

import org.junit.jupiter.params.aggregator.ArgumentsAccessor;

public class ArgumentAccessorUtils {
    public static int[] ArgumentAccessorToIntArray(ArgumentsAccessor data, StringBuffer sb) {
        sb.append("[");
        int sumSize = data.size();
        int[] nums = new int[sumSize];
        for (int i = 0; i < nums.length; i++) {
            if (i == nums.length -1) {
                sb.append(data.get(i)+ "]");
            }else {
                sb.append(data.get(i)+ ", ");
            }

            nums[i] =Integer.parseInt((String) data.get(i));
        }
        return nums;
    }

    public static long[] ArgumentAccessorToLongArray(ArgumentsAccessor data, StringBuffer sb) {
        sb.append("[");
        int sumSize = data.size();
        long[] nums = new long[sumSize];
        for (int i = 0; i < nums.length; i++) {
            if (i == nums.length -1) {
                sb.append(data.get(i)+ "]");
            }else {
                sb.append(data.get(i)+ ", ");
            }

            nums[i] =Long.parseLong((String) data.get(i));
        }
        return nums;
    }
}
