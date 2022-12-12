package com.alexey.shop.mapper;

import com.alexey.shop.dto.UserDto;
import com.alexey.shop.model.Role;
import com.alexey.shop.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(UserDto productDto);

    @InheritInverseConfiguration
    UserDto fromUser(User product);

    List<User> toUserList(List<UserDto> userDtoList);

    List<UserDto> fromUserList(List<User> users);


}
