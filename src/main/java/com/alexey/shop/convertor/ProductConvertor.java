package com.alexey.shop.convertor;

import com.alexey.shop.dto.ProductDto;
import com.alexey.shop.model.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductConvertor {
    private final ModelMapper modelMapper;

    public ProductConvertor() {
        this.modelMapper = new ModelMapper();
    }

    public ProductDto convertToDto(Product entity) {
        return modelMapper.map(entity, ProductDto.class);
    }
}
