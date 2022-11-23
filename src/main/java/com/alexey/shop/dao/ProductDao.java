package com.alexey.shop.dao;


import com.alexey.shop.model.Product;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class ProductDao {

    private EntityManagerFactory factory;

    @PostConstruct
    public void init() {
        factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    public Product findById(Long id) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Product product = em.find(Product.class, id);
        em.getTransaction().commit();
        return product;
    }

    public List<Product> findAll() {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        List list = em.createQuery("select p from Product p order by p.id asc", Product.class).getResultList();
        em.getTransaction().commit();
        return list;
    }

    public void deleteById(Long id) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
//        Product product = em.find(Product.class, id);
        Product product = new Product();
        product.setId(id);
        em.remove(product);
        em.getTransaction().commit();

    }

    public Product saveOrUpdate(Product product) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        if (product.getId() == null) {
            em.persist(product);
        } else {
            product = em.merge(product);
        }
        em.getTransaction().commit();
        return product;
    }

}
