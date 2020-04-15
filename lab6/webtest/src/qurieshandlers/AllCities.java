package qurieshandlers;

import adapters.CityAdapter;
import adapters.CountryAdapter;
import datastructures.City;
import datastructures.Country;
import providers.DBConnectionProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/AllCities")
public class AllCities extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        ArrayList<City> cities = null;
        try {
            cities = CityAdapter.getAllCities(new DBConnectionProvider().getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String contentString = "";
        if ((cities == null) || (cities.size() == 0)){
            contentString = "Nothing found";
        }
        else{
            contentString += "<h2 style='text-align: center;'>All cities</h2>";
            for (int i = 0; i <cities.size() ; i++) {
                String item = "";
                item += "<div class='cust-city'>" + "<h3> City: " + cities.get(i).getName() + "</h3>" +
                        "<h4> Code: " + cities.get(i).getCode() + "</h4>" +
                        "<p> Population: " + cities.get(i).getPopulation() + "</p>" +
                        (cities.get(i).isCapital() ? "<p>Capital</p>" : "") + "</div>";
                contentString += item;
            }
        }
        request.setAttribute("cities", contentString);
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
