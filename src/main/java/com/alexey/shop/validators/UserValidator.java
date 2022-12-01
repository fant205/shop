package com.alexey.shop.validators;

import com.alexey.shop.dto.ProductDto;
import com.alexey.shop.dto.UserDto;
import com.alexey.shop.exceptions.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserValidator {
    public void validate(UserDto userDto) {
        List<String> errors = new ArrayList<>();
        if (!StringUtils.hasText(userDto.getLogin())) {
            errors.add("Login пользователя не может быть пустым!");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
