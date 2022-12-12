package com.alexey.shop.controllers;

import com.alexey.shop.dto.ProductDto;
import com.alexey.shop.dto.ProductsGetDto;
import com.alexey.shop.dto.UserDto;
import com.alexey.shop.dto.UsersGetAllDto;
import com.alexey.shop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_SUPERADMIN"})
    public UserDto findUsersByProductId(@PathVariable Long id) {
        return userService.findProductById(id);
    }

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_SUPERADMIN"})
    public UsersGetAllDto getAllProducts(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String login,
            @RequestParam(required = false) String role,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        return userService.findAllProducts(id, login, role, page, size);
    }

    @PostMapping
    @Secured({"ROLE_SUPERADMIN"})
    public void create(@RequestBody UserDto userDto) {
        userService.create(userDto);
    }

    @PutMapping
    @Secured({"ROLE_SUPERADMIN"})
    public void update(@RequestBody UserDto userDto) {
        userService.update(userDto);
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_SUPERADMIN"})
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

}
