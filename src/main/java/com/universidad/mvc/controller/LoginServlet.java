package com.universidad.mvc.controller;

import com.universidad.mvc.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Map<String, String> CREDS = Map.of(
            "admin", "Admin123!",
            "viewer", "View456!"
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        actualizarIdioma(req);
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        actualizarIdioma(req);

        String user = req.getParameter("username");
        String pass = req.getParameter("password");

        if (user != null && CREDS.containsKey(user) && CREDS.get(user).equals(pass)) {
            HttpSession session = req.getSession(true);
            String rol = "admin".equals(user) ? "ADMIN" : "VIEWER";
            session.setAttribute("usuarioActual", new Usuario(user, user + "@universidad.edu", rol));
            session.setMaxInactiveInterval(1800);
            resp.sendRedirect(req.getContextPath() + "/productos");
        } else {
            req.setAttribute("errorLogin", "login.error.invalidCredentials");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }

    private void actualizarIdioma(HttpServletRequest req) {
        String lang = req.getParameter("lang");
        if (lang != null && !lang.isBlank()) {
            req.getSession(true).setAttribute("lang", normalizarIdioma(lang));
            return;
        }

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("lang") == null) {
            String headerLang = req.getLocale() != null ? req.getLocale().getLanguage() : "es";
            req.getSession(true).setAttribute("lang", normalizarIdioma(headerLang));
        }
    }

    private String normalizarIdioma(String lang) {
        String value = lang.toLowerCase(Locale.ROOT);
        return "en".equals(value) ? "en" : "es";
    }
}
