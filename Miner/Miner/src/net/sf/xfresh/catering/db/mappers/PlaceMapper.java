package net.sf.xfresh.catering.db.mappers;

import net.sf.xfresh.catering.model.Place;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/25/11
 * Time: 10:41 PM
 *
 * @author Anton Ohitin
 */
public class PlaceMapper implements ParameterizedRowMapper<Place> {
    public Place mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Place(resultSet.getInt("id"), resultSet.getString("name"),
                1, null);
    }
}
