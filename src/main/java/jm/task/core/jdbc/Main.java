package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь

        Util.getConnection();
        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();
        userDao.saveUser("Name1", "lastName1", (byte) 20);
        userDao.saveUser("Name2", "lastName2", (byte) 25);
        userDao.saveUser("Name3", "lastName3", (byte) 31);
        userDao.saveUser("Name4", "lastName4", (byte) 38);

        userDao.removeUserById(1);

        List<User> userList = userDao.getAllUsers();
        for (User user : userList) {
            System.out.println(user);
        }

        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}

