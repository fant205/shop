package com.alexey.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersGetAllDto {

    private List<UserDto> list;
    private Integer pagesCount;
    private Long recordsTotal;
}
