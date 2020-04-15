package qurieshandlers;

import adapters.CountryAdapter;
import datastructures.Country;
import providers.DBConnectionProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/AllCountries")
public class AllCountries extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        ArrayList<Country> countries = null;
        try {
            countries = CountryAdapter.getAllCountries(new DBConnectionProvider().getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String contentString = "";
        if ((countries == null) || (countries.size() == 0)){
            contentString = "Nothing found";
        }
        else{
            contentString += "<h2 style='text-align: center;'>All countries</h2>";
            for (int i = 0; i <countries.size() ; i++) {
                String item = "";
                item += "<div class='cust-country'>" + "<h3> Country:" + countries.get(i).getName() + "</h3>" +
                        "<h4> Code: " + countries.get(i).getCode() + "</h4>" + "</div>";
                contentString += item;
            }
        }
        request.setAttribute("countries", contentString);
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
