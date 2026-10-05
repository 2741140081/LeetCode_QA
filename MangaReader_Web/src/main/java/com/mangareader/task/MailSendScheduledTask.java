package com.mangareader.task;

import com.mangareader.mapper.MailSendRecordMapper;
import com.mangareader.model.entity.MailSendRecord;
import com.mangareader.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 邮件发送定时任务
 * 定时扫描待发送邮件记录并发送
 *
 * @author marks
 * @version v1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MailSendScheduledTask {

    private static final int MAX_RETRY = 3;
    private static final int BATCH_SIZE = 10;

    private final MailSendRecordMapper mailSendRecordMapper;
    private final MailService mailService;

    /**
     * 每 30 秒扫描并发送待发送邮件
     */
    @Scheduled(fixedDelay = 30000)
    public void scanAndSendMails() {
        List<MailSendRecord> pendingRecords = mailSendRecordMapper.selectPendingRecords(BATCH_SIZE);
        if (pendingRecords.isEmpty()) {
            return;
        }

        log.info("扫描到 {} 条待发送邮件", pendingRecords.size());

        for (MailSendRecord record : pendingRecords) {
            // 标记为发送中
            mailSendRecordMapper.updateStatus(record.getId(), 1, null);

            try {
                mailService.sendMail(record.getToEmail(), record.getSubject(), record.getContent());
                // 发送成功
                mailSendRecordMapper.updateStatus(record.getId(), 2, null);
                log.info("邮件发送成功: id={}, to={}", record.getId(), record.getToEmail());
            } catch (Exception e) {
                log.warn("邮件发送失败: id={}, to={}, 原因: {}", record.getId(), record.getToEmail(), e.getMessage());
                // 增加重试次数
                mailSendRecordMapper.incrementRetryCount(record.getId());

                if (record.getRetryCount() != null && record.getRetryCount() + 1 >= MAX_RETRY) {
                    // 超过最大重试次数，标记为失败
                    mailSendRecordMapper.updateStatus(record.getId(), 3, e.getMessage());
                    log.error("邮件发送最终失败: id={}, to={}, 已达最大重试次数", record.getId(), record.getToEmail());
                } else {
                    // 标记为待发送，等待下次重试
                    mailSendRecordMapper.updateStatus(record.getId(), 0, e.getMessage());
                }
            }
        }
    }
}
