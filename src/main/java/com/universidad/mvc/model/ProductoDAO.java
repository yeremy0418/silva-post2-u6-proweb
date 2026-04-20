package com.universidad.mvc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductoDAO {
	private static final List<Producto> lista = new ArrayList<>();
	private static int contador = 4;

	static {
		lista.add(new Producto(1, "Laptop Pro 15", "Computadoras", 1299.99, 10));
		lista.add(new Producto(2, "Monitor 27\" 4K", "Monitores", 549.00, 25));
		lista.add(new Producto(3, "Teclado Mecanico", "Perifericos", 89.99, 50));
	}

	public List<Producto> findAll() {
		return Collections.unmodifiableList(lista);
	}

	public Producto findById(int id) {
		return lista.stream()
				.filter(p -> p.getId() == id)
				.findFirst()
				.orElse(null);
	}

	public void save(Producto p) {
		p.setId(++contador);
		lista.add(p);
	}

	public void update(Producto p) {
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId() == p.getId()) {
				lista.set(i, p);
				return;
			}
		}
	}

	public void delete(int id) {
		lista.removeIf(p -> p.getId() == id);
	}
}
