package net.sf.xfresh.catering.db.mappers;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/25/11
 * Time: 10:57 PM
 *
 * @author Anton Ohitin
 */

public class IntegerLolwutMapper implements ParameterizedRowMapper<Integer> {
    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Integer(resultSet.getInt(1));
    }
}
