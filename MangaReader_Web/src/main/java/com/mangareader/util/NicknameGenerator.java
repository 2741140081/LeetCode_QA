package com.mangareader.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机昵称生成器
 * <p>
 * 生成格式：nick_ + 18位随机数字，例如 nick_384729105837261049
 * </p>
 *
 * @author marks
 * @version v1.0
 */
public final class NicknameGenerator {

    private static final String NICK_PREFIX = "nick_";
    private static final long NICK_MAX = 1_000_000_000_000_000_000L;

    private NicknameGenerator() {}

    /**
     * 生成随机昵称
     *
     * @return 格式为 nick_ + 18位随机数字的字符串
     */
    public static String generate() {
        long randomNum = ThreadLocalRandom.current().nextLong(NICK_MAX);
        return NICK_PREFIX + String.format("%018d", randomNum);
    }
}
