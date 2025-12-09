package com.college.utils;

import com.college.entities.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class DbUtils {
    private Connection connection;

    @PostConstruct
    public void init() {
        try {
            String host = "localhost";
            String username = "root";
            String password = "1234";
            String schema = "project_2025";
            int port = 3306;
            String url = "jdbc:mysql://"
                    + host + ":" + port + "/"
                    + schema;
            this.connection =
                    DriverManager.getConnection(url, username, password);
            System.out.println("Connection established");
        } catch (SQLException e) {
            System.out.println("Failed to create db connection");
            e.printStackTrace();
        }
    }

    public void insertUser(User user) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(
                    "INSERT INTO users (username, password) " +
                            "VALUE (?, ?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement preparedStatement =
                    this.connection.prepareStatement("SELECT id, username, password FROM users");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                User user = new User(username, password);
                user.setId(id);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;

    }

    public boolean doesUsernameExist(String username) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(
                    "SELECT id FROM users WHERE username = ?"
            );
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(String username, String password) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("" +
                    "SELECT id, username, password FROM users WHERE" +
                    " username = ? AND password=?;"
            );
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet resultSet=preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(username,password);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete (int id) {
        try {
            PreparedStatement preparedStatement =
                    this.connection.prepareStatement("DELETE FROM users WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
