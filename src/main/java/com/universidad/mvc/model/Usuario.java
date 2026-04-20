package com.universidad.mvc.model;

public class Usuario {
    private final String username;
    private final String email;
    private final String rol; // ADMIN o VIEWER

    public Usuario(String username, String email, String rol) {
        this.username = username;
        this.email = email;
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRol() {
        return rol;
    }
}
