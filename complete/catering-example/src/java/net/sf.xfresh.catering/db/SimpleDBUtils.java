package net.sf.xfresh.catering.db;

import net.sf.xfresh.catering.db.mappers.*;
import net.sf.xfresh.catering.model.Address;
import net.sf.xfresh.catering.model.Place;
import net.sf.xfresh.catering.model.Position;
import net.sf.xfresh.catering.model.PositionTag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/25/11
 * Time: 9:00 PM
 *
 * @author Anton Ohitin
 * Testimonials: Vladislav Kononov : This class is a big fucking piece of shit!!!
 */
public class SimpleDBUtils implements DBUtils{

    private SimpleJdbcTemplate jdbcTemplate;
    private final static TagMapper TAG_MAPPER = new TagMapper();
    private final static PositionMapper POSITION_MAPPER = new PositionMapper();
    private final static PlaceMapper PLACE_MAPPER = new PlaceMapper();
    private final static IntegerLolwutMapper INTEGER_LOLWUT_MAPPER = new IntegerLolwutMapper();
    private final static AddressMapper ADDRESS_MAPPER = new AddressMapper();
    private static int countHelper = 0;
    private final static int reasonableValue = 1500;

    public SimpleDBUtils(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Position> getByPositionIds(Collection<Integer> ids) throws DataAccessException {
        List<Position> result = new LinkedList<Position>();
        //countHelper = 0;
        for (Integer id : ids) {
            Position position = jdbcTemplate.queryForObject("SELECT id, name, description, imageUrl, price, rating, url " +
                    "FROM positions WHERE id = ?", POSITION_MAPPER, new Object[]{id});
            System.out.println(position.getTitle() + " " + position.getId()) ;
            List<PositionTag> tags = jdbcTemplate.query("SELECT id, name FROM tags WHERE id IN " +
                    "(SELECT tagId from tagpositions WHERE positionId = ?)", TAG_MAPPER, new Object[]{id});
            position.setTags(new LinkedList<PositionTag>(tags));
            //Integer placeId = jdbcTemplate.queryForInt("SELECT placesId FROM positions WHERE id = ?", new Object[]{id});
            //List<Integer> placeAddressIds = jdbcTemplate.query("SELECT id " +
            //        "FROM placeadresses WHERE placeId = ?", INTEGER_LOLWUT_MAPPER, new Object[]{placeId});

            //List<Address> addrs = new LinkedList<Address>();
            //for (Integer i : placeAddressIds) {
            //    addrs.add(jdbcTemplate.queryForObject("SELECT id, type, addr, coord " +
            //            "FROM placeadresses WHERE id = ?", ADDRESS_MAPPER, new Object[]{i}));
            //}

            //Place place = jdbcTemplate.queryForObject("SELECT id, name, url " +
            //        "FROM places WHERE id = ?", PLACE_MAPPER, new Object[]{placeId});
            //place.setAddrs(new LinkedList<Address>(addrs));
            //place.setAddrAndCoord(0);
            //position.setPlace(place);
            result.add(position);
            //++countHelper;
            //if (countHelper > reasonableValue)
            //    break;
        }
        return result;
    }

    public Collection<Position> getByTagId(Integer id) throws DataAccessException {
        List<Integer> ids = jdbcTemplate.query("SELECT id FROM positions WHERE id IN (SELECT positionId " +
                "FROM tagpositions WHERE tagId = ?)", INTEGER_LOLWUT_MAPPER, new Object[]{id});
        return getByPositionIds(ids);
    }

    public Collection<Position> getByPlaceId(Integer id) throws DataAccessException {
        List<Integer> ids = jdbcTemplate.query("SELECT id FROM positions WHERE placeId = ?",
                INTEGER_LOLWUT_MAPPER, new Object[]{id});
        return getByPositionIds(ids);

    }

    public Collection<Position> getAllPositions() throws DataAccessException {
        return getByPositionIds(jdbcTemplate.query("SELECT id FROM positions", INTEGER_LOLWUT_MAPPER));
    }

    public Collection<Integer> getUnIndexed() throws DataAccessException {
        return jdbcTemplate.query("SELECT id FROM positions WHERE isIndexed = 0", INTEGER_LOLWUT_MAPPER);
    }

    public void setIndexed(Integer id) throws DataAccessException {
        jdbcTemplate.update("UPDATE positions SET isIndexed = 1 WHERE id = ?", new Object[]{id});
    }

    public void setIndexed(Collection<Integer> ids) throws DataAccessException {
        for (int id : ids) {
            setIndexed(id);
        }
    }


    public int insertPlace(Place place) throws DataAccessException {
        List<Integer> places = jdbcTemplate.query("SELECT id " +
                "FROM places WHERE name = '" + place.getName() + "'", INTEGER_LOLWUT_MAPPER);
        if (places.size() > 0) {
            return places.get(0);
        } else {
            jdbcTemplate.update("INSERT INTO places (name) VALUES (?)", new Object[]{place.getName()});
            int placeId = getLastInsertedId("places");
            //for (Address addr : place.getAddrs()) {
            //    insertAddress(addr, placeId);
            //}
            return placeId;
        }
    }

    private int insertAddress(Address addr, Integer placeId){
        List<Integer> addrs = jdbcTemplate.query("SELECT id FROM placeadresses WHERE coord = ? AND placeId = ?",
                INTEGER_LOLWUT_MAPPER,
                new Object[]{addr.getCoord(), placeId});
        if (addrs.size() > 0) {
            return addrs.get(0);
        } else {
            jdbcTemplate.update("INSERT INTO placeadresses (type, coord, addr, placeId) VALUES (?, ?, ?, ?)",
                    new Object[]{addr.getType(), addr.getCoord(), addr.getAddr(), placeId});
            return getLastInsertedId("placeadresses");
        }
    }

    public int insertTag(PositionTag tag, Integer positionId) throws DataAccessException {
        List<Integer> tags = jdbcTemplate.query("SELECT id FROM tags " +
                "WHERE name = ?", INTEGER_LOLWUT_MAPPER, tag.getValue());
        if (tags.size() > 0) {
            List<Integer> tagpositions = jdbcTemplate.query("SELECT id FROM tagpositions " +
                    "WHERE positionId = ? AND tagId = ?", INTEGER_LOLWUT_MAPPER, new Object[]{positionId, tags.get(0)});
            if (tagpositions.size() > 0) {
                return tagpositions.get(0);
            } else {
                jdbcTemplate.update("INSERT INTO tagpositions (positionId, tagId) VALUES (?, ?)",
                        new Object[]{positionId, tags.get(0)});
                return getLastInsertedId("tagpositions");
            }
        } else {
            jdbcTemplate.update("INSERT INTO tags (name) VALUES (?)", new Object[]{tag.getValue()});
            Integer tagId = getLastInsertedId("tags");
            jdbcTemplate.update("INSERT INTO tagpositions (positionId, tagId) VALUES (?, ?)",
                    new Object[]{positionId, tagId});
            return getLastInsertedId("tagpositions");
        }
    }
    
    public int simpleInsertTag(String probability) throws DataAccessException{
        jdbcTemplate.update("INSERT INTO tags " +
            "(name)" +
            "VALUES (?)", new Object[]{
            probability
        });
        return getLastInsertedId("tags");
    }
    
    public int simpleInsertPosition(Position pos) throws DataAccessException{
        Integer placeId = insertPlace(pos.getPlace());
        jdbcTemplate.update("INSERT INTO positions " +
                "(name, description, price, placesId, isIndexed) " +
                "VALUES (?, ?, ?, ?, ?)", new Object[]{
                pos.getTitle(),
                pos.getDescription(),
                pos.getPrice(),
                placeId,
                Boolean.FALSE
        });
        return getLastInsertedId("positions");
    }
    
    public int simpleInsertTagPosition(Integer positionID, Integer tagID) throws DataAccessException{
        jdbcTemplate.update("INSERT INTO tagpositions " +
                "(tagId, positionId) " +
                "VALUES (?, ?)", new Object[]{
                tagID,
                positionID
        });
        return getLastInsertedId("tagpositions");
    }

    public void uncheckedInsertPositions(Collection<Position> positions) throws DataAccessException {
        for (Position position : positions) {
            uncheckedInsertPosition(position);
        }
    }

    public int uncheckedInsertPosition(Position position) {
        Integer placeId = insertPlace(position.getPlace());
        jdbcTemplate.update("INSERT INTO positions " +
                "(name, description, imageUrl, price, url, placesId, isIndexed) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)", new Object[]{
                position.getTitle(),
                position.getDescription(),
                position.getImgUrl(),
                position.getPrice(),
                position.getUrl(),
                placeId,
                Boolean.FALSE
        });
        int positionId = getLastInsertedId("positions");
        for (PositionTag tag : position.getTags()) {
            insertTag(tag, positionId);
        }
        return positionId;
    }

    public int getLastInsertedId(String table) {
        return jdbcTemplate.query("SELECT LAST_INSERT_ID() FROM " + table, INTEGER_LOLWUT_MAPPER).get(0);
    }

}
