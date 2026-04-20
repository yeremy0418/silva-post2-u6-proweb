<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
	<fmt:setLocale value="${sessionScope.lang}"/>
	<fmt:setBundle basename="i18n.messages"/>
	<meta charset="UTF-8">
	<title><fmt:message key="producto.list.title"/></title>
	<link rel="stylesheet" href="<c:url value='/css/estilos.css'/>">
</head>
<body>
	<p>
		<fmt:message key="app.welcome"/>:
		<strong><c:out value="${usuarioActual.username}"/></strong>
		(<c:out value="${usuarioActual.rol}"/>)
		| <a href="<c:url value='/logout'/>"><fmt:message key="app.logout"/></a>
	</p>
	<p>
		<fmt:message key="app.language"/>:
		<a href="<c:url value='/productos?lang=es'/>"><fmt:message key="app.spanish"/></a>
		|
		<a href="<c:url value='/productos?lang=en'/>"><fmt:message key="app.english"/></a>
	</p>

	<h1><fmt:message key="producto.list.title"/></h1>

	<c:if test="${not empty mensaje}">
		<p class="alert-success">${mensaje}</p>
	</c:if>

	<c:if test="${usuarioActual.rol == 'ADMIN'}">
		<a href="<c:url value='/productos?accion=formulario&lang=${sessionScope.lang}'/>"><fmt:message key="producto.new"/></a>
	</c:if>

	<table>
		<thead>
			<tr>
				<th><fmt:message key="producto.id"/></th>
				<th><fmt:message key="producto.nombre"/></th>
				<th><fmt:message key="producto.categoria"/></th>
				<th><fmt:message key="producto.precio"/></th>
				<th><fmt:message key="producto.stock"/></th>
				<th><fmt:message key="producto.acciones"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="p" items="${productos}" varStatus="s">
				<tr class="${s.index % 2 == 0 ? 'par' : 'impar'}">
					<td>${p.id}</td>
					<td><c:out value="${p.nombre}"/></td>
					<td><c:out value="${p.categoria}"/></td>
					<td><fmt:formatNumber value="${p.precio}" type="currency" currencySymbol="$"/></td>
					<td>${p.stock}</td>
					<td>
						<c:if test="${usuarioActual.rol == 'ADMIN'}">
							<a href="<c:url value='/productos?accion=editar&id=${p.id}&lang=${sessionScope.lang}'/>"><fmt:message key="producto.edit"/></a> |
							<a href="<c:url value='/productos?accion=eliminar&id=${p.id}&lang=${sessionScope.lang}'/>" onclick="return confirm('<fmt:message key="producto.delete.confirm"><fmt:param value="${p.nombre}"/></fmt:message>')"><fmt:message key="producto.delete"/></a>
						</c:if>
						<c:if test="${usuarioActual.rol != 'ADMIN'}">-</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
