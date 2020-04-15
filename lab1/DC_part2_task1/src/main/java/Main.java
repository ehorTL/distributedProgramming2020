import java.util.ArrayList;

public class Main {
    private static final String xmlPath = "C:\\Users\\user\\Desktop\\3course2sem\\distributedComputing\\LabRasp2\\lab1\\WorldMap.xml";
    private static final String xsdPath = "C:\\Users\\user\\Desktop\\3course2sem\\distributedComputing\\LabRasp2\\lab1\\WorldMap.xsd";

    public static void main(String[] args) throws ParseErrorException {
        WorldMap worldMap = null;

        try {
             worldMap = new WorldMap(xmlPath, xsdPath);
        } catch (ParseErrorException e) {
            e.printStackTrace();
        }

        worldMap.addCountry(new Country("newCntrCode1", "Nonameland", 121324));
        worldMap.addCity("newCntrCode1", new City("newCity", "newCtCode1", 3, 12312313, true));
        worldMap.deleteCountry("code001");
        worldMap.deleteCity("ctc003");
        worldMap.deleteCity("ctc007");
        worldMap.changeCountry("code112001", new Country("code112001", "USA Changed", 123));
        worldMap.changeCity("newCntrCode1", "newCtCode1", new City("ChangedCityName", "newCtCode1", 123, 32342342, false));


        System.out.println("ALL COUNTRIES ANR THEIR CITIES:");
        worldMap.getAllCountriesAndCities().forEach(pair -> {
            Country key = pair.getKey();
            System.out.println(key);

            pair.getValue().forEach(city -> {System.out.print("\t\t\t"); System.out.println(city);});
        });
        System.out.println("---------------------------------------------\n");

        //worldMap.saveToFile(xmlPath);
    }
}