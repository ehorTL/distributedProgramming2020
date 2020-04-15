import javafx.util.Pair;
import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.ArrayList;

public class WorldMapParser {
    private String xmlfileName = "";
    private String xsdFileName = "";
    DocumentBuilderFactory dbf = null;
    DocumentBuilder db = null;
    Document doc = null;
    Schema schema;
    private long nextCountryId;
    private long nextCityid;

    private final String mainElementTagName = "worldMap";
    private final String countryTagName = "country";
    private final String countryCode = "countryCode";
    private final String countryName = "countryName";
    private final String countryId = "countryId";
    private final String cityTagName = "city";
    private final String cityName = "cityName";
    private final String cityCode = "cityCode";
    private final String cityId = "cityId";
    private final String cityPopulation = "population";
    private final String cityIsCapital = "isCapital";


    public WorldMapParser(String xmlfileName, String xsdFileName) throws ParseErrorException {
        this.xmlfileName = xmlfileName;
        this.xsdFileName = xsdFileName;
        this.nextCountryId = 1;
        this.nextCityid = 1;

        dbf = DocumentBuilderFactory.newInstance();
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            schema = schemaFactory.newSchema(new File(xsdFileName));
        } catch (SAXException e) {
            throw new ParseErrorException(ParseErrorException.SCHEMA_ERROR);
        }
        dbf.setSchema(schema);

        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            throw new ParseErrorException(ParseErrorException.DOCUMENT_BUILDER_ERROR);
        }
        db.setErrorHandler(new ParseErrorHandler());
    }

    public void parse() throws ParseErrorException {
        try {
            doc = db.parse(new File(xmlfileName));
        } catch (Exception e) {
            throw new ParseErrorException(ParseErrorException.PARSE_ERROR);
        }

        this.nextCountryId = getNextCountryId();
        this.nextCityid = getNextCityid();
    }

    private long getNextCityid(){
        long nextCountryIdLocal = 1;
        Element root = doc.getDocumentElement();
        NodeList countries = root.getElementsByTagName(cityTagName);
        for (int i=0; i<countries.getLength(); i++){
            long currentId = Long.parseLong(((Element) countries.item(i)).getAttribute(cityId));
            if (currentId >= nextCountryIdLocal){
                nextCountryIdLocal = (currentId + 1);
            }
        }

        return nextCountryIdLocal;
    }

    private long getNextCountryId(){
        long nextCityIdLocal = 1;
        Element root = doc.getDocumentElement();
        NodeList cities = root.getElementsByTagName(countryTagName);
        for (int i=0; i<cities.getLength(); i++){
            long currentId = Long.parseLong(((Element) cities.item(i)).getAttribute(countryId));
            if (currentId >= nextCityIdLocal){
                nextCityIdLocal = (currentId + 1);
            }
        }

        return nextCityIdLocal;
    }

    public void saveFile(String newXmlfileName) throws ParseErrorException{
        Source domSourse = new DOMSource(doc);
        Result resultFile = new StreamResult(new File(newXmlfileName));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
//            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.transform(domSourse, resultFile);
        } catch (Exception e) {
            throw new ParseErrorException(ParseErrorException.TRANSFORM_ERROR);
        }
    }

    public void changeCountry(String countryCode, Country country){
        NodeList countries = doc.getElementsByTagName(this.countryTagName);
        for(int i=0; i<countries.getLength(); i++){
            Node countryNode = countries.item(i);
            if (((Element)countryNode).getAttribute(this.countryCode).equals(countryCode)){
                if ((country.getCode() != null) && (!country.getCode().isEmpty())){
                    ((Element) countryNode).setAttribute(this.countryName, country.getCode());
                }
                if ((country.getName() != null) && (!country.getName().isEmpty())){
                    ((Element) countryNode).setAttribute(this.countryName, country.getName());
                }
            }
        }
    }

    public void changeCity(String countryCode, String cityCode, City city){
        NodeList countries = doc.getElementsByTagName(this.countryTagName);
        for (int i=0; i<countries.getLength(); i++){
            Node countryNode = countries.item(i);
            if (((Element)countryNode).getAttribute(this.countryCode).equals(countryCode)){
                NodeList children = countryNode.getChildNodes();
                for(int j=0; j<children.getLength(); j++){
                    Node cityNode = children.item(j);
                    if (cityNode.getNodeName().equals(this.cityTagName)){
                        if (((Element)cityNode).getAttribute(this.cityCode).equals(cityCode)){
                            if ((city.getName() != null) && (!city.getName().isEmpty())){
                                ((Element) cityNode).setAttribute(this.cityName, city.getName());
                            }
                            if ((city.getCode() != null) && (!city.getCode().isEmpty())){
                                ((Element) cityNode).setAttribute(this.cityCode, city.getCode());
                            }
                            ((Element) cityNode).setAttribute(this.cityIsCapital, city.isCapital() ? "1" : "0");
                            ((Element) cityNode).setAttribute(this.cityPopulation, Long.toString(city.getPopulation()));

                        }
                    }
                }
            }
        }
    }

    public boolean addCity(String countryCode, City city){
        if (getCountryByCountryCode(countryCode) == null){
            return false;
        }

        if (getCityByCityCode(city.getCode()) != null){
            return false;
        }

        Element cityElement = doc.createElement(this.cityTagName);
        cityElement.setAttribute(this.cityName, city.getName());
        cityElement.setAttribute(this.cityCode, city.getCode());
        cityElement.setAttribute(this.cityIsCapital, city.isCapital() ? "1" : "0");
        cityElement.setAttribute(this.cityPopulation, Long.toString(city.getPopulation()));
        cityElement.setAttribute(this.cityId, Long.toString(this.nextCityid++));

        NodeList countries = doc.getElementsByTagName(this.countryTagName);
        for (int i=0; i<countries.getLength(); i++){
            if (((Element)(countries.item(i))).getAttribute(this.countryCode).equals(countryCode)){
                countries.item(i).appendChild(cityElement);
                return true;
            }
        }

        return false;
    }

    public boolean deleteCity(String cityCode){
        NodeList cities = doc.getElementsByTagName(this.cityTagName);
        for (int i=0; i<cities.getLength(); i++){
            Node cityNode = cities.item(i);
            if (((Element)cityNode).getAttribute(this.cityCode).equals(cityCode)){
                cityNode.getParentNode().removeChild(cityNode);
                return true;
            }
        }

        return false;
    }

    public boolean deleteCountry(String countryCode){
        NodeList countries = doc.getElementsByTagName(this.countryTagName);
        for (int i=0; i<countries.getLength(); i++){
            Node country = countries.item(i);
            if (((Element)country).getAttribute(this.countryCode).equals(countryCode)){
                country.getParentNode().removeChild(country);
                return true;
            }
        }

        return false;
    }

    public boolean addCountry(Country country){
        Element root = doc.getDocumentElement();

        if (getCountryByCountryCode(country.getCode()) != null){
            return false;
        }

        Element countryElement = doc.createElement(countryTagName);
        countryElement.setAttribute(countryName, country.getName());
        countryElement.setAttribute(countryCode, country.getCode());
        countryElement.setAttribute(countryId, Long.toString(nextCountryId++));
        root.appendChild(countryElement);

        return  true;
    }

    ArrayList<Country> getAllCountries(){
        Element root = doc.getDocumentElement();
        NodeList countries = root.getElementsByTagName(countryTagName);
        ArrayList<Country> countriesReturnArray = new ArrayList<>();
        for (int i=0; i<countries.getLength(); i++){
            Element currentCountry = (Element) countries.item(i);
            countriesReturnArray.add(new Country(
                    currentCountry.getAttribute(this.countryCode),
                    currentCountry.getAttribute(this.countryName),
                    Integer.parseInt(currentCountry.getAttribute(this.countryId))
            ));
        }

        return countriesReturnArray;
    }

    public City getCityByCityCode(String cityCode){
        ArrayList<City> cities = this.getAllCities();
        for (City c: cities) {
            if (c.getCode().equals(cityCode)){
                return c;
            }
        }

        return null;
    }

    public Country getCountryByCountryCode(String countryCode){
        ArrayList<Country> countriesAll = this.getAllCountries();
        for (Country c: countriesAll ) {
            if (c.getCode().equals(countryCode)){
                return c;
            }
        }

        return null;
    }

    ArrayList<City> getAllCities(){
        Element root = doc.getDocumentElement();
        NodeList cities = root.getElementsByTagName(cityTagName);
        ArrayList<City> citiesReturnArray = new ArrayList<>();
        for (int i=0; i<cities.getLength(); i++){
            Element currentCity = (Element) cities.item(i);
            citiesReturnArray.add(new City(
                    currentCity.getAttribute(this.cityName),
                    currentCity.getAttribute(this.cityCode),
                    Long.parseLong(currentCity.getAttribute(this.cityId)),
                    Long.parseLong(currentCity.getAttribute(this.cityPopulation)),
                    (Integer.parseInt(currentCity.getAttribute(this.cityIsCapital)) == 1) ? true : false
            ));
        }

        return citiesReturnArray;
    }

    public ArrayList<Pair<Country, ArrayList<City>>> getAllCountriesAndCities(){
        Element root = doc.getDocumentElement();
        NodeList countries = root.getElementsByTagName(countryTagName);

        ArrayList<Pair<Country, ArrayList<City>>> hashTable = new ArrayList<Pair<Country, ArrayList<City>>>();

        for (int i=0; i<countries.getLength(); i++){
            Element currentCountry = (Element) countries.item(i);
            Country currentCountryObject = new Country(
                    currentCountry.getAttribute(this.countryCode),
                    currentCountry.getAttribute(this.countryName),
                    Integer.parseInt(currentCountry.getAttribute(this.countryId)));

            ArrayList<City> cityArray = new ArrayList<>();
            NodeList citiesOfCurrentCountry = countries.item(i).getChildNodes();

            for (int j=0; j<citiesOfCurrentCountry.getLength(); j++) {
                if (!citiesOfCurrentCountry.item(j).getNodeName().equals(cityTagName)){
                    continue;
                }

                NamedNodeMap cityAttrMap = citiesOfCurrentCountry.item(j).getAttributes();
                cityArray.add(new City(
                    cityAttrMap.getNamedItem(this.cityName).getNodeValue(),
                    cityAttrMap.getNamedItem(this.cityCode).getNodeValue(),
                    Long.parseLong(cityAttrMap.getNamedItem(this.cityId).getNodeValue()),
                    Long.parseLong(cityAttrMap.getNamedItem(this.cityPopulation).getNodeValue()),
                    (Integer.parseInt(cityAttrMap.getNamedItem(this.cityIsCapital).getNodeValue()) == 1) ? true : false
                ));
            }

            hashTable.add(new Pair<>(currentCountryObject, cityArray));
        }
        return hashTable;
    }
}

class ParseErrorHandler implements ErrorHandler {
    public void warning(SAXParseException e) throws SAXException {
        System.out.println("Строка " +e.getLineNumber() + ":");
        System.out.println(e.getMessage());
    }

    public void error(SAXParseException e) throws SAXException {
        System.out.println("Строка " +e.getLineNumber() + ":");
        System.out.println(e.getMessage());
    }

    public void fatalError(SAXParseException e) throws SAXException {
        System.out.println("Строка " +e.getLineNumber() + ":");
        System.out.println(e.getMessage());
    }
}


