<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="i18n.messages"/>
    <meta charset="UTF-8">
    <title><fmt:message key="login.title"/></title>
    <link rel="stylesheet" href="<c:url value='/css/estilos.css'/>">
</head>
<body>
    <h1><fmt:message key="login.title"/></h1>

    <p>
        <fmt:message key="app.language"/>:
        <a href="<c:url value='/login?lang=es'/>"><fmt:message key="app.spanish"/></a>
        |
        <a href="<c:url value='/login?lang=en'/>"><fmt:message key="app.english"/></a>
    </p>

    <c:if test="${not empty errorLogin}">
        <p class="alert-error"><fmt:message key="${errorLogin}"/></p>
    </c:if>

    <form method="post" action="<c:url value='/login'/>">
        <input type="hidden" name="lang" value="${sessionScope.lang}">

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
