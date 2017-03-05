package cn.edu.tjut.ots.utils;

import org.apache.ibatis.annotations.TypeDiscriminator;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by KINGBOOK on 2017/3/5.
 */
public class BooleanTypeHandler implements TypeHandler{
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType) throws SQLException {
        Boolean b = (Boolean) o;
        String value = (Boolean) b == true ? "Y" : "N";
        preparedStatement.setString(i, value);
    }

    @Override
    public Object getResult(ResultSet resultSet, String s) throws SQLException {
        String str = resultSet.getString(s);
        Boolean rt = Boolean.FALSE;
        if (str.equalsIgnoreCase("Y")){
            rt = Boolean.TRUE;
        }
        return rt;
    }

    @Override
    public Object getResult(ResultSet resultSet, int i) throws SQLException {
        Boolean b = resultSet.getBoolean(i);
        return b == true ? "Y" : "N";
    }

    @Override
    public Object getResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
