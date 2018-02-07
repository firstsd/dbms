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
</head>
<body>
<a href="#">ADD CUSTOMER</a>
<hr/>
<h3>Customer List</h3>
<hr/>
    <form method="post" action="/customer">
        <fieldset>
            <legend>ADD CUSTOMER</legend>
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
</body>
</html>
