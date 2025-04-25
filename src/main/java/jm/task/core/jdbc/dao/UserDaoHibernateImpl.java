package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import org.hibernate.Transaction;


import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = null;
        Transaction transaction = null;
        try {
            String sql = "CREATE TABLE IF NOT EXISTS user (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), lastName VARCHAR(100), age TINYINT)";
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Таблица пользователей создана!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(System.out);
            session.close();// комитим и подтврежданм транзакцию
            System.out.println("сессия успешно закрыта!");
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        Transaction transaction = null;
        try {
            String sql = "DROP TABLE IF EXISTS user";
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit(); // комитим и подтврежданм транзакцию
            System.out.println("Таблица полностью удалена!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(System.out);
            session.close();
            System.out.println("Сессия успешно закрыта!");
        }
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
            e.printStackTrace(System.out);
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
            if (transaction != null)
                transaction.rollback();
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
        Session session = null;
        Transaction transaction = null;
        try {
            String sql = "DELETE FROM user";
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit(); // комитим и подтврежданм транзакцию
            System.out.println("Пользователи из таблицы удалены!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(System.out);
        } finally {
            session.close();
            System.out.println("Сессия успешно закрыта!");
        }

    }
}



