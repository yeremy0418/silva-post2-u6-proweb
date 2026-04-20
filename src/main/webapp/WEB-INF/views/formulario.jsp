<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
	<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'es'}"/>
	<fmt:setBundle basename="messages"/>
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
			<ul>
				<c:forEach var="e" items="${errores}">
					<li>${e.value}</li>
				</c:forEach>
			</ul>
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

		<label><fmt:message key="tabla.nombre"/>:
			<input type="text" name="nombre" value="<c:out value='${not empty nombre ? nombre : producto.nombre}'/>" class="${not empty errores.nombre ? 'input-error' : ''}" required>
			<c:if test="${not empty errores.nombre}">
				<span class="campo-error">${errores.nombre}</span>
			</c:if>
		</label>

		<label><fmt:message key="tabla.categoria"/>:
			<input type="text" name="categoria" value="<c:out value='${not empty categoria ? categoria : producto.categoria}'/>">
		</label>

		<label><fmt:message key="tabla.precio"/>:
			<input type="number" name="precio" step="0.01" min="0" value="<c:out value='${not empty precio ? precio : producto.precio}'/>" class="${not empty errores.precio ? 'input-error' : ''}" required>
			<c:if test="${not empty errores.precio}">
				<span class="campo-error">${errores.precio}</span>
			</c:if>
		</label>

		<label><fmt:message key="tabla.stock"/>:
			<input type="number" name="stock" min="0" value="<c:out value='${not empty stock ? stock : producto.stock}'/>" class="${not empty errores.stock ? 'input-error' : ''}" required>
			<c:if test="${not empty errores.stock}">
				<span class="campo-error">${errores.stock}</span>
			</c:if>
		</label>

		<button type="submit">
			<c:choose>
				<c:when test="${empty producto}"><fmt:message key="form.save"/></c:when>
				<c:otherwise><fmt:message key="form.update"/></c:otherwise>
			</c:choose>
		</button>
		<a href="<c:url value='/productos'/>"><fmt:message key="form.cancel"/></a>
	</form>
</body>
</html>
