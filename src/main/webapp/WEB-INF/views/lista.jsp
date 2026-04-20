<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<title>Inventario de Productos</title>
	<link rel="stylesheet" href="<c:url value='/css/estilos.css'/>">
</head>
<body>
	<h1>Inventario de Productos</h1>

	<c:if test="${not empty mensaje}">
		<p class="alert-success">${mensaje}</p>
	</c:if>

	<a href="<c:url value='/productos?accion=formulario'/>">+ Nuevo Producto</a>

	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>Nombre</th>
				<th>Categoria</th>
				<th>Precio</th>
				<th>Stock</th>
				<th>Acciones</th>
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
						<a href="<c:url value='/productos?accion=editar&id=${p.id}'/>">Editar</a> |
						<a href="<c:url value='/productos?accion=eliminar&id=${p.id}'/>" onclick="return confirm('Eliminar ${p.nombre}?')">Eliminar</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
