package com.alexey.shop.services;

import com.alexey.shop.dao.ProductsDao;
import com.alexey.shop.dao.UsersDao;
import com.alexey.shop.dto.ProductDTO;
import com.alexey.shop.dto.UserDTO;
import com.alexey.shop.model.Product;
import com.alexey.shop.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ProductsDao productsDao;
    private final UsersDao usersDao;

    public List<ProductDTO> findProductsByUserId(Long id){
        User user = usersDao.findById(id);
        List<Product> products = user.getProducts();
        ArrayList<ProductDTO> result = new ArrayList<>();
        products.forEach(product -> {
            result.add(new ProductDTO(product.getId(), product.getTitle(), product.getCost()));
        });
        return result;
    }

    public List<UserDTO> findUsersByProductId(Long id){
        Product product = productsDao.findById(id);
        List<User> list = product.getUsers();
        ArrayList<UserDTO> result = new ArrayList<>();
        list.forEach(user -> {
            result.add(new UserDTO(user.getId(), user.getName()));
        });
        return result;
    }
}
