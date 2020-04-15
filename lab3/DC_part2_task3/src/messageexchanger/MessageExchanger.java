package messageexchanger;

import datastructures.City;
import datastructures.Country;

import java.io.Serializable;
import java.util.ArrayList;

public class MessageExchanger implements Serializable {
    public Operations operations;
    public boolean responseStatus;

    public String countryCode;
    public String cityCode;
    public City city;
    public Country country;
    public ArrayList<Country> countries;
    public ArrayList<City> cities;

    public MessageExchanger(){}
}
