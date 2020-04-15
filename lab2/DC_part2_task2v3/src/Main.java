import com.mysql.cj.x.protobuf.MysqlxDatatypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.Spliterators;

public class Main {
    private static final String url = "jdbc:mysql://remotemysql.com:3306/1WxvvnNvoF?allowPublicKeyRetrieval=true&serverTimezone=Europe/Moscow&useSSL=false";
    private final static String adminPassword = "vwgRJ7gbzD";
    private final static String  adminUserName = "1WxvvnNvoF";
    private  final static  String driverName = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        initConnection();

        System.out.println("\n\n---------COUNTRIES--------------");
        CountryAdapter.getAllCountries(connection).forEach((c) -> System.out.println(c));
        System.out.println("\n------------------CITIES--------------");
        CityAdapter.getAllCities(connection).forEach(c -> System.out.println(c));

//      CountryAdapter.addCountry(connection, new Country("code4", "LandaoLand", 666));
//        CityAdapter.addCity(connection, "code4", new City("Landao City", "lndst1", 222222, 33333333, true));
//        CityAdapter.updateCity(connection, "code4", "lndst1", new City("Landao City Updated", "lndst1", 444, 555, false));
//        CountryAdapter.updateCountry(connection, "code4", new Country("code4", "Landao Land Updated", 777));
//        CityAdapter.deleteCity(connection, "code4", "lndst1");

//        CityAdapter.addCity(connection, "code4", new City("Landao City 2", "lndst2", 222, 999, false));
//       CountryAdapter.deleteCountry(connection, "code4");

        System.out.println("\n\n---------COUNTRIES--------------");
        CountryAdapter.getAllCountries(connection).forEach((c) -> System.out.println(c));

        System.out.println("\n------------------CITIES--------------");
        CityAdapter.getAllCities(connection).forEach(c -> System.out.println(c));

    }

    public static void initConnection(){

        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, adminUserName, adminPassword);
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("CLASS NOT FOUND");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
