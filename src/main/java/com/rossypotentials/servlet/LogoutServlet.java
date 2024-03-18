package com.rossypotentials.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/log-out")

public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(LogoutServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.INFO, "GET request received for /log-out");
        processLogout(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.INFO, "POST request received for /log-out");
        processLogout(request, response);
    }

    private void processLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            if (request.getSession().getAttribute("auth") != null) {
                logger.log(Level.INFO, "User is logged in. Performing logout.");
                request.getSession().removeAttribute("auth");
                response.sendRedirect("login.jsp");
            } else {
                logger.log(Level.INFO, "User is not logged in. Redirecting to index.jsp.");
                response.sendRedirect("index.jsp");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred during logout process: " + e.getMessage(), e);
            throw e;
        }
    }
}
