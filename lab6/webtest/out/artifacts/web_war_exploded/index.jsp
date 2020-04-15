<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>WorldMap</title>
</head>
<body>
<h1 class="cust-header"> --- World map ---</h1>
<div class="cust-menu">
    <form class="cust-form" action="AllCountries" method="get">
        <button type="submit">Get all countries</button>
    </form>
    <form class="cust-form" action="AllCities" method="get">
        <button>Get all cities</button>
    </form>
    <form class="cust-form" action="CountiesAndCities" method="get">
        <button>Get cities with countries list</button>
    </form>
</div>
    <div class="info">
        ${countries}
        ${cities}
        ${citiesAndCountries}
    </div>
</body>
</html>