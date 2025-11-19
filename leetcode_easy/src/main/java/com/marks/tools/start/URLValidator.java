package com.marks.tools.start;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: URLValidator </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/19 11:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class URLValidator {

    /**
     * 验证URL是否有效
     * @param urlString 要验证的URL地址
     * @param timeout 超时时间（毫秒）
     * @return true表示URL有效，false表示无效
     */
    public static boolean isUrlValid(String urlString, int timeout) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法为HEAD，只获取头部信息，不下载内容
            connection.setRequestMethod("HEAD");

            // 设置连接和读取超时
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);

            // 获取响应码
            int responseCode = connection.getResponseCode();

            // 2xx和3xx状态码通常表示有效
            return (responseCode >= 200 && responseCode < 400);

        } catch (UnknownHostException e) {
            // 域名无法解析
            System.out.println("域名无法解析: " + e.getMessage());
            return false;
        } catch (IOException e) {
            // 连接失败或其他IO异常
            System.out.println("连接失败: " + e.getMessage());
            return false;
        } catch (Exception e) {
            // 其他异常，如URL格式错误
            System.out.println("URL格式错误: " + e.getMessage());
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * 重载方法，使用默认超时时间（5秒）
     */
    public static boolean isUrlValid(String urlString) {
        return isUrlValid(urlString, 5000);
    }

    public static void main(String[] args) {
        String testUrl = "http://localhost:7101/console";

        System.out.println("正在验证URL: " + testUrl);
        boolean isValid = isUrlValid(testUrl);

        if (isValid) {
            System.out.println("✓ URL有效 - 可以正常访问");
        } else {
            System.out.println("✗ URL无效 - 无法访问此网站");
        }
    }
}

