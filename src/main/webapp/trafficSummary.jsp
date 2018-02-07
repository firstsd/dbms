<%--
  Created by IntelliJ IDEA.
  User: Gundee
  Date: 2/6/2018
  Time: 12:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body>
<div class="main"><a href="http://localhost:8080">GO BACK</a></div>
<form method="post" action="">
    <fieldset>
        <legend><h3>Generate Traffic Summary</h3></legend>
        <label>Start date &nbsp;</label>
        <input type="text" placeholder="YYYY-MM-DD"><br>
        <label>End date &nbsp;</label>
        <input type="text" placeholder="YYYY-MM-DD"><br>
        <input type="submit" value="Generate">
    </fieldset>
</form>
</body>
</html>
