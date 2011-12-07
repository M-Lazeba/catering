package net.sf.xfresh.catering.db;

import net.sf.xfresh.catering.model.Position;
import org.springframework.dao.DataAccessException;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA. User: exprmntr Date: 11/17/11 Time: 4:55 PM
 *
 * @author Anton Ohitin
 */
public interface DBUtils {

    public Collection<Position> getByPositionIds(Collection<Integer> ids) throws DataAccessException;

    public Collection<Position> getByTagId(Integer id) throws DataAccessException;

    public Collection<Position> getByPlaceId(Integer id) throws DataAccessException;

    public Collection<Position> getAllPositions() throws DataAccessException;

    public void uncheckedInsertPositions(Collection<Position> positions) throws DataAccessException;

    public int uncheckedInsertPosition(Position positions) throws DataAccessException;

    public Collection<Integer> getUnIndexed() throws DataAccessException;

    public void setIndexed(Integer id) throws DataAccessException;

    public void setIndexed(Collection<Integer> ids) throws DataAccessException;

}