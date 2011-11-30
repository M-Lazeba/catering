package net.sf.xfresh.catering.db.mappers;

import net.sf.xfresh.catering.model.PositionTag;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/25/11
 * Time: 9:16 PM
 *
 * @author Anton Ohitin
 */
public class TagMapper implements ParameterizedRowMapper<PositionTag> {

    public PositionTag mapRow(ResultSet resultSet, int i) throws SQLException {
        return new PositionTag(resultSet.getInt("id"), resultSet.getString("name"));
    }
}
