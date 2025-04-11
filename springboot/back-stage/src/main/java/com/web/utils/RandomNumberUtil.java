package com.web.utils;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机数生成工具类
 */
public class RandomNumberUtil {
    
    // 私有构造方法禁止实例化
    private RandomNumberUtil() {
        throw new AssertionError("工具类禁止实例化");
    }

    /**
     * 使用ThreadLocalRandom生成随机数（推荐）
     * @param max 最大值（包含）
     * @return 0-max范围内的随机整数
     */
    public static int generateRandom(int max) {
        validateMax(max);
        return ThreadLocalRandom.current().nextInt(max + 1);
    }

    /**
     * 使用传统Random类生成随机数
     * @param max 最大值（包含）
     * @return 0-max范围内的随机整数
     */
    public static int generateRandomLegacy(int max) {
        validateMax(max);
        return new Random().nextInt(max + 1);
    }

    /**
     * 使用SecureRandom生成安全随机数
     * @param max 最大值（包含）
     * @return 0-max范围内的安全随机整数
     */
    public static int generateSecureRandom(int max) {
        validateMax(max);
        try {
            return SecureRandom.getInstanceStrong().nextInt(max + 1);
        } catch (Exception e) {
            throw new RuntimeException("安全随机数生成失败", e);
        }
    }

    /**
     * 验证最大值有效性
     */
    private static void validateMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("最大值不能为负数");
        }
    }

    /**
     * 生成固定范围的随机数（0-100000）
     */
    public static int generateDefaultRange() {
        return generateRandom(100000);
    }
}
