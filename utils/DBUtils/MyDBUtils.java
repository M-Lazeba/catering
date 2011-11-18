package net.sf.xfresh.catering.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Simple implementation of DBUtils
 * 
 * @author Grechko Vladislav
 * 
 */
public class MyDBUtils implements DBUtils {

	private Connection connection;

	/**
	 * 
	 * @param dbName
	 *            Database url
	 * @param username
	 *            Username
	 * @param pass
	 *            Password
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MyDBUtils(String url, String username, String pass)
			throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://" + url,
				username, pass);
	}

	public MyDBUtils(String url) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://" + url);
	}

	@Override
	public ArrayList<Position> getByPositionIds(ArrayList<Integer> ids)
			throws SQLException {
		ArrayList<Position> positionList = new ArrayList<Position>();
		Statement posSt = connection.createStatement();
		for (int id : ids) {

			ResultSet positions = posSt
					.executeQuery("SELECT id, name, description, imageUrl, costId, rating, placeAddressId FROM positions WHERE id = "
							+ id);
			if (positions.first()) {

				Statement tagSt = connection.createStatement();
				ResultSet tags = tagSt
						.executeQuery("SELECT id, name FROM tags WHERE id IN (SELECT id from tagpositions WHERE positionId = "
								+ id + " )");

				Statement costSt = connection.createStatement();
				ResultSet costRes = costSt
						.executeQuery("SELECT value FROM costs WHERE id = "
								+ positions.getInt("costId"));
				costRes.first();
				int cost = costRes.getInt("value");

				ArrayList<PositionTag> tagsList = new ArrayList<PositionTag>();
				while (tags.next()) {
					tagsList.add(new PositionTag(tags.getInt("id"), tags
							.getString("name")));
				}

				int placeAddressId = positions.getInt("placeAddressId");

				Statement placeSt = connection.createStatement();
				ResultSet placeRes = placeSt
						.executeQuery("SELECT placeId FROM placeadresses WHERE id = "
								+ placeAddressId);
				placeRes.first();
				int placeId = placeRes.getInt("placeId");

				Statement tmpSt = connection.createStatement();
				ResultSet tmpRes = tmpSt
						.executeQuery("SELECT coordId FROM placeadresses WHERE id = "
								+ placeAddressId);
				tmpRes.first();
				int coordId = tmpRes.getInt("coordId");

				Statement placeAddressSt = connection.createStatement();
				ResultSet places = placeAddressSt
						.executeQuery("SELECT places.name, placeadresses.type, coordinates.lat, coordinates.long FROM places, placeAdresses, coordinates WHERE places.id = "
								+ placeId
								+ " AND placeadresses.id = "
								+ placeAddressId
								+ " AND coordinates.id = "
								+ coordId);

				Place place = null;

				if (places.first())
					place = new Place(placeAddressId, places.getString("name"),
							places.getInt("type"), places.getFloat("long")
									+ "," + places.getFloat("lat"));

				positionList.add(new Position(id, positions.getString("name"),
						positions.getString("description"), positions
								.getBlob("imageUrl") != null, cost, positions
								.getFloat("rating"), tagsList, place));

			} else { // id isn't exists
				throw new IllegalArgumentException("Id" + id
						+ " not found in DB");
			}
		}
		return positionList;
	}

	@Override
	public ArrayList<Position> getByTagIds(ArrayList<Integer> ids)
			throws SQLException {

		ArrayList<Integer> positionIdsList = new ArrayList<Integer>();

		Statement tagSt = connection.createStatement();
		for (int id : ids) {
			ResultSet positionIds = tagSt
					.executeQuery("SELECT id FROM positions WHERE id IN (SELECT positionId FROM tagpositions WHERE tagId = "
							+ id + " )");

			while (positionIds.next())
				if (!positionIdsList.contains(positionIds.getInt("id")))
					positionIdsList.add(positionIds.getInt("id"));
		}

		return getByPositionIds(positionIdsList);
	}

	@Override
	public ArrayList<Position> getByPlaceAddressesIds(ArrayList<Integer> ids)
			throws SQLException {
		ArrayList<Integer> positionIdsList = new ArrayList<Integer>();

		Statement placeSt = connection.createStatement();
		for (int id : ids) {
			ResultSet positionIds = placeSt
					.executeQuery("SELECT id FROM positions WHERE placeAddressId = "
							+ id);
			while (positionIds.next())
				positionIdsList.add(positionIds.getInt("id"));
		}

		return getByPositionIds(positionIdsList);
	}

	@Override
	public void insertPositions(ArrayList<Position> positionList)
			throws SQLException {

		for (Position position : positionList) {
			int placeAddressId = insertPlaceAddress(position.getPlace());
			int costId = insertCost(position.getPrice());
			Statement insertSt = connection.createStatement();
			String query = "INSERT INTO `positions` (`placeAddressId`, `costId`, `description`, `name`) VALUES ("
					+ placeAddressId
					+ ", "
					+ costId
					+ ", "
					+ "'"
					+ position.getDesc()
					+ "', "
					+ "'"
					+ position.geTitle()
					+ "')";
			insertSt.execute(query);

			ResultSet newPositionIdRes = insertSt
					.executeQuery("SELECT id FROM positions ORDER BY id DESC");
			newPositionIdRes.first();
			int newPositionId = newPositionIdRes.getInt("id");

			ArrayList<Integer> tagIds = insertTags(position.getTags());
			for (int tagId : tagIds) {
				Statement tagDishInsertSta = connection.createStatement();
				tagDishInsertSta
						.execute("INSERT INTO tagpositions (tagId, positionId) VALUES("
								+ tagId + ", " + newPositionId + " )");
			}
		}
	}

	private int insertCost(int value) throws SQLException {
		// ובאםûי סעûה!
		Statement insertSt = connection.createStatement();
		insertSt.execute("INSERT INTO costs (value, unit) VALUES( " + value
				+ ", 'rub')");
		ResultSet newId = insertSt
				.executeQuery("SELECT id FROM costs WHERE value = " + value
						+ " AND unit = 'rub'");
		newId.first();
		return newId.getInt("id");
	}

	private int insertPlaceAddress(Place place) throws SQLException {
		// ובאםûי סעûה!
		int placeId = insertPlace(place.getName());
		int coordId = insertCoordinates(place.getCoord());

		Statement insertSt = connection.createStatement();
		insertSt.execute("INSERT INTO placeadresses (placeId, coordId) VALUES("
				+ placeId + ", " + coordId + ")");
		ResultSet newId = insertSt
				.executeQuery("SELECT id FROM placeadresses WHERE placeId = "
						+ placeId + " AND coordId = " + coordId);
		newId.first();
		return newId.getInt("id");
	}

	private int insertCoordinates(String coord) throws SQLException {
		// ובאםûי סעûה!
		float longitude = (float) Double.parseDouble(coord.substring(0,
				coord.indexOf(',')));
		float lattitude = (float) Double.parseDouble(coord.substring(coord
				.indexOf(',') + 1));
		Statement insertSt = connection.createStatement();
		insertSt.execute("INSERT INTO coordinates (`lat`, `long`) VALUES ("
				+ lattitude + ", " + longitude + ")");
		ResultSet newId = insertSt
				.executeQuery("SELECT LAST_INSERT_ID() FROM coordinates");
		newId.first();
		return newId.getInt(1);
	}

	private int insertPlace(String name) throws SQLException {
		Statement st = connection.createStatement();
		ResultSet id = st.executeQuery("SELECT id FROM places WHERE name = '"
				+ name + "'");
		if (id.first()) {
			return id.getInt("id");
		} else {
			st.execute("INSERT INTO places (name) VALUES ('" + name + "')");
			ResultSet newId = st
					.executeQuery("SELECT id FROM places WHERE name = '" + name
							+ "'");
			newId.first();
			return newId.getInt("id");
		}
	}

	private ArrayList<Integer> insertTags(ArrayList<PositionTag> tags)
			throws SQLException {
		ArrayList<Integer> ids = new ArrayList<Integer>(tags.size());
		Statement tagSt = connection.createStatement();
		for (PositionTag tag : tags) {
			ResultSet tagId = tagSt
					.executeQuery("SELECT id FROM tags WHERE name = '" + tag
							+ "'");
			if (tagId.first()) {
				ids.add(tagId.getInt("id"));
			} else {
				Statement insertSt = connection.createStatement();
				insertSt.execute("INSERT INTO tags (name) VALUES ('"
						+ tag.getValue() + "')");
				// ובאםûי סעûה!!!
				ResultSet newId = insertSt
						.executeQuery("SELECT id FROM tags WHERE name = '"
								+ tag.getValue() + "'");
				newId.first();
				ids.add(newId.getInt("id"));
			}
		}
		return ids;
	}
}
