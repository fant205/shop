package com.alexey.shop;

import com.alexey.shop.dto.ProductDTO;
import com.alexey.shop.dto.UserDTO;
import com.alexey.shop.services.ShopService;
import org.junit.After;
import org.junit.Before;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class Test {
    private GenericApplicationContext ctx;
    private ShopService shopService;

    @Before
    public void setUp() {
        ctx = new AnnotationConfigApplicationContext("com.alexey.shop");
        shopService = ctx.getBean(ShopService.class);
        assertNotNull(shopService);
    }

    @org.junit.Test
    public void findProductsByUserId() {
        List<ProductDTO> all = shopService.findProductsByUserId(1l);
        assertNotNull(all);
        assertTrue(all.size() == 3);
    }

    @org.junit.Test
    public void findUsersByProductId() {
        List<UserDTO> all = shopService.findUsersByProductId(4l);
        assertNotNull(all);
        assertTrue(all.size() == 2);
    }

    @After
    public void tearDown() {
        ctx.close();
    }
}
