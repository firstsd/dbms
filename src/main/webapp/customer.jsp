<%--
  Created by IntelliJ IDEA.
  User: SD
  Date: 2/5/2018
  Time: 10:08 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Customer section</title>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body>
<div class="main"><a href="http://localhost:8080">GO BACK</a></div>
<div>
    <form method="post" action="/customer">
        <fieldset>
            <legend><h3>ADD CUSTOMER</h3></legend>
            <input type="text" placeholder="First name" name="fName"><br>
            <input type="text" placeholder="Last name" name="lName"><br>
            <label>Calling from &nbsp;</label>
            <select name="country">
                <c:forEach var="country" items="${countries}">
                    <option value="${country.countryCode}"> ${country.countryName} </option>
                </c:forEach>
            </select><br>
            <input type="text" placeholder="Phone number" name="phone"><br>
            <label>Services &nbsp;</label>
            <select name="service">
                <c:forEach var="item" items="${Service}">
                    <option value="${item.serviceId}"> ${item.serviceName} </option>
                </c:forEach>
            </select><br>
            <input type="text" placeholder="Address" name="address"><br>
            <input type="submit" value="Add customer">
        </fieldset>
    </form>
</div>
<hr/>
<h3>Customer List</h3>
<hr/>
<table border="1" cellpadding="5" cellspacing="1">
    <tr bgcolor="#EDEDED">
        <td>CustomerID</td>
        <td>First Name</td>
        <td>Last Name</td>
        <td>Country</td>
        <td>Phone</td>
        <td>Service</td>
        <td>Address</td>
    </tr>
    <c:forEach var="customer" items="${customers}">
        <tr>
            <td>${customer.custID}</td>
            <td>${customer.fName}</td>
            <td>${customer.lName}</td>
            <td>${customer.countryCode.countryName}</td>
            <td>${customer.phoneNo}</td>
            <td>${customer.service.serviceName}</td>
            <td>${customer.address}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
