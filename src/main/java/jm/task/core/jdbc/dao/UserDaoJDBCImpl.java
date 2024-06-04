package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        String sql = """
                CREATE TABLE users (
                 id INT PRIMARY KEY AUTO_INCREMENT,
                 username VARCHAR(40) NOT NULL,
                 lastname VARCHAR(40),
                 age INT
                  );
                """;
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = """
                DROP TABLE users;
                 """;
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = """
                INSERT INTO users 
                (username, lastname, age)
                VALUES (?, ?, ?);
                """;
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = """
                DELETE  FROM users
                WHERE users.id=?;
                """;
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        String sql = """
                SELECT
                users.username AS username,
                users.lastname AS lastname,
                users.age AS age
                FROM users
                      """;
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<User> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(new User(
                        resultSet.getString("username"),
                        resultSet.getString("lastname"),
                        resultSet.getByte("age")
                ));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void cleanUsersTable() {
        String sql = """
                TRUNCATE TABLE users;
                """;
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table was cleaned");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
