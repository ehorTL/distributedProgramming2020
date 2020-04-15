package server.rmiobjects;

import datastructures.City;
import datastructures.Country;
import rmiinterfaces.RMIWorldMap;
import server.CityAdapter;
import server.CountryAdapter;
import server.DBConnectionProvider;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class RMIWorldMapObj extends UnicastRemoteObject implements RMIWorldMap {
    private Connection connection = null;

    public RMIWorldMapObj() throws RemoteException {
        super();
        this.connection = new DBConnectionProvider().getConnection();
    }

    @Override
    public ArrayList<City> getAllCities() throws RemoteException{
        ArrayList<City> cities = null;
        try {
            cities = CityAdapter.getAllCities(connection);
        } catch (SQLException e) {
        }

        return cities;
    }

    @Override
    public City getCity(String cityCode) throws RemoteException {
        City city = null;
        try {
            city = CityAdapter.getCity(connection, cityCode);
        } catch (SQLException e) {
        }

        return city;
    }

    @Override
    public boolean deleteCity(String countryCode, String cityCode) throws RemoteException{
        boolean ok = false;
        try {
            ok = CityAdapter.deleteCity(connection, countryCode, cityCode);
        } catch (SQLException e) {
        }

        return ok;
    }

    @Override
    public boolean updateCity(String countryCode, String cityCode, City city) throws RemoteException{
        boolean ok = false;
        try {
            ok = CityAdapter.updateCity(connection, countryCode, cityCode, city);
        } catch (SQLException e) {
        }

        return ok;
    }

    @Override
    public boolean addCity(String countryCode, City city) throws RemoteException{
        boolean ok = false;
        try {
            ok = CityAdapter.addCity(connection, countryCode, city);
        } catch (SQLException e) {
        }

        return ok;
    }

    @Override
    public ArrayList<Country> getAllCountries() throws RemoteException{
        ArrayList<Country> countries = null;
        try {
            countries = CountryAdapter.getAllCountries(this.connection);
        } catch (SQLException e) {
        }

        return countries;
    }

    @Override
    public boolean addCountry(Country country) throws RemoteException{
        boolean ok = false;
        try {
            ok = CountryAdapter.addCountry(connection, country);
        } catch (SQLException e) {
        }

        return ok;
    }

    @Override
    public Country getCountry(String countryCode) throws RemoteException{
        Country country = null;
        try {
            country = CountryAdapter.getCountry(connection, countryCode);
        } catch (SQLException e) {
        }

        return country;
    }

    @Override
    public boolean deleteCountry(String countryCode) throws RemoteException{
        boolean ok = false;
        try {
            ok = CountryAdapter.deleteCountry(connection, countryCode);
        } catch (SQLException e) {
        }

        return ok;
    }

    @Override
    public boolean updateCountry(String countryCode, Country country) throws RemoteException{
        boolean ok = false;
        try {
            ok = CountryAdapter.updateCountry(connection, countryCode, country);
        } catch (SQLException e) {
        }

        return ok;
    }
}
