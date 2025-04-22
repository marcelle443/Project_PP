package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao { // мой коммент * реализация методов которые лежат в интрефейсе UserDAO

    @Override
    public void createUsersTable() { //создание таблицы
        String sql = "CREATE TABLE IF NOT EXISTS user (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), lastName VARCHAR(100), age TINYINT)";
        try (Connection connection = new Util().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица пользователей создана!");
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void dropUsersTable() { // удаление данных из таблицы и самой таблицы
        String sql = "DROP TABLE IF EXISTS user";
        try (Connection connection = new Util().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица пользователей удалена!");
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) { //внести в таблицу данные о пользователе
        String sql = "INSERT INTO user (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = new Util().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем — %s добавлен в базу данных%n", name);
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void removeUserById(long id) { //удалить пользователя по id
        String sql = "DELETE FROM user WHERE id = ?";
        try (Connection connection = new Util().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.printf("User с id = %d удалён из базы данных%n", id);
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public List<User> getAllUsers() { // получить данные о пользователях
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age FROM user";
        try (Connection connection = new Util().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User("Malay", "Kalay", 1);
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() { //удалить данные из таблицы, кроме самой таблицы
        String sql = "DELETE FROM user";
        try (Connection connection = new Util().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица пользователей очищена!");
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}
