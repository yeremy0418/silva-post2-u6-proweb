package com.universidad.mvc.service;

import com.universidad.mvc.model.Producto;
import com.universidad.mvc.model.ProductoDAO;

import java.util.List;

public class ProductoService {
	private final ProductoDAO dao = new ProductoDAO();

	public List<Producto> obtenerTodos() {
		return dao.findAll();
	}

	public Producto obtenerPorId(int id) {
		return dao.findById(id);
	}

	public void guardar(Producto p) {
		if (p.getNombre() == null || p.getNombre().trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre es obligatorio.");
		}
		if (p.getPrecio() < 0) {
			throw new IllegalArgumentException("El precio no puede ser negativo.");
		}
		dao.save(p);
	}

	public void actualizar(Producto p) {
		if (dao.findById(p.getId()) == null) {
			throw new IllegalArgumentException("Producto no encontrado.");
		}
		dao.update(p);
	}

	public void eliminar(int id) {
		dao.delete(id);
	}
}
