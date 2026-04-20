package com.universidad.mvc.controller;

import com.universidad.mvc.model.Producto;
import com.universidad.mvc.model.Usuario;
import com.universidad.mvc.service.ProductoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {
	private final ProductoService service = new ProductoService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (!verificarSesion(req, resp)) {
			return;
		}

		String accion = req.getParameter("accion");
		if (accion == null) {
			accion = "listar";
		}

		if (esAccionDeEscritura(accion) && !esAdmin(req)) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
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
		if (!verificarSesion(req, resp)) {
			return;
		}

		req.setCharacterEncoding("UTF-8");
		String accion = req.getParameter("accion");
		if (esAccionDeEscritura(accion) && !esAdmin(req)) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

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
		req.setAttribute("usuarioActual", obtenerUsuarioActual(req));
		req.setAttribute("productos", service.obtenerTodos());
		String msg = req.getParameter("mensaje");
		if (msg != null) {
			req.setAttribute("mensaje", resolverMensaje(req, msg));
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
		Producto p = service.obtenerPorId(id);
		req.setAttribute("producto", p);
		req.setAttribute("nombre", p == null ? "" : p.getNombre());
		req.setAttribute("precio", p == null ? "" : String.valueOf(p.getPrecio()));
		req.setAttribute("stock", p == null ? "" : String.valueOf(p.getStock()));
		req.setAttribute("categoria", p == null ? "" : p.getCategoria());
		forward(req, resp, "/WEB-INF/views/formulario.jsp");
	}

	private void guardar(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		Map<String, String> errores = new LinkedHashMap<>();
		Producto p = extraerProducto(req, 0, errores);

		req.setAttribute("nombre", req.getParameter("nombre"));
		req.setAttribute("precio", req.getParameter("precio"));
		req.setAttribute("stock", req.getParameter("stock"));
		req.setAttribute("categoria", req.getParameter("categoria"));

		if (!errores.isEmpty()) {
			req.setAttribute("errores", errores);
			req.setAttribute("producto", p);
			forward(req, resp, "/WEB-INF/views/formulario.jsp");
			return;
		}

		service.guardar(p);
		resp.sendRedirect(req.getContextPath() + "/productos?mensaje=msg.guardado");
	}

	private void actualizar(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		int id = Integer.parseInt(req.getParameter("id"));
		Map<String, String> errores = new LinkedHashMap<>();
		Producto p = extraerProducto(req, id, errores);

		req.setAttribute("nombre", req.getParameter("nombre"));
		req.setAttribute("precio", req.getParameter("precio"));
		req.setAttribute("stock", req.getParameter("stock"));
		req.setAttribute("categoria", req.getParameter("categoria"));

		if (!errores.isEmpty()) {
			req.setAttribute("errores", errores);
			req.setAttribute("producto", p);
			forward(req, resp, "/WEB-INF/views/formulario.jsp");
			return;
		}

		service.actualizar(p);
		resp.sendRedirect(req.getContextPath() + "/productos?mensaje=msg.actualizado");
	}

	private void eliminar(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		service.eliminar(id);
		resp.sendRedirect(req.getContextPath() + "/productos?mensaje=msg.eliminado");
	}

	private Producto extraerProducto(HttpServletRequest req, int id, Map<String, String> errores) {
		ResourceBundle bundle = obtenerBundle(req);

		String nombre = limpiar(req.getParameter("nombre"));
		String categoria = limpiar(req.getParameter("categoria"));
		String precioTxt = limpiar(req.getParameter("precio"));
		String stockTxt = limpiar(req.getParameter("stock"));

		if (nombre.isEmpty()) {
			errores.put("nombre", bundle.getString("validation.nombre.required"));
		} else if (nombre.length() > 100) {
			errores.put("nombre", bundle.getString("validation.nombre.max"));
		}

		double precio = 0;
		if (precioTxt.isEmpty()) {
			errores.put("precio", bundle.getString("validation.precio.required"));
		} else {
			try {
				precio = Double.parseDouble(precioTxt);
				if (precio < 0) {
					errores.put("precio", bundle.getString("validation.precio.min"));
				}
			} catch (NumberFormatException ex) {
				errores.put("precio", bundle.getString("validation.precio.numeric"));
			}
		}

		int stock = 0;
		if (stockTxt.isEmpty()) {
			errores.put("stock", bundle.getString("validation.stock.required"));
		} else {
			try {
				stock = Integer.parseInt(stockTxt);
				if (stock < 0) {
					errores.put("stock", bundle.getString("validation.stock.min"));
				}
			} catch (NumberFormatException ex) {
				errores.put("stock", bundle.getString("validation.stock.numeric"));
			}
		}

		return new Producto(
				id,
				nombre,
				categoria,
				precio,
				stock
		);
	}

	private boolean verificarSesion(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		HttpSession s = req.getSession(false);
		if (s == null || s.getAttribute("usuarioActual") == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return false;
		}
		return true;
	}

	private boolean esAccionDeEscritura(String accion) {
		return "formulario".equals(accion)
				|| "guardar".equals(accion)
				|| "editar".equals(accion)
				|| "actualizar".equals(accion)
				|| "eliminar".equals(accion);
	}

	private boolean esAdmin(HttpServletRequest req) {
		Usuario usuario = obtenerUsuarioActual(req);
		return usuario != null && "ADMIN".equals(usuario.getRol());
	}

	private Usuario obtenerUsuarioActual(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session == null) {
			return null;
		}

		Object attr = session.getAttribute("usuarioActual");
		if (attr instanceof Usuario) {
			return (Usuario) attr;
		}
		return null;
	}

	private ResourceBundle obtenerBundle(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		Locale locale = Locale.forLanguageTag("es");
		if (session != null) {
			Object localeAttr = session.getAttribute("locale");
			if (localeAttr instanceof Locale) {
				locale = (Locale) localeAttr;
			}
		}
		return ResourceBundle.getBundle("messages", locale);
	}

	private String resolverMensaje(HttpServletRequest req, String key) {
		ResourceBundle bundle = obtenerBundle(req);
		if (bundle.containsKey(key)) {
			return bundle.getString(key);
		}
		return key;
	}

	private String limpiar(String value) {
		return value == null ? "" : value.trim();
	}

	private void forward(HttpServletRequest req, HttpServletResponse resp, String path)
			throws ServletException, IOException {
		req.getRequestDispatcher(path).forward(req, resp);
	}
}
