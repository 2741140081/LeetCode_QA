package com.mangareader.service;

/**
 * 邮件发送服务接口
 *
 * @author marks
 * @version v1.0
 */
public interface MailService {

    /**
     * 发送邮件
     */
    void sendMail(String to, String subject, String content);

    /**
     * 创建密码重置邮件记录并加入发送队列
     */
    void createResetMail(String email, String code);
}
