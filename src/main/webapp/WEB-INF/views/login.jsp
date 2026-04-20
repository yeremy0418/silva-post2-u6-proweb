<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'es'}"/>
    <fmt:setBundle basename="messages"/>
    <meta charset="UTF-8">
    <title><fmt:message key="login.title"/></title>
    <link rel="stylesheet" href="<c:url value='/css/estilos.css'/>">
</head>
<body>
    <h1><fmt:message key="login.title"/></h1>

    <p>
        <a href="<c:url value='/idioma?lang=es'/>">Espanol</a>
        |
        <a href="<c:url value='/idioma?lang=en'/>">English</a>
    </p>

    <c:if test="${not empty errorLogin}">
        <p class="alert-error"><fmt:message key="${errorLogin}"/></p>
    </c:if>

    <form method="post" action="<c:url value='/login'/>">
        <label><fmt:message key="login.username"/>:
            <input type="text" name="username" required>
        </label>

        <label><fmt:message key="login.password"/>:
            <input type="password" name="password" required>
        </label>

        <button type="submit"><fmt:message key="login.submit"/></button>
    </form>
</body>
</html>
