package rmiinterfaces;

import datastructures.City;
import datastructures.Country;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIWorldMap extends Remote {
    public ArrayList<City> getAllCities() throws RemoteException;
    public City getCity(String cityCode) throws RemoteException;
    public boolean deleteCity(String countryCode, String cityCode) throws RemoteException;
    public boolean updateCity(String countryCode, String cityCode, City city) throws RemoteException;
    public boolean addCity(String countryCode, City city) throws RemoteException;
    public ArrayList<Country> getAllCountries() throws RemoteException;
    public boolean addCountry(Country country) throws RemoteException;
    public Country getCountry(String countryCode) throws RemoteException;
    public boolean deleteCountry(String countryCode) throws RemoteException;
    public boolean updateCountry(String countryCode, Country country) throws RemoteException;
}
