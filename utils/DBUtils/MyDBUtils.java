import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Simple implementation of DBUtils
 * 
 * @author Grechko Vladislav, Kononov Vladislav
 * 
 */
public class MyDBUtils implements DBUtils {

	private Connection connection;
	private String url;
	private String username;
	private String pass;
	private ImgUtils imgutil;

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
		this.pass = pass;
		this.url = url;
		this.username = username;
	}

	public MyDBUtils(String url, String username, String pass, ImgUtils util)
			throws SQLException, ClassNotFoundException {
		this(url, username, pass);
		imgutil = util;
	}

	public MyDBUtils(String url) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://" + url);
	}

	public void reconnect(String username, String pass) throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://" + url,
				username, pass);
	}

	public void reconnect() throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://" + url,
				username, pass);
	}

	@Override
	public ArrayList<Position> getByPositionIds(ArrayList<Integer> ids)
			throws SQLException {
		ArrayList<Position> positionList = new ArrayList<Position>();
		Statement posSt = connection.createStatement();
		for (int id : ids) {

			ResultSet positions = posSt
					.executeQuery("SELECT id, name, description, imageUrl, costId, rating, placeAddressId, url FROM positions WHERE id = "
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
								.getFloat("rating"), tagsList, place, positions
								.getString("url")));

			} else {
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
			throws SQLException, MalformedURLException, IOException {

		for (Position position : positionList) {
			int placeAddressId = insertPlaceAddress(position.getPlace());
			int costId = insertCost(position.getPrice());
			Statement insertSt = connection.createStatement();
			String insertQuery = "INSERT INTO `positions` (`placeAddressId`, `costId`, `description`, `name`, `url`, `isIndexed`) VALUES ("
					+ placeAddressId
					+ ", "
					+ costId
					+ ", "
					+ (position.getDesc() == null ? "null, " : "'"
							+ position.getDesc() + "', ")
					+ "'"
					+ position.geTitle()
					+ "', "
					+ (position.getUrl() == null ? "'http://ya.ru', " : "'"
							+ position.getUrl() + "', ") + "false)";
			System.out.println(insertQuery);
			insertSt.execute(insertQuery);

			int newPositionId = getLastInsertedId("positions");

			ArrayList<Integer> tagIds = insertTags(position.getTags());
			for (int tagId : tagIds) {
				if (tagId == -1)
					continue;
				Statement tagDishInsertSta = connection.createStatement();
				tagDishInsertSta
						.execute("INSERT INTO tagpositions (tagId, positionId) VALUES("
								+ tagId + ", " + newPositionId + " )");
			}
			if (imgutil != null)
				imgutil.get(insertQuery, newPositionId);
		}
	}

	private int insertCost(int value) throws SQLException {
		Statement insertSt = connection.createStatement();
		ResultSet id = insertSt
				.executeQuery("SELECT id FROM costs WHERE value = " + value
						+ " AND unit = 'rub'");
		if (id.first())
			return id.getInt(1);
		insertSt.execute("INSERT INTO costs (value, unit) VALUES( " + value
				+ ", 'rub')");
		return getLastInsertedId("costs");
	}

	private int insertPlaceAddress(Place place) throws SQLException {

		int placeId = insertPlace(place.getName());
		int coordId = insertCoordinates(place.getCoord());

		Statement insertSt = connection.createStatement();
		insertSt.execute("INSERT INTO placeadresses (placeId, coordId) VALUES("
				+ placeId + ", " + coordId + ")");
		return getLastInsertedId("placeadresses");
	}

	private int insertCoordinates(String coord) throws SQLException {
		// ������ ����!
		float longitude = (float) Double.parseDouble(coord.substring(0,
				coord.indexOf(',')));
		float lattitude = (float) Double.parseDouble(coord.substring(coord
				.indexOf(',') + 1));
		Statement st = connection.createStatement();
		ResultSet id = st
				.executeQuery("SELECT id FROM coordinates WHERE `lat` = "
						+ lattitude + " AND `long` = " + longitude);
		if (id.first())
			return id.getInt(1);
		Statement insertSt = connection.createStatement();
		insertSt.execute("INSERT INTO coordinates (`lat`, `long`) VALUES ("
				+ lattitude + ", " + longitude + ")");
		return getLastInsertedId("coordinates");
	}

	private int insertPlace(String name) throws SQLException {
		Statement st = connection.createStatement();
		ResultSet id = st.executeQuery("SELECT id FROM places WHERE name = '"
				+ name + "'");
		if (id.first()) {
			return id.getInt("id");
		} else {
			st.execute("INSERT INTO places (name) VALUES ('" + name + "')");
			return getLastInsertedId("places");
		}
	}

	private ArrayList<Integer> insertTags(ArrayList<PositionTag> tags)
			throws SQLException {
		ArrayList<Integer> ids = new ArrayList<Integer>(tags.size());
		Statement tagSt = connection.createStatement();
		for (PositionTag tag : tags) {
			// ������ ����!
			if (tag.getValue() == null) {
				ids.add(-1);
				continue;
			}
			ResultSet tagId = tagSt
					.executeQuery("SELECT id FROM tags WHERE name = '" + tag
							+ "'");
			if (tagId.first()) {
				ids.add(tagId.getInt("id"));
			} else {
				Statement insertSt = connection.createStatement();
				insertSt.execute("INSERT INTO tags (name) VALUES ('"
						+ tag.getValue() + "')");

				ids.add(getLastInsertedId("tags"));
			}
		}
		return ids;
	}

	private int getLastInsertedId(String table) throws SQLException {
		Statement st = connection.createStatement();
		ResultSet id = st.executeQuery("SELECT LAST_INSERT_ID() FROM " + table);
		if (id.first())
			return id.getInt(1);
		else
			throw new IllegalArgumentException("Table " + table
					+ " doesn't contains any id");
	}

	public ArrayList<Integer> getUnIndexed() throws SQLException {
		ArrayList<Integer> unIndexed = new ArrayList<Integer>();
		Statement statement = connection.createStatement();
		ResultSet id = statement.executeQuery("SELECT id FROM positions WHERE "
				+ "isIndexed = false");
		while (id.next()) {
			Integer a = id.getInt("id");
			unIndexed.add(a);
		}
		return unIndexed;
	}

	public void setIndexed(int id) throws SQLException {
		Statement st = connection.createStatement();
		if (st.executeUpdate("UPDATE positions SET isIndexed = true WHERE id = "
				+ id) == 0)
			throw new IllegalArgumentException("Id " + id + " not found in DB");
	}

	public void setIndexed(ArrayList<Integer> ids) throws SQLException {
		for (int id : ids)
			setIndexed(id);
	}

	@Override
	public float updateRating(int id, int vote) throws SQLException {
		Statement st = connection.createStatement();
		ResultSet data = st
				.executeQuery("SELECT rating, votesCount FROM positions WHERE id = "
						+ id);
		if (data.first()) {
			// ������ ����!
			if (!(data.getObject("votesCount") == null)) {
				float newRating = (data.getFloat("rating")
						* data.getInt("votesCount") + vote)
						/ (data.getInt("votesCount") + 1);
				st.execute("UPDATE `positions` SET `rating`= " + newRating
						+ ", `votesCount`= " + (data.getInt("votesCount") + 1)
						+ " WHERE `id`= '" + id + "'");
				return newRating;
			} else { // first vote
				System.out.println("opda");
				st.execute("UPDATE `positions` SET `rating` = " + vote
						+ ", `votesCount` = 1 WHERE `id` = '" + id + "'");
				return vote;
			}
		} else {
			throw new IllegalStateException("Id " + id + " not found in DB");
		}
	}
}