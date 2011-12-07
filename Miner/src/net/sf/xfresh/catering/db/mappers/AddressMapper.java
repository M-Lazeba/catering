package net.sf.xfresh.catering.db.mappers;

import net.sf.xfresh.catering.model.Address;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/26/11
 * Time: 2:56 PM
 *
 * @author Anton Ohitin
 */
public class AddressMapper implements ParameterizedRowMapper<Address> {
    public Address mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Address(resultSet.getInt("id"), resultSet.getInt("type"), resultSet.getString("coord"), resultSet.getString("addr"));
    }
}
