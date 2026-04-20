<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
	<fmt:setLocale value="${sessionScope.lang}"/>
	<fmt:setBundle basename="i18n.messages"/>
	<meta charset="UTF-8">
	<title>
		<c:choose>
			<c:when test="${empty producto}"><fmt:message key="form.new.title"/></c:when>
			<c:otherwise><fmt:message key="form.edit.title"/></c:otherwise>
		</c:choose>
	</title>
	<link rel="stylesheet" href="<c:url value='/css/estilos.css'/>">
</head>
<body>
	<h1>
		<c:choose>
			<c:when test="${empty producto}"><fmt:message key="form.new.header"/></c:when>
			<c:otherwise><fmt:message key="form.edit.header"/></c:otherwise>
		</c:choose>
	</h1>

	<c:if test="${not empty errores}">
		<div class="alert-error">
			<c:forEach var="e" items="${errores}">
				<p>${e.value}</p>
			</c:forEach>
		</div>
	</c:if>

	<form method="post" action="<c:url value='/productos'/>">
		<c:if test="${not empty producto}">
			<input type="hidden" name="id" value="${producto.id}">
			<input type="hidden" name="accion" value="actualizar">
		</c:if>
		<c:if test="${empty producto}">
			<input type="hidden" name="accion" value="guardar">
		</c:if>
		<input type="hidden" name="lang" value="${sessionScope.lang}">

		<label><fmt:message key="producto.nombre"/>:
			<input type="text" name="nombre" required value="<c:out value='${producto.nombre}'/>">
		</label>

		<label><fmt:message key="producto.categoria"/>:
			<input type="text" name="categoria" value="<c:out value='${producto.categoria}'/>">
		</label>

		<label><fmt:message key="producto.precio"/>:
			<input type="number" name="precio" step="0.01" min="0" required value="${producto.precio}">
		</label>

		<label><fmt:message key="producto.stock"/>:
			<input type="number" name="stock" min="0" required value="${producto.stock}">
		</label>

		<button type="submit">
			<c:choose>
				<c:when test="${empty producto}"><fmt:message key="form.save"/></c:when>
				<c:otherwise><fmt:message key="form.update"/></c:otherwise>
			</c:choose>
		</button>
		<a href="<c:url value='/productos?lang=${sessionScope.lang}'/>"><fmt:message key="form.cancel"/></a>
	</form>
</body>
</html>
