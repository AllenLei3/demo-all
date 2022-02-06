package org.xl.mybatis.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author xulei
 */
@MappedTypes(IntEnum.class)
@MappedJdbcTypes(JdbcType.INTEGER)
public class IntEnumTypeHandler extends BaseTypeHandler<IntEnum> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, IntEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public IntEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return IntEnum.get(code);
    }

    @Override
    public IntEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return IntEnum.get(code);
    }

    @Override
    public IntEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return IntEnum.get(code);
    }
}
