package com.rossypotentials.controller;


import com.rossypotentials.dataAccessObject.PersonDao;
import com.rossypotentials.model.User;
import com.rossypotentials.util.ConnectionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "users", value = "/signup")
public class PersonController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User newUser = new User(firstName,lastName,email,password);
        PersonDao userDao = new PersonDao(ConnectionUtils.getConnection());
        userDao.addUser(newUser);

        response.sendRedirect("login-form.jsp");
    }
}
