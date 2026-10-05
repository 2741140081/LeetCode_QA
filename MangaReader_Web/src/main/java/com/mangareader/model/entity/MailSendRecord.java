package com.mangareader.model.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 邮件发送记录实体
 *
 * @author marks
 * @version v1.0
 */
@Data
public class MailSendRecord {
    private Long id;
    private String toEmail;
    private String subject;
    private String content;
    /** 类型: 1-密码重置 */
    private Integer mailType;
    /** 0-待发送, 1-发送中, 2-已发送, 3-发送失败 */
    private Integer sendStatus;
    private Integer retryCount;
    private String errorMsg;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
