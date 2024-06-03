package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoHibernateImpl();
        UserService userService = new UserServiceImpl(userDao);
        userService.createUsersTable();
        userService.saveUser("Petya", "Petrov", (byte) 19);
        userService.saveUser("Vasya", "Metrov", (byte) 22);
        userService.saveUser("Kostya", "Uetrov", (byte) 29);
        userService.saveUser("Zhenya", "Putov", (byte) 39);
        userService.removeUserById(3);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();// реализуйте алгоритм здесь
    }
}
