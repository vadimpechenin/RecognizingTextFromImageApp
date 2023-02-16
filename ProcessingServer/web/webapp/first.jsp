<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Главная страница</title>
</head>
<body>
    <h1>Testing JSP</h1>
    <%= "Приветствую на сервисе распознавания текста"%>
    <%
        java.util.Date now = new java.util.Date();
        String someString = "Текущая дата : " + now;
    %>
    <%= someString%>
</body>
</html>
