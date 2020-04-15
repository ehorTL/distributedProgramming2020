package server;

import datastructures.City;
import datastructures.Country;
import messageexchanger.MessageExchanger;
import messageexchanger.Operations;

import java.sql.SQLException;

public class QueryHandler {

    public QueryHandler(){}

    public static MessageExchanger serveQuery(MessageExchanger messageExchanger) throws SQLException {
        Operations op = messageExchanger.operations;
        MessageExchanger returnMessage = new MessageExchanger();

        if (op == Operations.CREATE_CITY){
            returnMessage = addCityHandler(messageExchanger.countryCode, messageExchanger.city);
        } else if (op == Operations.CREATE_COUNTRY){
            returnMessage = addCountryHandler(messageExchanger.country);
        } else if (op == Operations.READ_CITY){
            returnMessage = getCityHandler(messageExchanger.cityCode);
        } else if (op == Operations.READ_COUNTRY){
            returnMessage = getCountryHandler(messageExchanger.countryCode);
        } else if (op == Operations.READ_ALL_CITIES){
            returnMessage = getAllCitiesHandler();
        } else if (op == Operations.READ_ALL_COUNTRIES){
            returnMessage = getAllCountriesHandler();
        } else if (op == Operations.UPDATE_CITY){
            returnMessage = changeCityHandler(messageExchanger.countryCode, messageExchanger.cityCode, messageExchanger.city);
        } else if (op == Operations.UPDATE_COUNTRY){
            returnMessage = changeCountryHandler(messageExchanger.countryCode, messageExchanger.country);
        } else if (op == Operations.DELETE_CITY){
            returnMessage = deleteCityHandler(messageExchanger.countryCode, messageExchanger.cityCode);
        } else if (op == Operations.DELETE_COUNTRY){
            returnMessage = deleteCountryHandler(messageExchanger.countryCode);
        }

        return returnMessage;
    }

    private static MessageExchanger getCityHandler(String cityCode) throws SQLException {
        MessageExchanger me = new MessageExchanger();
        me.operations = Operations.READ_CITY;

        me.city = CityAdapter.getCity(new DBConnectionProvider().getConnection(), cityCode);
        me.responseStatus = true;
        if (me.city == null){
            me.responseStatus = false;
        }

        return me;
    }

    private static MessageExchanger getCountryHandler(String countryCode) throws SQLException {
        MessageExchanger me = new MessageExchanger();
        me.operations = Operations.READ_COUNTRY;

        me.country = CountryAdapter.getCountry(new DBConnectionProvider().getConnection(), countryCode);
        me.responseStatus = (me.country != null);
        return me;
    }

    private static MessageExchanger getAllCitiesHandler() throws SQLException {
        MessageExchanger me = new MessageExchanger();
        me.operations = Operations.READ_ALL_CITIES;

        me.cities = CityAdapter.getAllCities(new DBConnectionProvider().getConnection());
        me.responseStatus = (me.cities != null && (me.cities.size() != 0));

        return me;
    }

    private static MessageExchanger getAllCountriesHandler() throws SQLException {
        MessageExchanger me = new MessageExchanger();
        me.operations = Operations.READ_ALL_COUNTRIES;

        me.countries = CountryAdapter.getAllCountries(new DBConnectionProvider().getConnection());
        me.responseStatus = (me.countries != null && (me.countries.size() != 0));

        return me;
    }

    private static MessageExchanger addCityHandler(String countryCode, City city) throws SQLException {
        MessageExchanger me = new MessageExchanger();
        me.operations = Operations.CREATE_CITY;
        me.countryCode = countryCode;
        me.city = city;

        me.responseStatus = CityAdapter.addCity(new DBConnectionProvider().getConnection(), countryCode, city);
        return me;
    }

    private static MessageExchanger addCountryHandler(Country country) throws SQLException {
        MessageExchanger me = new MessageExchanger();
        me.operations = Operations.CREATE_COUNTRY;
        me.country = country;

        me.responseStatus = CountryAdapter.addCountry(new DBConnectionProvider().getConnection(), country);
        return me;
    }

    private static MessageExchanger changeCityHandler(String countryCode, String cityCode, City city) throws SQLException {
        MessageExchanger me = new MessageExchanger();
        me.operations = Operations.UPDATE_CITY;
        me.countryCode = countryCode;
        me.cityCode = cityCode;
        me.city = city;

        me.responseStatus = CityAdapter.updateCity(new DBConnectionProvider().getConnection(), countryCode, cityCode, city);
        return me;
    }

    private static MessageExchanger changeCountryHandler(String countryCode, Country country) throws SQLException {
        MessageExchanger me = new MessageExchanger();
        me.operations = Operations.UPDATE_COUNTRY;
        me.countryCode = countryCode;
        me.country = country;

        me.responseStatus = CountryAdapter.updateCountry(new DBConnectionProvider().getConnection(), countryCode, country);
        return me;
    }

    private static MessageExchanger deleteCityHandler(String countryCode, String cityCode) throws SQLException {
        MessageExchanger me = new MessageExchanger();
        me.operations = Operations.DELETE_CITY;
        me.countryCode = countryCode;
        me.cityCode = cityCode;

        me.responseStatus = CityAdapter.deleteCity(new DBConnectionProvider().getConnection(), countryCode, cityCode);
        return me;
    }

    private static MessageExchanger deleteCountryHandler(String countryCode) throws SQLException {
        MessageExchanger me = new MessageExchanger();
        me.operations = Operations.DELETE_COUNTRY;
        me.countryCode = countryCode;

        me.responseStatus = CountryAdapter.deleteCountry(new DBConnectionProvider().getConnection(), countryCode);
        return me;
    }
}
