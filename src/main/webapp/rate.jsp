<%--
  Created by IntelliJ IDEA.
  User: SD
  Date: 2/5/2018
  Time: 10:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Rate</title>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body>
<div class="main"><a href="http://localhost:8080">GO BACK</a></div>
<div>
    <h1>UPDATE RATE</h1>
    <form action="/rate" method="post">
        <input type="file" name="rateFile">
        <input type="submit" value="IMPORT DATA" name="import">
    </form>
    <div class='<c:choose>
<c:when test="${importErrorType eq 1}">error</c:when>
<c:otherwise>success</c:otherwise>
</c:choose>'>${importError} - ${filepath}</div>
</div>
</div>
<br>
<div>
    <h1>Export current Rates by creating Rate Sheet</h1>
    <form action="/rate" method="post">
        <label>Calling from &nbsp;</label>
        <select name="country">
            <c:forEach var="country" items="${countries}">
                <option value="${country.countryCode}"> ${country.countryName} </option>
            </c:forEach>
        </select><br>
        <label>Services &nbsp;</label>
        <select name="service">
            <c:forEach var="item" items="${Service}">
                <option value="${item.serviceId}"> ${item.serviceName} </option>
            </c:forEach>
        </select>
        <input type="submit" value="EXPORT DATA" name="export">
    </form>
    <div class='<c:choose>
<c:when test="${errorType eq 1}">error</c:when>
<c:otherwise>success</c:otherwise>
</c:choose>'>${error} - ${filepath}</div>
    <h1>Rate List</h1>
    <table border="1" cellpadding="5" cellspacing="1">
        <tr bgcolor="#EDEDED">
            <td>rateID</td>
            <td>serviceName</td>
            <td>fromCountry</td>
            <td>destCountry</td>
            <td>peak</td>
            <td>offPeak</td>
            <td>changeDate</td>
        </tr>
        <c:forEach var="ratetmp" items="${rateTmpList}">
            <tr>
                <td>${ratetmp.rateID}</td>
                <td>${ratetmp.serviceName}</td>
                <td>${ratetmp.sourceName}</td>
                <td>${ratetmp.destName}</td>
                <td>${ratetmp.peak}</td>
                <td>${ratetmp.offPeak}</td>
                <td>${ratetmp.changeDate}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
