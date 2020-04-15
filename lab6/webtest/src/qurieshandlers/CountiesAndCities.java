package qurieshandlers;

import adapters.CityAdapter;
import adapters.CountryAdapter;
import datastructures.City;
import datastructures.Country;
import javafx.util.Pair;
import providers.DBConnectionProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/CountiesAndCities")
public class CountiesAndCities extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        ArrayList<Pair<City, Country>> citiesWithCountries = null;
        try {
            citiesWithCountries = CityAdapter.getAllCitiesWithCountries(new DBConnectionProvider().getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String contentString = "";
        if ((citiesWithCountries == null) || (citiesWithCountries.size() == 0)){
            contentString = "Nothing found";
        }
        else{
            contentString += "<h2 style='text-align: center;'>All cities with countries</h2>";
            for (int i = 0; i <citiesWithCountries.size() ; i++) {
                String item = "";
                City city = citiesWithCountries.get(i).getKey();
                Country country = citiesWithCountries.get(i).getValue();
                item += "<div class='cust-city'>" + "<h3> City:" + city + "</h3>" +
                        "<h4> Country: " + country + "</h4>" + "</div><br>";
                contentString += item;
            }
        }
        request.setAttribute("citiesAndCountries", contentString);
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
