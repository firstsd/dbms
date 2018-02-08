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
    <title>Bill</title>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body>
<div class="main"><a href="http://localhost:8080">GO BACK</a></div>
<div>
    <form method="post" action="/bill">
        <fieldset>
            <legend><h3>ADD CUSTOMER</h3></legend>
            <input type="text" placeholder="Customer phone number" name="phoneNo"><br>
            <label>Start date</label>
            <input type="text" placeholder="YYYY-MM-DD" name="startDate"><br>
            <label>End date</label>
            <input type="text" placeholder="YYYY-MM-DD" name="endDate"><br>
            <input type="submit" value="Create bill">
        </fieldset>
    </form>
</div>
<div class='<c:choose>
<c:when test="${errorType eq 1}">error</c:when>
<c:otherwise>success</c:otherwise>
</c:choose>'>${error} - ${filepath}
</div>
</body>
</html>
