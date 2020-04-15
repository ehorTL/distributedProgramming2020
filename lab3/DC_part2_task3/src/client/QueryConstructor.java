package client;

import datastructures.City;
import datastructures.Country;
import messageexchanger.MessageExchanger;
import messageexchanger.Operations;

public class QueryConstructor {
    public static MessageExchanger getCity(){
        MessageExchanger me = new MessageExchanger();
        me.operations = Operations.READ_CITY;
        return me;
    }

    public static MessageExchanger getCountry(){
        MessageExchanger me = new MessageExchanger();
        me.operations = Operations.READ_COUNTRY;
        return me;
    }

    public static MessageExchanger getAllCities(){
        MessageExchanger me = new MessageExchanger();
        me.operations = Operations.READ_ALL_CITIES;
        return me;
    }

    public static MessageExchanger getAllCountries(){
        MessageExchanger me = new MessageExchanger();
        me.operations = Operations.READ_ALL_COUNTRIES;
        return me;
    }

    public static MessageExchanger addCity(String countryCode, City city){
        MessageExchanger me = new MessageExchanger();
        me.operations = Operations.CREATE_CITY;
        me.countryCode = countryCode;
        me.city = city;

        return me;
    }

    public static MessageExchanger addCountry(Country country){
        MessageExchanger me = new MessageExchanger();
        me.operations = Operations.CREATE_COUNTRY;
        me.country = country;

        return me;
    }

    public static MessageExchanger changeCity(String countryCode, String cityCode, City city){
        MessageExchanger me = new MessageExchanger();
        me.operations = Operations.UPDATE_CITY;
        me.countryCode = countryCode;
        me.cityCode = cityCode;
        me.city = city;

        return me;
    }

    public static MessageExchanger changeCountry(String countryCode, Country country){
        MessageExchanger me = new MessageExchanger();
        me.operations = Operations.UPDATE_COUNTRY;
        me.countryCode = countryCode;
        me.country = country;

        return me;
    }

    public static MessageExchanger deleteCity(String countryCode, String cityCode){
        MessageExchanger me = new MessageExchanger();
        me.operations = Operations.DELETE_CITY;
        me.countryCode = countryCode;
        me.cityCode = cityCode;

        return me;
    }

    public static MessageExchanger deleteCountry(String countryCode){
        MessageExchanger me = new MessageExchanger();
        me.operations = Operations.DELETE_COUNTRY;
        me.countryCode = countryCode;

        return me;
    }
}
