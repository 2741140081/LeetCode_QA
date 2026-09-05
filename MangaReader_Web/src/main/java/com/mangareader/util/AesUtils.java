package com.mangareader.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AES-128-CBC 加解密工具类
 *
 * @author marks
 * @version v1.0
 */
public final class AesUtils {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    /** IV 与密钥相同（简化方案，生产环境建议使用随机 IV） */
    private static final int IV_LENGTH = 16;

    private AesUtils() {}

    /**
     * AES 加密
     *
     * @param plainText 明文
     * @param secretKey 密钥（必须为 16 字节）
     * @return Base64 编码的密文
     */
    public static String encrypt(String plainText, String secretKey) {
        if (plainText == null || plainText.isEmpty()) {
            return plainText;
        }
        try {
            byte[] keyBytes = normalizeKey(secretKey);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM);
            IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("AES 加密失败", e);
        }
    }

    /**
     * AES 解密
     *
     * @param cipherText Base64 编码的密文
     * @param secretKey  密钥（必须为 16 字节）
     * @return 解密后的明文
     */
    public static String decrypt(String cipherText, String secretKey) {
        if (cipherText == null || cipherText.isEmpty()) {
            return cipherText;
        }
        try {
            byte[] keyBytes = normalizeKey(secretKey);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM);
            IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decoded = Base64.getDecoder().decode(cipherText);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            // 解密失败时返回原文（可能是未加密的数据）
            return cipherText;
        }
    }

    /**
     * 将密钥规范化为 16 字节
     */
    private static byte[] normalizeKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] normalized = new byte[IV_LENGTH];
        System.arraycopy(keyBytes, 0, normalized, 0, Math.min(keyBytes.length, IV_LENGTH));
        return normalized;
    }
}
