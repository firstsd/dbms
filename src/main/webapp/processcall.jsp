<%--
  Created by IntelliJ IDEA.
  User: SD
  Date: 2/6/2018
  Time: 11:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Process Call</title>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body>
<div class="main"><a href="http://localhost:8080">GO BACK</a></div>
<h1>Process Call Import</h1>
<form action="/processcall" method="post">
    <input type="file" name="processFile">
    <input type="submit" value="IMPORT">
</form>
<div class='<c:choose>
<c:when test="${errorType eq 1}">error</c:when>
<c:otherwise>success</c:otherwise>
</c:choose>'>${error} - ${filepath}</div>
<h1>Process Call List</h1>
<table border="1" cellpadding="5" cellspacing="1">
    <tr bgcolor="#EDEDED">
        <td>callID</td>
        <td>fromCountry</td>
        <td>toCountry</td>
        <td>fromPhoneNo</td>
        <td>toPhoneNo</td>
        <td>duration</td>
        <td>callDate</td>
        <td>callTime</td>
    </tr>
    <c:forEach var="calls" items="${processCall}">
        <tr>
            <td>${calls.callID}</td>
            <td>${calls.fromCountry}</td>
            <td>${calls.toCountry}</td>
            <td>${calls.fromPhoneNo}</td>
            <td>${calls.toPhoneNo}</td>
            <td>${calls.duration}</td>
            <td>${calls.callDate}</td>
            <td>${calls.callTime}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
