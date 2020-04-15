package adapters;
import datastructures.City;
import datastructures.Country;
import javafx.util.Pair;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import java.sql.*;
import java.util.ArrayList;

public class CountryAdapter {

    public CountryAdapter(){}

    public static ArrayList<Country> getAllCountries(Connection connection) throws SQLException {
        String q = "SELECT * FROM country";
        ResultSet resultSet = connection.createStatement().executeQuery(q);
        ArrayList<Country> countries = new ArrayList<>();

        while(resultSet.next()){
            countries.add(new Country(
                    resultSet.getString("country_code"),
                    resultSet.getString("country_name"),
                    resultSet.getInt("country_id")
            ));
        }

        return countries;
    }

    public static boolean addCountry(Connection connection, Country country) throws SQLException {
        String q = "INSERT INTO country (country_name, country_code) VALUES (?,?)";
        PreparedStatement ps =  connection.prepareStatement(q);
        ps.setString(1, country.getName());
        ps.setString(2, country.getCode());
        return ps.execute();
    }

    public static Country getCountry(Connection connection, String countryCode) throws SQLException {
        String q = "SELECT * FROM country WHERE country_code=?";
        PreparedStatement ps = connection.prepareStatement(q);
        ps.setString(1, countryCode);
        ResultSet resultSet = ps.executeQuery();

        Country country = null;
        while(resultSet.next()){
            country = new Country(
                    resultSet.getString("country_code"),
                    resultSet.getString("country_name"),
                    resultSet.getInt("country_id")
            );
        }
        return country;
    }

    public static boolean deleteCountry(Connection connection, String countryCode) throws SQLException {
        String q = "DELETE FROM country WHERE country_code=?";
        PreparedStatement ps = connection.prepareStatement(q);
        ps.setString(1, countryCode);
        return ps.execute();
    }

    public static boolean updateCountry(Connection connection, String countryCode, Country country) throws SQLException {
        String q = "UPDATE country SET country_name=?, country_code=? WHERE country_code=?";
        PreparedStatement ps = connection.prepareStatement(q);
        ps.setString(1, country.getName());
        ps.setString(2, country.getCode());
        ps.setString(3, countryCode);
        return ps.execute();
    }
}

