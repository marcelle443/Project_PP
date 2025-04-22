package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS user (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), lastName VARCHAR(100), age TINYINT)";
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery(sql).executeUpdate();
        System.out.println("Таблица пользователей создана!");
        transaction.commit();
        session.close();// комитим и подтврежданм транзакцию
        System.out.println("сессия успешно закрыта!");
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS user";
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery(sql).executeUpdate();
        System.out.println("Таблица полностью удалена!");
        transaction.commit();
        session.close();// комитим и подтврежданм транзакцию
        System.out.println("Сессия успешно закрыта!");

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {

        transaction = session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        transaction.commit();
        System.out.printf("Пользователь с именем — %s добавлен в базу данных%n", name);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            System.out.println("Сессия успешно закрыта!");
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {

            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
            System.out.printf("Пользователь с id — %s удален из базы данных%n", id);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace(System.out);
        } finally {
            session.close();
            System.out.println("Сессия успешно закрыта!");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            userList = session.createQuery("FROM User", User.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return userList;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM user";
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery(sql).executeUpdate();
        System.out.println("Пользователи из таблицы удалены!");
        transaction.commit();
        session.close();// комитим и подтврежданм транзакцию
        System.out.println("сессия успешно закрыта!");
        }
    }


