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
        String createTab = "CREATE TABLE IF NOT EXISTS userdb.user" +
                "(\n" +
                "`id` INT NOT NULL AUTO_INCREMENT, \n" +
                "`name` VARCHAR(45) NOT NULL, \n" +
                "`lastName` VARCHAR(45) NOT NULL, \n" +
                "`age` INT NOT NULL, \n" +
                "PRIMARY KEY (`id`))";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createTab)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException message:" + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        String dropTab = "DROP TABLE IF EXISTS userdb.user";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(dropTab)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException message:" + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String saveUsers = "INSERT INTO userdb.user(name, lastName, age) VALUES (?, ?, ?)";
        Connection connection = null;
        try {
            connection = Util.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(saveUsers);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("SQLException message:" + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException a) {
                a.printStackTrace();
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException a) {
                    a.printStackTrace();
                }
            }

        }
    }

    @Override
    public void removeUserById(long id) {
        String removeUser = "DELETE FROM userdb.user WHERE id=? ";
        Connection connection = null;
        try {
            connection = Util.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(removeUser);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("SQLException message:" + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException a) {
                a.printStackTrace();
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException a) {
                    a.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> listAll = new ArrayList<>();
        String getAll = "SELECT * FROM userdb.user";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAll);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                listAll.add(user);
            }
        } catch (SQLException e) {
            System.out.println("SQLException message:" + e.getMessage());
        }
        return listAll;
    }

    @Override
    public void cleanUsersTable() {
        String cleanTab = "TRUNCATE TABLE userdb.user";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(cleanTab)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException message:" + e.getMessage());
        }
    }
}

