package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession();) {
            Transaction transaction = null;
                    transaction =  session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)";
            int updateCount = session.createSQLQuery(sql).executeUpdate();
            transaction.commit();

        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession();) {
            Transaction transaction = null;
            transaction =  session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS users";
            int updateCount = session.createSQLQuery(sql).executeUpdate();
            transaction.commit();

        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                session.save(user);
                transaction.commit();
            } catch (Exception e) {
                // Обработать исключение, например, откатить транзакцию и записать ошибку в лог
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }



    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).list();
        }
    }

//    public List<User> getAllUsers() {
//        try (Session session = Util.getSessionFactory().openSession()) {
//            Query query = (Query) session.createQuery("FROM User");  // Используйте имя класса User
//            List<User> list = query.getResultList();
//            return list;
//        }


//        }


    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession();) {
            Transaction transaction = null;
            transaction =  session.beginTransaction();
            String sql = "DELETE FROM users";
            int updateCount = session.createSQLQuery(sql).executeUpdate();
            transaction.commit();

        }

    }


}
