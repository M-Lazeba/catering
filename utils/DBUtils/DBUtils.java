package net.sf.xfresh.catering.util;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA. User: exprmntr Date: 11/17/11 Time: 4:55 PM
 * 
 * @author Anton Ohitin
 */
public interface DBUtils {
	public ArrayList<Position> getByPositionIds(ArrayList<Integer> ids)
			throws SQLException;

	public ArrayList<Position> getByTagIds(ArrayList<Integer> ids)
			throws SQLException;

	public ArrayList<Position> getByPlaceAddressesIds(ArrayList<Integer> ids)
			throws SQLException;

	public void insertPositions(ArrayList<Position> positionList)
			throws SQLException;
}