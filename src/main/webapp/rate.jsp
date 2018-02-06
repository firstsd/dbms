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
</head>
<body>
<a href="/rate?export=rate">Export current Rates by creating Rate Sheet</a>
<div>${error}</div>
Rate List
<table>
    <tr>
        <td>rateID</td>
        <td>serviceName</td>
        <td>fromCountry</td>
        <td>destCountry</td>
        <td>peak</td>
        <td>offPeak</td>
        <td>changeDate</td>
    </tr>
    <c:forEach items="" var="">
        <tr>

        </tr>
    </c:forEach>
</table>
</body>
</html>
