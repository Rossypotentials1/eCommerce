package com.rossypotentials.dataAccessObject;

import com.rossypotentials.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDao {
    private Connection connection;

    public PersonDao(Connection connection) {
        this.connection = connection;
    }

    public void addUser(User user) {
        try {
            // Check if the email already exists before adding a new user
            if (emailExists(user.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO customerT (firstName, lastName, email, password) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.executeUpdate();

            // Close the PreparedStatement
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    public boolean emailExists(String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM customerT WHERE email = ?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // Close the ResultSet and PreparedStatement
                resultSet.close();
                preparedStatement.close();
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
        return false;
    }
}
