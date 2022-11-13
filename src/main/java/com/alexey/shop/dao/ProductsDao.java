package com.alexey.shop.dao;


import com.alexey.shop.model.Product;
import com.alexey.shop.util.SessionFactoryUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductsDao {

    private final SessionFactoryUtils sessionFactoryUtils;

    public Product findById(Long id) {
        Session session = sessionFactoryUtils.getSession();
        session.getTransaction().begin();
        Product product = session.find(Product.class, id);
        session.getTransaction().commit();
        return product;
    }

    public List<Product> findAll() {
        Session session = sessionFactoryUtils.getSession();
        session.getTransaction().begin();
        List list = session.createQuery("select p from Product p order by p.id asc", Product.class).getResultList();
        session.getTransaction().commit();
        return list;
    }

    public void deleteById(Long id) {
        Session session = sessionFactoryUtils.getSession();
        session.getTransaction().begin();
        Product product = new Product();
        product.setId(id);
        session.remove(product);
        session.getTransaction().commit();

    }

    public Product saveOrUpdate(Product product) {
        Session session = sessionFactoryUtils.getSession();
        session.getTransaction().begin();
        session.saveOrUpdate(product);
        session.getTransaction().commit();
        return product;
    }

}
