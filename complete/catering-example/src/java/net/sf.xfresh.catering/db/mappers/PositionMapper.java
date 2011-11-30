package net.sf.xfresh.catering.db.mappers;

import net.sf.xfresh.catering.model.Position;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/25/11
 * Time: 9:34 PM
 *
 * @author Anton Ohitin
 */
public class PositionMapper implements ParameterizedRowMapper<Position> {

    public Position mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Position(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("imageUrl") != null && !resultSet.getString("imageUrl").equals(""),
                resultSet.getInt("price"),
                resultSet.getFloat("rating"),
                resultSet.getString("url"),
                null,
                null
        );
    }


}
