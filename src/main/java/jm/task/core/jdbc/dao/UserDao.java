package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;

public interface UserDao { //мой коммент *  интерфейс DAO — это Data Access Object необходим для работы с БД.
    void createUsersTable(); //Создание таблицы для User(ов)

    void dropUsersTable(); //Удаление таблицы User(ов)

    void saveUser(String name, String lastName, byte age); //Добавление User в таблицу

    void removeUserById(long id); //Удаление User из таблицы (по id)

    List<User> getAllUsers(); //Получение всех User(ов) из таблицы

    void cleanUsersTable(); //Очистка содержания таблицы
}
