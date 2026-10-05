package com.mangareader.service.impl;

import com.mangareader.mapper.MailSendRecordMapper;
import com.mangareader.model.entity.MailSendRecord;
import com.mangareader.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * 邮件发送服务实现
 *
 * @author marks
 * @version v1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final MailSendRecordMapper mailSendRecordMapper;

    @Override
    public void sendMail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            log.info("邮件发送成功: to={}", to);
        } catch (MessagingException e) {
            log.error("邮件发送失败: to={}, 原因: {}", to, e.getMessage());
            throw new RuntimeException("邮件发送失败: " + e.getMessage(), e);
        }
    }

    @Override
    public void createResetMail(String email, String code) {
        String subject = "MangaReader 密码重置验证码";
        String content = buildResetEmailContent(code);

        MailSendRecord record = new MailSendRecord();
        record.setToEmail(email);
        record.setSubject(subject);
        record.setContent(content);
        record.setMailType(1);
        record.setSendStatus(0);
        record.setRetryCount(0);

        mailSendRecordMapper.insert(record);
        log.info("密码重置邮件已加入发送队列: to={}", email);
    }

    private String buildResetEmailContent(String code) {
        return "<!DOCTYPE html>" +
                "<html><head><meta charset='UTF-8'></head><body>" +
                "<div style='max-width:600px;margin:0 auto;padding:20px;font-family:sans-serif;'>" +
                "<h2 style='color:#333;text-align:center;'>MangaReader 密码重置</h2>" +
                "<div style='background:#f5f7fa;padding:20px;border-radius:8px;margin:20px 0;'>" +
                "<p>您好，您正在重置 MangaReader 账户密码。</p>" +
                "<p>您的验证码为：</p>" +
                "<div style='text-align:center;margin:20px 0;'>" +
                "<span style='font-size:32px;font-weight:bold;color:#409eff;letter-spacing:4px;'>" + code + "</span>" +
                "</div>" +
                "<p style='color:#999;font-size:12px;'>验证码 5 分钟内有效，请勿泄露给他人。</p>" +
                "</div>" +
                "<p style='color:#999;font-size:12px;text-align:center;'>此邮件由系统自动发送，请勿回复</p>" +
                "</div></body></html>";
    }
}
