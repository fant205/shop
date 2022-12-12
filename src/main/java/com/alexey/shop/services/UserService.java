package com.alexey.shop.services;

import com.alexey.shop.dto.ProductDto;
import com.alexey.shop.dto.ProductsGetDto;
import com.alexey.shop.dto.UserDto;
import com.alexey.shop.dto.UsersGetAllDto;
import com.alexey.shop.mapper.ProductMapper;
import com.alexey.shop.mapper.UserMapper;
import com.alexey.shop.model.Product;
import com.alexey.shop.model.User;
import com.alexey.shop.repository.UsersRepository;
import com.alexey.shop.repository.specification.ProductSpecification;
import com.alexey.shop.repository.specification.UserSpecification;
import com.alexey.shop.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UsersRepository usersRepository;
    private final UserValidator userValidator;
    
    public UserDto findProductById(Long id) {
        User user = usersRepository.findById(id).orElseThrow();
        return UserMapper.MAPPER.fromUser(user);
    }

    public UsersGetAllDto findAllProducts(Long id, String login, String role, Integer page, Integer size) {
        Specification<User> spec = Specification
                .where(id == null ? null : UserSpecification.equalId(id))
                .and(StringUtils.hasText(login) ? UserSpecification.likeLogin(login) : null);

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id"));
        Page<User> products = usersRepository.findAll(spec, pageRequest);
        List<UserDto> list = UserMapper.MAPPER.fromUserList(products.toList());
        return new UsersGetAllDto(list, products.getTotalPages(), products.getTotalElements());
    }

    @Transactional
    public void create(UserDto userDto) {
        userValidator.validate(userDto);
        User user = UserMapper.MAPPER.toUser(userDto);
        usersRepository.save(user);
    }

    @Transactional
    public void update(UserDto userDto) {
        userValidator.validate(userDto);
        User user = usersRepository.findById(userDto.getId()).orElseThrow();
        user.setLogin(userDto.getLogin());
        usersRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        usersRepository.deleteById(id);
    }
}
