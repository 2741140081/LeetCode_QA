package com.mangareader.config;

import com.mangareader.enums.ProcessStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(ProcessStatus.class)
public class ProcessStatusTypeHandler extends BaseTypeHandler<ProcessStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ProcessStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public ProcessStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return rs.wasNull() ? null : ProcessStatus.fromCode(code);
    }

    @Override
    public ProcessStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return rs.wasNull() ? null : ProcessStatus.fromCode(code);
    }

    @Override
    public ProcessStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return cs.wasNull() ? null : ProcessStatus.fromCode(code);
    }
}