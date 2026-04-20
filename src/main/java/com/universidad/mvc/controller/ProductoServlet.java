package com.universidad.mvc.controller;

import com.universidad.mvc.model.Producto;
import com.universidad.mvc.service.ProductoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {
	private final ProductoService service = new ProductoService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String accion = req.getParameter("accion");
		if (accion == null) {
			accion = "listar";
		}

		switch (accion) {
			case "listar":
				listar(req, resp);
				break;
			case "formulario":
				mostrarFormulario(req, resp);
				break;
			case "editar":
				mostrarEdicion(req, resp);
				break;
			case "eliminar":
				eliminar(req, resp);
				break;
			default:
				resp.sendError(404);
				break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String accion = req.getParameter("accion");

		if ("guardar".equals(accion)) {
			guardar(req, resp);
		} else if ("actualizar".equals(accion)) {
			actualizar(req, resp);
		} else {
			resp.sendError(400);
		}
	}

	private void listar(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("productos", service.obtenerTodos());
		String msg = req.getParameter("mensaje");
		if (msg != null) {
			req.setAttribute("mensaje", msg);
		}
		forward(req, resp, "/WEB-INF/views/lista.jsp");
	}

	private void mostrarFormulario(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		forward(req, resp, "/WEB-INF/views/formulario.jsp");
	}

	private void mostrarEdicion(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		req.setAttribute("producto", service.obtenerPorId(id));
		forward(req, resp, "/WEB-INF/views/formulario.jsp");
	}

	private void guardar(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Producto p = extraerProducto(req, 0);
		service.guardar(p);
		resp.sendRedirect(req.getContextPath() + "/productos?mensaje=Producto+guardado+exitosamente");
	}

	private void actualizar(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		Producto p = extraerProducto(req, id);
		service.actualizar(p);
		resp.sendRedirect(req.getContextPath() + "/productos?mensaje=Producto+actualizado");
	}

	private void eliminar(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		service.eliminar(id);
		resp.sendRedirect(req.getContextPath() + "/productos?mensaje=Producto+eliminado");
	}

	private Producto extraerProducto(HttpServletRequest req, int id) {
		return new Producto(
				id,
				req.getParameter("nombre"),
				req.getParameter("categoria"),
				Double.parseDouble(req.getParameter("precio")),
				Integer.parseInt(req.getParameter("stock"))
		);
	}

	private void forward(HttpServletRequest req, HttpServletResponse resp, String path)
			throws ServletException, IOException {
		req.getRequestDispatcher(path).forward(req, resp);
	}
}
