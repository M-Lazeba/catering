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
}
