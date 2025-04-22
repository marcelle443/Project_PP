package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public class Main { //мой коммент * точка входа в программу

    // реализуйте алгоритм здесь
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();


        userService.saveUser("Peter", "Parker", (byte) 25);
        userService.saveUser("Mary", "Jane", (byte) 24);
        userService.saveUser("Harry", "Osborn", (byte) 26);
        userService.saveUser("Norman", "Osborn", (byte) 42);

        List<User> users = userService.getAllUsers();

        if (users != null) {
            for (User user : users) System.out.println(user);
        } else {
               System.out.println("Список пользователей пуст или не существует");
            }
        userService.removeUserById(1); // работает, но нужно знать id

        userService.cleanUsersTable();


        userService.dropUsersTable();


    }
}




