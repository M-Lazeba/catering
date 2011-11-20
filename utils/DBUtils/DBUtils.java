import java.io.IOException;
import java.net.MalformedURLException;
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
			throws SQLException, MalformedURLException, IOException;

	public void setIndexed(int id) throws SQLException;

	public void setIndexed(ArrayList<Integer> ids) throws SQLException;

	public float updateRating(int id, int vote) throws SQLException;
}