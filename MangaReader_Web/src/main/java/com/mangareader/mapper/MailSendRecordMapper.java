package com.mangareader.mapper;

import com.mangareader.model.entity.MailSendRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 邮件发送记录 Mapper
 *
 * @author marks
 * @version v1.0
 */
@Mapper
public interface MailSendRecordMapper {

    int insert(MailSendRecord record);

    List<MailSendRecord> selectPendingRecords(@Param("limit") int limit);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status, @Param("errorMsg") String errorMsg);

    int incrementRetryCount(@Param("id") Long id);
}
