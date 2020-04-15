import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CityAdapter {

    public static ArrayList<City> getAllCities(Connection connection) throws SQLException {
        String q = "SELECT * FROM city";
        ResultSet resultSet = connection.createStatement().executeQuery(q);
        ArrayList<City> cities = new ArrayList<>();

        while (resultSet.next()) {
            cities.add(new City(
                    resultSet.getString("city_name"),
                    resultSet.getString("city_code"),
                    resultSet.getInt("city_id"),
                    resultSet.getLong("city_population"),
                    resultSet.getInt("city_iscapital") == 0 ? false : true

            ));
        }
        return cities;
    }

    public static City getCity(Connection connection, String cityCode) throws SQLException {
        String q = "SELECT * FROM city WHERE city_code=?";
        PreparedStatement ps = connection.prepareStatement(q);
        ps.setString(1, cityCode);
        ResultSet rs = ps.executeQuery();

        City city = null;
        while (rs.next()) {
            city = new City(
                    rs.getString("city_name"),
                    rs.getString("city_code"),
                    rs.getInt("city_id"),
                    rs.getLong("city_population"),
                    rs.getInt("city_iscapital") == 0 ? false : true);
        }

        return city;
    }

    public static boolean deleteCity(Connection connection, String countryCode, String cityCode) throws SQLException {
        String q = "DELETE FROM city WHERE city_code=? AND country_id IN (SELECT country.country_id FROM country WHERE " +
                "country_code=?)";
        PreparedStatement ps = connection.prepareStatement(q);
        ps.setString(1, cityCode);
        ps.setString(2, countryCode);

        return ps.execute();
    }

    public static boolean updateCity(Connection connection, String countryCode, String cityCode, City city) throws SQLException {
        String q = "UPDATE city SET city_name=?, city_code=?, city_population=?, city_iscapital=? " +
                "WHERE city_code=? AND (country_id IN (SELECT country.country_id FROM country WHERE country_code=?))";
        PreparedStatement ps = connection.prepareStatement(q);
        ps.setString(1, city.getName());
        ps.setString(2, city.getCode());
        ps.setLong(3, city.getPopulation());
        ps.setInt(4, city.isCapital() ? 1 : 0);
        ps.setString(5, cityCode);
        ps.setString(6, countryCode);

        return ps.execute();
    }

    //public City(String name, String code, long id, long population, boolean isCapital) {
    public static boolean addCity(Connection connection, String countryCode, City city) throws SQLException {
        String qq = "SELECT country_id from country WHERE country_code=? LIMIT 1";
        PreparedStatement ps1 = connection.prepareStatement(qq);
        ps1.setString(1, countryCode);
        ResultSet rs1 = ps1.executeQuery();
        boolean nothingFound = true;
        long countryId = 0;
        while(rs1.next()){
            countryId = rs1.getLong("country_id");
            nothingFound = false;
        }
        if (nothingFound){
            return false;
        }

        String q = "INSERT INTO city (country_id, city_code, city_name, city_population, city_iscapital) VALUES (?,?,?,?,?)";
        PreparedStatement ps2 = connection.prepareStatement(q);
        ps2.setLong(1, countryId);
        ps2.setString(2, city.getCode());
        ps2.setString(3, city.getName());
        ps2.setLong(4, city.getPopulation());
        ps2.setInt(5, city.isCapital() ? 1 : 0);

        return ps2.execute();
    }

}