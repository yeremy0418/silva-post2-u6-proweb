<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
	<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'es'}"/>
	<fmt:setBundle basename="messages"/>
	<meta charset="UTF-8">
	<title><fmt:message key="app.titulo"/></title>
	<link rel="stylesheet" href="<c:url value='/css/estilos.css'/>">
</head>
<body>
	<p>
		<fmt:message key="app.bienvenida"/>:
		<strong><c:out value="${usuarioActual.username}"/></strong>
		(<c:out value="${usuarioActual.rol}"/>)
		| <a href="<c:url value='/logout'/>"><fmt:message key="app.logout"/></a>
	</p>
	<p>
		<a href="<c:url value='/idioma?lang=es'/>">Espanol</a>
		|
		<a href="<c:url value='/idioma?lang=en'/>">English</a>
	</p>

	<h1><fmt:message key="app.titulo"/></h1>

	<c:if test="${not empty mensaje}">
		<p class="alert-success">${mensaje}</p>
	</c:if>

	<c:if test="${usuarioActual.rol == 'ADMIN'}">
		<a href="<c:url value='/productos?accion=formulario'/>"><fmt:message key="menu.nuevo"/></a>
	</c:if>

	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th><fmt:message key="tabla.nombre"/></th>
				<th><fmt:message key="tabla.categoria"/></th>
				<th><fmt:message key="tabla.precio"/></th>
				<th><fmt:message key="tabla.stock"/></th>
				<th><fmt:message key="tabla.acciones"/></th>
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
							<a href="<c:url value='/productos?accion=editar&id=${p.id}'/>"><fmt:message key="btn.editar"/></a> |
							<a href="<c:url value='/productos?accion=eliminar&id=${p.id}'/>" onclick="return confirm('<fmt:message key="msg.confirmar.eliminar"><fmt:param value="${p.nombre}"/></fmt:message>')"><fmt:message key="btn.eliminar"/></a>
						</c:if>
						<c:if test="${usuarioActual.rol != 'ADMIN'}">-</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
