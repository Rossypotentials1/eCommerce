package com.rossypotentials.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;

@WebServlet (name = "adminServlet", value="/admin-servlet")
public class AdminLoginServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/PersonDB";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "Mmmmmmmm.";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String adminToken = request.getParameter("adminToken");

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                String sql = "SELECT * FROM newAdmin WHERE email= ? AND password=? AND adminToken=?";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setString(1, email);
                    statement.setString(2, password);
                    statement.setString(3, adminToken);
                    try (ResultSet result = statement.executeQuery()) {
                        if (result.next()) {
                            // User authenticated
                            //String userEmail = result.getString("email");
                           // request.setAttribute("email", userEmail);
                            HttpSession session = request.getSession();
                            session.setAttribute("adminEmail", email);
                            response.sendRedirect("adminDashboard.jsp");
                            //request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);

                        } else {

                            response.sendRedirect("Not a registered email, create an account");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("signup-error.jsp");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("signup-error.jsp");
        }

    }
}
