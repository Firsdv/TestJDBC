package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
        UserDao userDao = new UserDaoJDBCImpl();

    }
    public void createUsersTable() throws SQLException {

        PreparedStatement preparedStatement = null;
        String sql = "CREATE TABLE IF NOT EXISTS user (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(45) NOT NULL," +
                "lastName VARCHAR(45) NOT NULL," +
                "age TINYINT UNSIGNED NOT NULL" + ")";
        try {
            preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (getConnection() != null) {
                getConnection().close();
            }
        }

    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS user";
        try (Statement statement = getConnection().createStatement()) {  // try-with-resources
            statement.executeUpdate(sql);
            System.out.println("Table 'user' dropped.");

        } catch (SQLException e) {
            System.err.println("Error dropping table.");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO user (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User " + name + " saved.");

        } catch (SQLException e) {
            System.err.println("Error saving user.");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User with id " + id + " removed.");
            } else {
                System.out.println("User with id " + id + " not found.");
            }

        } catch (SQLException e) {
            System.err.println("Error removing user.");
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age FROM user";
        Statement statement = null;
        try {
            statement= getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (getConnection() != null) {
                getConnection().close();
            }
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE user";
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table 'user' cleaned.");

        } catch (SQLException e) {
            System.err.println("Error cleaning table.");
            e.printStackTrace();
        }
    }
    public void closeConnection() {
        try {
            if (getConnection() != null && !getConnection().isClosed()) {
                getConnection().close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection.");
            e.printStackTrace();
        }
    }
}
