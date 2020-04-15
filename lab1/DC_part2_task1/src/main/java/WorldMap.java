import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.ArrayList;

public class WorldMap {
    private ArrayList<Country> countries;
    private ArrayList<City> cities;
    private WorldMapParser worldMapParser = null;

    public WorldMap(String xmlFileName, String xsdFileName) throws ParseErrorException {
        worldMapParser = new WorldMapParser(xmlFileName, xsdFileName);
        worldMapParser.parse();
    }

    public void saveToFile(String fileName) throws ParseErrorException {
        worldMapParser.saveFile(fileName);
    }

    public boolean addCountry(Country country){
        return worldMapParser.addCountry(country);
     }

     public boolean addCity(String countryCode, City city){
        return worldMapParser.addCity(countryCode, city);
     }

     public boolean deleteCountry(String countryCode){
        return worldMapParser.deleteCountry(countryCode);
     }

     public boolean deleteCity(String cityCode){
        return  worldMapParser.deleteCity(cityCode);
     }

     public void changeCity(String countryCode, String cityCode, City city){
        worldMapParser.changeCity(countryCode, cityCode, city);
     }

    public void changeCountry(String countryCode, Country country){
        worldMapParser.changeCountry(countryCode, country);
     }

     public City getCityByCityCode(String cityCode){
        return worldMapParser.getCityByCityCode(cityCode);
     }

     public Country getCountryByCountryCode(String countryCode){
        return  worldMapParser.getCountryByCountryCode(countryCode);
     }

     public ArrayList<Country> getAllCountries(){
        return worldMapParser.getAllCountries();
     }

     public ArrayList<City> getAllCities(){
         return worldMapParser.getAllCities();
     }

     public ArrayList<Pair<Country, ArrayList<City>>> getAllCountriesAndCities(){
        return worldMapParser.getAllCountriesAndCities();
     }
}
