package com.alexey.shop.dao;

import com.alexey.shop.model.User;
import com.alexey.shop.util.SessionFactoryUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UsersDao {
    private final SessionFactoryUtils sessionFactoryUtils;

    public User findById(Long id) {
        Session session = sessionFactoryUtils.getSession();
        session.getTransaction().begin();
        User user = session.find(User.class, id);
        session.getTransaction().commit();
        return user;
    }

    public List<User> findAll() {
        Session session = sessionFactoryUtils.getSession();
        session.getTransaction().begin();
        List list = session.createQuery("select u from User u order by u.id asc", User.class).getResultList();
        session.getTransaction().commit();
        return list;
    }

    public void deleteById(Long id) {
        Session session = sessionFactoryUtils.getSession();
        session.getTransaction().begin();
        User user = new User();
        user.setId(id);
        session.remove(user);
        session.getTransaction().commit();

    }

    public User saveOrUpdate(User user) {
        Session session = sessionFactoryUtils.getSession();
        session.getTransaction().begin();
        session.saveOrUpdate(user);
        session.getTransaction().commit();
        return user;
    }
}
